package com.m3958.firstgwt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.google.inject.Inject;

public class DiskFileUpload extends FileUploader{
	
	@Inject
	public DiskFileUpload(HttpServletRequest req, HttpServletResponse res) {
		super(req, res);
	}
	
	@Inject
	private FilePathService fps;
	//参数：siteId,dirPath,
	
//	@Override
//	@SuppressWarnings("unchecked")
//	public void process(){
//		
//			try {
//				// Create a new file upload handler
//				if(isExceedMaxSize()){
//					status = UploadResponseStatus.FAILURE;
//					errorMsg = "上传内容太大了。";
//					writeResponse();
//					return;
//				}
//				
//				String extraParams = req.getParameter(UploadFormField.EXTRA_PARAMS.toString());
//				String[] ss = extraParams.split(",");
//				if(ss.length != 2)return;
//				
//				String siteId = ss[0];
//				String dirPath = ss[1];
//				
//				ServletFileUpload upload = new ServletFileUpload();
//				Enumeration<String> pss = req.getParameterNames();
//				
//				while(pss.hasMoreElements()){
//					String ps = pss.nextElement();
//					params.put(ps, req.getParameter(ps));
//				}
//				
//				UploadProgressListener fupl = injector.getInstance(UploadProgressListener.class); 
//				upload.setProgressListener(fupl);
//				
//				fuTimeStamp = req.getParameter(UploadFormField.FU_TIMESTAMP.toString());
//				askerTimeStamp = req.getParameter(UploadFormField.ASKER_TIMESTAMP.toString());
//				
//				fups.addListener(fuTimeStamp, fupl);
//
//				FileItemIterator iter = upload.getItemIterator(req);
//				while (iter.hasNext()) {
//				    FileItemStream item = iter.next();
//				    String name = item.getFieldName();
//				    InputStream stream = item.openStream();
//				    
//				    if (item.isFormField()) {
//				    	String value = Streams.asString(stream);
//				    	params.put(name, value);
//				    } else {
//				    	String fname = item.getName();
//				    	if(fname != null){
//				    		fname = new String(fname.getBytes(Charset.defaultCharset().displayName()),"UTF-8");
//				    		fname = FilenameUtils.getName(fname);
//				    	}
//				    	fupl.addFnames(fname);
//				    	saveSiteFile(siteId,dirPath,fname,stream);
//				    }
//				}
//				afterUpload();
//				status = UploadResponseStatus.SUCCESS;
//				msg = "上传成功！";
//				writeResponse();
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (FileUploadException e) {
//				e.printStackTrace();
//			}
//	}

//	private void saveSiteFile(String siteId, String dirPath, String fname,
//			InputStream stream) throws IOException {
//		File f = fps.getFile(siteId, dirPath);
//		f = new File(f,fname);
//		if(f.exists()){
//			status = UploadResponseStatus.FAILURE;
//			msg = "文件已经存在！请先删除再上传！";
//			return;
//		}
//		
//    	long size = 0;
//    	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
//    	byte[] bytes = new byte[4096];
//    	int num;
//    	while((num = stream.read(bytes)) != -1){
//    		size += num;
//    		bos.write(bytes, 0, num);
//    	}
//    	bos.flush();
//    	bos.close();
//	}

	@Override
	protected void afterUpload() {

	}


	@Override
	protected JSONObject getOthersJsonObject() {
		return null;
	}

	@Override
	protected String getHtmlResponse() {
		return null;
	}

	@Override
	protected boolean isExceedMaxSize() {
		return false;
	}

}
