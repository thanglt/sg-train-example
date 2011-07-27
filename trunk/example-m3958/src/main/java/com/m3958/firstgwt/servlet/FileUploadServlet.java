package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.UploadFormField;
import com.m3958.firstgwt.service.ContentImgUploader;
import com.m3958.firstgwt.service.DiskFileUpload;
import com.m3958.firstgwt.service.JrAttamentsUploader;
import com.m3958.firstgwt.service.JrPreViewImgUploader;
import com.m3958.firstgwt.service.AttachmentUploader;
import com.m3958.firstgwt.service.TitleImgUploader;
import com.m3958.firstgwt.service.FileUploader;
import com.m3958.firstgwt.service.SimpleUploader;
import com.m3958.firstgwt.service.UploaderInFolder;

@Singleton
public class FileUploadServlet extends HttpServlet implements Servlet{
	
	
	@Inject
	Injector injector;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		
		res.setContentType("text/html;charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		
		String uploadForString = req.getParameter(UploadFormField.UPLOAD_FOR.toString()); 
//		if( uploadForString == null || uploadForString.isEmpty()){
//			printError(req, res,"must have an " + UploadFormField.UPLOAD_FOR.toString() +  " parameter!");
//			return;
//		}
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		
		if(isMultipart){
				FileUploader nfu;
				UploadFor ufor = getUploadForValue(uploadForString);
				req.setAttribute("uploadFor", ufor);
				switch (ufor) {
				case JRXML_FILE_PREVIEW_IMG:
					nfu = injector.getInstance(JrPreViewImgUploader.class);
					break;
				case JRXML_FILE_ATTACHMENT:
					nfu = injector.getInstance(JrAttamentsUploader.class);
					break;
				case LGB_ZP:
					nfu = injector.getInstance(TitleImgUploader.class);
					break;
				case ATTACHMENT:
					nfu = injector.getInstance(AttachmentUploader.class);
					break;
				case UPLOAD_TO_FOLDER:
					nfu = injector.getInstance(UploaderInFolder.class);
					break;
				case SITE_FILE:
					nfu = injector.getInstance(DiskFileUpload.class);
					break;
				case TITLE_IMG:
					nfu = injector.getInstance(TitleImgUploader.class);
					break;
				case CONTENT_IMG:
					nfu = injector.getInstance(ContentImgUploader.class);
					break;
				default:
					nfu = injector.getInstance(SimpleUploader.class);
					break;
				}
				nfu.process();
		}else{
			printError(req,res,"Tt's only for fileUpload!");
		}
	}
	

	private UploadFor getUploadForValue(String uploadForString) {
		UploadFor ufor = UploadFor.JUST_UPLOAD;
		try {
			ufor = UploadFor.valueOf(uploadForString);
		} catch (Exception e) {} 
		return ufor;
	}


	private void printError(HttpServletRequest req, HttpServletResponse res,String msg) throws IOException {
		PrintWriter out = res.getWriter();
		out.write(msg);
		out.close();
	}

}
