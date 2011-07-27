package com.m3958.firstgwt.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.m3958.firstgwt.client.types.FileSaveTo;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.utils.StringUtils;
import com.m3958.firstgwt.dao.WebSiteDao;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.utils.ExecPerl;


public class DiskAssetRepository extends AssetRepositoryImpl implements AssetRepository{

	@Override
	public InputStream get(Asset asset) {
		return null;
	}

	@Override
	public Asset save(String siteId,UploadFor ufor,String fname, String contentType, InputStream stream) throws IOException {
    	Asset a = createAsset();
    	saveAsset(a);
    	a.setOriginName(fname);
    	a.setMimeType(contentType);
    	a.setSaveTo(FileSaveTo.FILE_SYSTEM);
    	try {
			a.setSiteId(Integer.parseInt(siteId));
		} catch (NumberFormatException e2) {}
    	//改变了fileId和filePath。将每一个asset取出来，复制文件，更改fileId和filePath。
//		DateFormat df = new SimpleDateFormat(SmartConstants.UPLOADED_FILE_DIR_PATTERN);
		
//		String s = df.format(a.getCreatedAt());
		String[] sss = (a.getId() + "").split("");
		int l = sss.length;
		String s = sss[l-3] + "/" + sss[l-2] + "/" + sss[l-1] + "/";
		
		String ext = StringUtils.getFileExtensionWithDot(a.getOriginName());
    	a.setFilePath(s + a.getId() + ext);
    	a.setFileId(a.getId()+"");
    	a.setExtension(ext);
    	scs.createDirIfNotExist(s);
    	
    	File file = new File(scs.getAssetSavePath(),a.getFilePath());
    	long size = 0;
    	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
    	byte[] bytes = new byte[4096];
    	int num;
    	while((num = stream.read(bytes)) != -1){
    		size += num;
    		bos.write(bytes, 0, num);
    	}
    	bos.flush();
    	bos.close();
    	a.setFileSize(size);
    	
    	int sid = SmartConstants.NONE_EXIST_MODEL_ID;
    	try {
			sid = Integer.parseInt(siteId);
		} catch (NumberFormatException e1) {}
    	
		String[] ss = new String[]{"48x48"};
		if(sid != SmartConstants.NONE_EXIST_MODEL_ID){
			WebSiteDao wsdao = injector.getInstance(WebSiteDao.class);
			WebSite site = wsdao.find(sid);
			if(site != null && site.getImgSizes() != null && !site.getImgSizes().isEmpty()){
				if(site.getImgSizes().contains("48x48")){
					ss = site.getImgSizes().split(",");
				}else{
					ss = new String[ss.length + 1];
					ss[ss.length - 1] = "48x48";
				}
	    		
			}
		}
    	if(StringUtils.isImageExt(fname)){
			ExecPerl ep = injector.getInstance(ExecPerl.class);//new ExecPerl("resize.pl",scs.getAssetSavePath(),a.getFilePath());
	    	try {
				ep.exec(new File(fps.getWebinfDir(),"resize.pl").getAbsolutePath(),scs.getAssetSavePath(),a.getFilePath(),ss);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	saveAsset(a);
    	return a;
	}

//	@Override
//	public Asset save(String siteId,UploadFor ufor,String fname, String contentType, InputStream stream) throws IOException {
//    	Asset a = createAsset();
//    	a.setOriginName(fname);
//    	a.setMimeType(contentType);
//    	a.setSaveTo(FileSaveTo.FILE_SYSTEM);
//    	try {
//			a.setSiteId(Integer.parseInt(siteId));
//		} catch (NumberFormatException e2) {}
//    	
//		DateFormat df = new SimpleDateFormat(SmartConstants.UPLOADED_FILE_DIR_PATTERN);
//		String s = df.format(a.getCreatedAt());
//		String ext = StringUtils.getFileExtensionWithDot(a.getOriginName());
//    	a.setFilePath(s + a.getFileId() + ext);
//    	a.setExtension(ext);
//    	scs.createDirIfNotExist(s);
//    	
//    	File file = new File(scs.getAssetSavePath(),a.getFilePath());
//    	long size = 0;
//    	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//    	byte[] bytes = new byte[4096];
//    	int num;
//    	while((num = stream.read(bytes)) != -1){
//    		size += num;
//    		bos.write(bytes, 0, num);
//    	}
//    	bos.flush();
//    	bos.close();
//    	a.setFileSize(size);
//    	
//    	int sid = SmartConstants.NONE_EXIST_MODEL_ID;
//    	try {
//			sid = Integer.parseInt(siteId);
//		} catch (NumberFormatException e1) {}
//    	
//		String[] ss = new String[]{"48x48"};
//		if(sid != SmartConstants.NONE_EXIST_MODEL_ID){
//			WebSiteDao wsdao = injector.getInstance(WebSiteDao.class);
//			WebSite site = wsdao.find(sid);
//			if(site != null && site.getImgSizes() != null && !site.getImgSizes().isEmpty()){
//				if(site.getImgSizes().contains("48x48")){
//					ss = site.getImgSizes().split(",");
//				}else{
//					ss = new String[ss.length + 1];
//					ss[ss.length - 1] = "48x48";
//				}
//	    		
//			}
//		}
//    	if(StringUtils.isImageExt(fname)){
//			ExecPerl ep = injector.getInstance(ExecPerl.class);//new ExecPerl("resize.pl",scs.getAssetSavePath(),a.getFilePath());
//	    	try {
//				ep.exec(new File(fps.getWebinfDir(),"resize.pl").getAbsolutePath(),scs.getAssetSavePath(),a.getFilePath(),ss);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//    	}
//    	saveAsset(a);
//    	return a;
//	}
}
