package com.m3958.firstgwt.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoped;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.UploadFormField;
import com.m3958.firstgwt.client.types.UploadResponseFields;
import com.m3958.firstgwt.client.types.UploadResponseStatus;
import com.m3958.firstgwt.client.types.UploadWantedResponse;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.server.response.UploadResponse;
import com.m3958.firstgwt.session.UploadProgressSession;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;


@RequestScoped
public abstract class FileUploader {

	protected HttpServletRequest req;
	protected HttpServletResponse res;
	
	@Inject
	protected ModelAndDao modelConfig;

	@Inject
	protected Injector injector;
	
	@Inject
	protected AppUtilService autils;
	
	@Inject
	protected UploadProgressSession fups;	
	
	protected List<Asset> assets = new ArrayList<Asset>();
	
	protected Map<String,String> params = new HashMap<String, String>();
	
	protected int status = UploadResponseStatus.FAILURE;
	
	protected String askerTimeStamp;
	
	protected String fuTimeStamp;
	
	protected String msg;
	
	protected String errorMsg;
	
	protected String viewname;
	
	protected abstract boolean isExceedMaxSize();
	
	@Inject
	public FileUploader(HttpServletRequest req, HttpServletResponse res){
		this.req = req;
		this.res = res;
	}
	

	@SuppressWarnings("unchecked")
	public void process(){
			fuTimeStamp = req.getParameter(UploadFormField.FU_TIMESTAMP.toString());
			askerTimeStamp = req.getParameter(UploadFormField.ASKER_TIMESTAMP.toString());
			viewname = req.getParameter(UploadFormField.VIEWNAME.toString());
			String siteId = req.getParameter(CommonField.SITE_ID.getEname());
			try {
				// Create a new file upload handler
				if(isExceedMaxSize()){
					status = UploadResponseStatus.FAILURE;
					errorMsg = "上传内容太大了。";
					writeResponse();
					return;
				}
				
				ServletFileUpload upload = new ServletFileUpload();
				Enumeration<String> pss = req.getParameterNames();
				
				while(pss.hasMoreElements()){
					String ps = pss.nextElement();
					params.put(ps, req.getParameter(ps));
				}
				
				UploadProgressListener fupl = injector.getInstance(UploadProgressListener.class); 
				upload.setProgressListener(fupl);
				
				fuTimeStamp = req.getParameter(UploadFormField.FU_TIMESTAMP.toString());
				askerTimeStamp = req.getParameter(UploadFormField.ASKER_TIMESTAMP.toString());
				viewname = req.getParameter(UploadFormField.VIEWNAME.toString());
				
				fups.addListener(fuTimeStamp, fupl);
				
//				System.out.println(req.getContentLength());
				//我们可以在这里构建好Asset，已经知道fileId，所以不需要tmpdir，直接将流写入目标文件即可。
				// Parse the request
				
				FileItemIterator iter = upload.getItemIterator(req);
				while (iter.hasNext()) {
				    FileItemStream item = iter.next();
				    String name = item.getFieldName();
				    InputStream stream = item.openStream();
				    
				    if (item.isFormField()) {
				    	String value = Streams.asString(stream);
				    	params.put(name, value);
				    } else {
				    	String fname = item.getName();
				    	if(fname != null){
				    		fname = new String(fname.getBytes(Charset.defaultCharset().displayName()),"UTF-8");
				    		fname = FilenameUtils.getName(fname);
				    	}
				    	fupl.addFnames(fname);

				    	AssetRepository dar = injector.getInstance(DiskAssetRepository.class);
				    	assets.add(dar.save(siteId,(UploadFor) req.getAttribute("uploadFor"),fname,item.getContentType(),stream));
				    }
				}
				afterUpload();
				status = UploadResponseStatus.SUCCESS;
				msg = "上传成功！";
				writeResponse();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
	}
	
	protected void writeResponse() throws IOException{
		UploadWantedResponse uwr = getWantedResponse();
		switch (uwr) {
		case PJSON:
			sendPJsonResponse();
			break;
		case JSON:
			sendJsonResponse();
			break;
		case KINDEDITORJSON:
			sendKindEditorJson();
			break;
		default:
			sendHtmlResponse();
			break;
		}
	}


	private void sendKindEditorJson() throws IOException {
		JSONObject jo = new JSONObject();
		jo.element("error", 0);
		jo.element("url", assets.get(0).getUrl());
		autils.writeHtmlResponse(res, jo.toString());
	}

	private UploadWantedResponse getWantedResponse(){
		UploadWantedResponse uwr = UploadWantedResponse.HTML;
		try {
			uwr = UploadWantedResponse.valueOf(req.getParameter(UploadFormField.WANTED_RESPONSE.toString()));
		} catch (Exception e) {}
		return uwr;
	}

	protected abstract void afterUpload();
	
	protected String iFrameHtml1 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" +
	"<html>" + 
	"<head>" + 
	"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + 
	"<title>Insert title here</title>" + 
	"</head>" + 
	"<body>";
	
	protected String iFrameHtml2 = "<script type=\"text/javascript\">self.location.href=\"/blank.html\"</script></body></html>";
	
	
	protected void sendPJsonResponse() throws IOException {
		JSONObject jo = getOthersJsonObject();
		if(jo == null){
			jo = defaultOthers();
		}
		PrintWriter out = res.getWriter();
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.ASSET_EXCLUDES);
		
		UploadResponse urp = new UploadResponse(status,
												JSONArray.fromObject(assets,jc),
												jo);
		String s = "<script>window.top.assetsMethods.afterUpload('" + urp.toString()  + "');</script>";
		out.write(iFrameHtml1 + s + iFrameHtml2);
		out.close();
	}
	
	
	protected void sendJsonResponse() throws IOException {
		JSONObject jo = getOthersJsonObject();
		if(jo == null){
			jo = defaultOthers();
		}
		PrintWriter out = res.getWriter();
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.ASSET_EXCLUDES);
		
		
		UploadResponse urp = new UploadResponse(status,
												JSONArray.fromObject(assets,jc),
												jo);
		out.write(urp.toString());
		out.close();
	}
	
	
	private void sendHtmlResponse() throws IOException {
		if(UploadResponseStatus.SUCCESS != status && getWantedResponse() == UploadWantedResponse.KINDEDITORJSON){
			JSONObject jo = new JSONObject();
			jo.element("error", 1);
			jo.element("message", errorMsg);
			autils.writeHtmlResponse(res, jo.toString());
			return;
			
		}
		
		PrintWriter out = res.getWriter();
		String html = getHtmlResponse();
		if(html == null){
			html = getDefaultHtmlResponse();
		}
		out.write(html);
		
		out.close();
	}
	


	private String getDefaultHtmlResponse() {
		if(UploadResponseStatus.SUCCESS == status){
			return "上传成功！";
		}else{
			return "上传失败！";
		}
	}


	protected abstract String getHtmlResponse();


	private JSONObject defaultOthers() {
		JSONObject jo = new JSONObject();
		jo.element(UploadResponseFields.UPLOADFOR.toString(), req.getParameter(UploadFormField.UPLOAD_FOR.toString()));
		jo.element(UploadFormField.ASKER_TIMESTAMP.toString(), askerTimeStamp);
		jo.element(UploadFormField.FU_TIMESTAMP.toString(), fuTimeStamp);
		jo.element(UploadFormField.VIEWNAME.toString(), viewname);
		if(UploadResponseStatus.SUCCESS == status)
			jo.element(UploadResponseFields.MSG.toString(), msg);
		else{
			jo.element(UploadResponseFields.ERRORMSG.toString(), errorMsg);
		}
		return jo;
	}


	protected abstract JSONObject getOthersJsonObject();
	
}
