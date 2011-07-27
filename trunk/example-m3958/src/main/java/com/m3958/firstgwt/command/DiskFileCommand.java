package com.m3958.firstgwt.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;


import com.google.inject.Inject;
import com.m3958.firstgwt.accesschecker.WebSiteChecker;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.DiskFileField;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.dao.CacheBreakerItemDao;
import com.m3958.firstgwt.exception.BadZipEntryNameException;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.CacheBreakerItem;
import com.m3958.firstgwt.model.DiskFile;
import com.m3958.firstgwt.model.CacheBreakerItem.ObAction;
import com.m3958.firstgwt.model.CacheBreakerItem.ObType;
import com.m3958.firstgwt.server.response.ErrorMessageResponse;
import com.m3958.firstgwt.server.response.HasToJsonResponse;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.server.types.CharactSetName;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.server.types.HasToJson;
import com.m3958.firstgwt.service.FilePathService;
import com.m3958.firstgwt.service.SiteConfigService;

public class DiskFileCommand extends BaseCommand{
	
	@Inject
	private FilePathService fps;
	
	private boolean writeResponse = true;
	
	@Inject
	private SiteConfigService scs;
	
	private String result = null;
	
	private File siteRoot;
	
	private String siteId;
	private String dirPath;
	private String fileName;
	private String oldFileName;
	private boolean isFolder;
	private String content;
	
	private int requestTimeStamp;
	private Boolean showBak;
	
	@Override
	public void execute() throws SmartJpaException, IOException {
		SmartOperationName son = SmartOperationName.NO_OPERATION;
		SmartSubOperationName sson = SmartSubOperationName.NO_SUB_OPERATION;
		
		WebSiteChecker wsc = injector.getInstance(WebSiteChecker.class);
		if(!wsc.doCheck()){
			errors.addError(new Error("没有操作权限！", ServerErrorCode.ACCESS_DENY));
			writeErrorResponse();
			return;
		}
		String ips = initParas(); 
		if(ips != null){
			autils.writeJsonResponse(res, ips);
			return;
		}
		
		try {
			son = SmartOperationName.valueOf(reqPs.getStringValue(SmartParameterName.OPERATION_TYPE).toUpperCase());
			sson = SmartSubOperationName.valueOf(reqPs.getStringValue(SmartParameterName.SUB_OPERATION_TYPE).toUpperCase());
		} catch (Exception e) {}
		switch (son) {
		case FETCH:
			switch (sson) {
			case FETCH_ONE:
				File tf = getFinalFileOrFolder();
				if(tf != null){
					streamFile = tf;
					fileName = tf.getName();
					ServletOutputStream out = res.getOutputStream();
					InputStream is = new FileInputStream(streamFile);
					res.setContentLength(new Long(streamFile.length()).intValue());
					res.setContentType("*/*");
					res.setHeader( "Content-disposition", "attachment;filename= " + fileName);
					byte[] buffer = new byte[4096];
					int i = 0;
					while((i = is.read(buffer)) > 0){
						out.write(buffer, 0, i);
					}
					is.close();
					out.close();
				}
				return;
			default:
				result = fetchFolderConent();
				break;
			}
			break;
		case REMOVE:
			result = removeDiskFile();
			break;
		case ADD:
			result = createFileOrFolder();
			break;
		case UPDATE:
			result = updateFileOrFolder();
			break;
		case CUSTOM:
			switch (sson) {
			case UNZIP:
				result = unzip();
				break;
			case FETCH_ONE:
				result = getOneFile();
				break;
			case PASTER_DISK_FILE:
				result = pasterDiskFile();
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		
		if(!errors.isEmpty()){
			result = injector.getInstance(ErrorMessageResponse.class).toString();
		}
		if(writeResponse)
			autils.writeJsonResponse(res,result);
	}



	private List<HasToJson> getChildrenDiskFile(File f) {
		if(requestTimeStamp == 0)requestTimeStamp = (int)new Date().getTime();
		List<HasToJson> results = new ArrayList<HasToJson>();
		String purpose = reqPs.getStringValue(CommonField.PURPOSE.getEname());
		showBak = reqPs.getBooleanValueObject(DiskFileField.SHOW_BACKUP.getEname());
		if(showBak == null)showBak = false;
		File[] files = f.listFiles();
		if(purpose == null || purpose.isEmpty()){
			if(files != null){
				for(File ff:files){
					if(showBak){
						DiskFile df = new DiskFile(ff);
						df.setId(requestTimeStamp++);
						results.add(df);
					}else{
						if(!ff.getName().matches(".*_\\d{4}_\\d{2}_\\d{2}.*")){
							DiskFile df = new DiskFile(ff);
							df.setId(requestTimeStamp++);
							results.add(df);
						}
					}
				}
			}
		}else if("theme".equals(purpose)){
			for(File ff:files){
				if(ff.isDirectory()){
					DiskFile df = new DiskFile(ff);
					df.setId(requestTimeStamp++);
					results.add(df);
				}
			}
		}else if("linktpl".equals(purpose)){
			File defaultf = new File(f,"default");
			if(defaultf.exists() && defaultf.isDirectory()){
				for(File ff:defaultf.listFiles()){
					if(ff.isFile() && ff.getName().endsWith(AppConstants.TPL_FILE_EXTENSION) && !ff.getName().matches(".*_\\d{4}_\\d{2}_\\d{2}.*")){
						DiskFile df = new DiskFile(ff,AppConstants.TPL_FILE_EXTENSION);
						df.setId(requestTimeStamp++);
						results.add(df);
					}
				}
			}
		}
		
		return results;
	}




	private String initParas(){
		siteId = reqPs.getStringValue(DiskFileField.SITE_ID.getEname());
		dirPath = reqPs.getStringValue(DiskFileField.DIR_PATH.getEname());
		if(siteId == null || siteId.isEmpty() || dirPath == null || dirPath.isEmpty())return HasToJsonResponse.getEmptyResponse().toString();
		
		fileName = reqPs.getStringValue(DiskFileField.FILE_NAME.getEname());
		oldFileName = reqPs.getStringValue(DiskFileField.OLD_FILE_NAME.getEname());
		content = reqPs.getStringValue(DiskFileField.CONTENT.getEname());
		requestTimeStamp = reqPs.getIntegerValue(CommonField.ID.getEname());
		if(reqPs.getBooleanValueObject(DiskFileField.IS_FOLDER.getEname()) == null){
			isFolder = false;
		}else{
			isFolder = reqPs.getBooleanValueObject(DiskFileField.IS_FOLDER.getEname());
		}
		return null;
	}
	
	
	private File getCurrentFolder(){
		File f = fps.getFile(siteId, dirPath);
		try {
			if(f.getCanonicalPath().startsWith(getSiteRootDir().getCanonicalPath())){
				return f;
			}
		} catch (IOException e) {}
		addFileNameError();
		return null;
	}
	
	
	private File getDiskFile(String fn){
		File f = fps.getFile(siteId, fn);
		try {
			if(f.getCanonicalPath().startsWith(getSiteRootDir().getCanonicalPath())){
				return f;
			}
		} catch (IOException e) {}
		addFileNameError();
		return null;
	}
	
	private File getSiteRootDir(){
		if(siteRoot != null)return siteRoot;
		siteRoot = fps.getFile(siteId, "/");
		return siteRoot;
	}
	
	private File getFinalFileOrFolder(){
		File f = getCurrentFolder();
		if(f == null)return null;
		f = new File(f,fileName);
		try {
			if(f.getCanonicalPath().startsWith(getSiteRootDir().getCanonicalPath())){
				return f;
			}
		} catch (IOException e) {}
		addFileNameError();
		return null;
	}
	
	private File getOldFileOrFolder(){
		File f = getCurrentFolder();
		if(f == null)return null;
		f = new File(f,oldFileName);
		try {
			if(f.getCanonicalPath().startsWith(getSiteRootDir().getCanonicalPath())){
				return f;
			}
		} catch (IOException e) {}
		addFileNameError();
		return null;
	}
	
	private void addFileNameError(){
		errors.addError(new Error("目录名称有问题！无法处理", ServerErrorCode.ACCESS_DENY));
	}
	
	private String createFileOrFolder(){
		File f = getFinalFileOrFolder();
		if(f != null && f.exists()){
			errors.addError(new Error("文件或目录已经存在！",ServerErrorCode.ACCESS_DENY));
			return null;
		}
		if(isFolder){
			return createFolder(f);
		}else{
			return createFile(f);
		}
	}
	
	private String updateFileOrFolder(){
		File fo = getOldFileOrFolder();
		File fn = getFinalFileOrFolder();
		if(fo == null || !fo.exists()){
			errors.addError(new Error("文件或者目录不存在！",ServerErrorCode.ACCESS_DENY));
			return null;
		}
		if(fn == null){
			errors.addError(new Error("文件名或目录不符合规范！",ServerErrorCode.ACCESS_DENY));
			return null;
		}
		if(isFolder){
			return updateFolder(fo,fn);
		}else{
			return updateFile(fo,fn);
		}

	}
	
	private String createFile(File f){
		try {
			fps.setContents(f, content, CharactSetName.UTF_8);
			List<HasToJson> results = new ArrayList<HasToJson>();
			DiskFile df = new DiskFile(f);
			df.setId(requestTimeStamp);
			results.add(df);
			return new HasToJsonResponse(results).toString();
		} catch (Exception e) {
			errors.addError(new Error("无法创建文件！",ServerErrorCode.ACCESS_DENY));
		}
		return null;
	}
	
	private String createFolder(File f){
		if(f.mkdir()){
			List<HasToJson> results = new ArrayList<HasToJson>();
			DiskFile df = new DiskFile(f);
			df.setId(requestTimeStamp);
			results.add(df);
			return new HasToJsonResponse(results).toString();
		}else{
			errors.addError(new Error("无法创建目录！",ServerErrorCode.ACCESS_DENY));
		}
		return null;
	}

	private String removeDiskFile() {
		File f = getFinalFileOrFolder();
		List<HasToJson> results = new ArrayList<HasToJson>();
		if(f.exists()){
			if(f.isDirectory()){
				if(f.list().length > 0){
					errors.addError(new Error("请先删除目录内容！", ServerErrorCode.DEPENDENCY_ERROR));
					return null;
				}
			}
			if(errors.getErrors().size() == 0){
				DiskFile df = new DiskFile(f);
				df.setId(requestTimeStamp);
				if(f.delete()){
					results.add(df);
				}else{
					errors.addError(new Error("删除失败！", ServerErrorCode.ACCESS_DENY));
					return null;
				}
			}
		}
		return new HasToJsonResponse(results).toString();
	}
	
	private String updateFile(File fo,File fn) {
		String result = null;
		try {
			if(fo.equals(fn)){//编辑
				if(scs.editable(fn)){
					File f = fps.getNextBakFile(fo);
					fps.copyFile(fo, f);
					fps.setContents(fo, content, CharactSetName.UTF_8);
					deleteOldBaks(f);
					DiskFile df = new DiskFile(f);
					df.setId(requestTimeStamp);
					result = new HasToJsonResponse(new ArrayList<HasToJson>()).toString();
				}else{//名字一样，又不能编辑。
					result =  new HasToJsonResponse(new ArrayList<HasToJson>()).toString();
				}
			}else{//重命名
				if(scs.editable(fn)){
					fps.setContents(fn, content, CharactSetName.UTF_8);
					DiskFile df = new DiskFile(fn);
					df.setId(requestTimeStamp);
					fps.deleteFile(fo);
					result =  new HasToJsonResponse(df).toString();
				}else{
					if(!fo.renameTo(fn)){
						errors.addError(new Error("重命名失败！",ServerErrorCode.ACCESS_DENY));
						return null;
					}
					DiskFile df = new DiskFile(fn);
					df.setId(requestTimeStamp);
					result = new HasToJsonResponse(df).toString();
				}
			}
			if(fo.getName().endsWith(AppConstants.TPL_FILE_EXTENSION)){
				updateCacheBreakItem(fo.getName());
			}
		} catch (Exception e) {e.printStackTrace();}
		return result;
	}
	
	private void deleteOldBaks(File fb) {
		File parent = fb.getParentFile(); 
		String afp = fb.getName();
		String dd = "_\\d{4}_\\d{2}_\\d{2}_\\d{2}_\\d{2}_\\d{2}\\.";
		String r = "(.*?)" + dd + "(.*)";
		Pattern pattern = Pattern.compile(r); 
		Matcher matcher = pattern.matcher(afp);
		boolean b = matcher.matches();
		if(b){
			String pre = matcher.group(1);
			String suffix = matcher.group(2);
			String r1 = pre + dd + suffix;
			List<String> bakefiles = new ArrayList<String>();
			File[] ff = parent.listFiles();
			for(File f : ff){
				String fn = f.getName();
				try{
					if(f.isFile() && fn != null && fn.matches(r1)){
						bakefiles.add(f.getAbsolutePath());
					}
				}catch (Exception e) {}
			}
			Collections.sort(bakefiles);
			for(int i=0;i<bakefiles.size()-3;i++){
				File myf = new File(bakefiles.get(i));
				try {
					myf.delete();
				} catch (Exception e) {}
			}
		}
	}



	private void updateCacheBreakItem(String fn){
		String tname = fn.substring(0, fn.lastIndexOf('.'));
		CacheBreakerItem cbi = new CacheBreakerItem();
		cbi.setAction(ObAction.UPDATE);
		cbi.setCreatedAt(new Date());
		cbi.setDone(false);
		cbi.setObId(SmartConstants.NONE_EXIST_MODEL_ID);
		cbi.setObName(tname);
		cbi.setObType(ObType.TEMPLATE);
		int sid = Integer.parseInt(siteId);
		cbi.setSiteId(sid);
		CacheBreakerItemDao cbidao = injector.getInstance(CacheBreakerItemDao.class);
		cbidao.smartPersistBaseModel(cbi);
	}
	
	private String updateFolder(File fo,File fn) {
		List<HasToJson> results = new ArrayList<HasToJson>();
		if(!fo.equals(fn)){
			if(fn.exists()){
				errors.addError(new Error("同名文件已经存在！",ServerErrorCode.ACCESS_DENY));
				return null;
			}
			if(!fo.renameTo(fn)){
				errors.addError(new Error("重命名失败！",ServerErrorCode.ACCESS_DENY));
				return null;
			}
		}
		DiskFile df = new DiskFile(fn);
		df.setId(requestTimeStamp);
		results.add(df);
		return new HasToJsonResponse(results).toString();
	}

	private String fetchFolderConent() {
		File tf = getCurrentFolder();
		if(tf != null)
			return new HasToJsonResponse(getChildrenDiskFile(tf)).toString();
		else
			return null;
	}
	
	private File streamFile = null;

	private String unzip() {
		File tf = getFinalFileOrFolder();
		if(tf != null){
			try {
				fps.unzipFile(siteId, tf);
				return new HasToJsonResponse(getChildrenDiskFile(tf)).toString();
			} catch (IOException e) {
				errors.addError(new Error("解压出错！IOException", ServerErrorCode.ACCESS_DENY));
			} catch (BadZipEntryNameException e) {
				errors.addError(new Error(e.getMessage(), ServerErrorCode.ACCESS_DENY));
			}
		}
		return null;
	}
	
	private String pasterDiskFile(){
		File pasterTo = getCurrentFolder();
		File bePaster = getDiskFile(fileName);
		boolean success = bePaster.renameTo(new File(pasterTo,bePaster.getName()));
		if(success){
			return new HasToJsonResponse(new DiskFile(bePaster)).toString();
		}else{
			errors.addError(new Error("移动文件出错",ServerErrorCode.NO_ERROR));
		}
		return null;
	}

	private String getOneFile() {
		try {
			File f = getFinalFileOrFolder();
			if(f != null){
				String c = "";
				if(scs.editable(f)){
					c = fps.getContents(f, CharactSetName.UTF_8);
				}
				DiskFile df = new DiskFile(f);
				df.setId(requestTimeStamp);
				df.setContent(c);
				return new HasToJsonResponse(df).toString();
			}
		} catch (Exception e) {}
		return null;
	}



	@Override
	public String getResult() {
		return result;
	}
	
	@Override
	public void execute(boolean writeResponse) throws SmartJpaException,
			IOException {
		this.writeResponse = writeResponse;
		execute();
	}

}
