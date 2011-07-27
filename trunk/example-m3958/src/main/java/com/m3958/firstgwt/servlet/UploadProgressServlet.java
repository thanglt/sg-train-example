package com.m3958.firstgwt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.UploadFormField;
import com.m3958.firstgwt.service.UploadProgressListener;
import com.m3958.firstgwt.session.UploadProgressSession;

@Singleton
public class UploadProgressServlet extends HttpServlet implements Servlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Injector injector;
	
	
	private long preCleanTime = new Date().getTime();
	
	//必须有一种机制，定时清理死去的UploadProgressListener。没关系，session会自动过期。
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/html;charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		
//
//		UploadProgressSession ups = injector.getInstance(UploadProgressSession.class);
//		Map<String, UploadProgressListener> listenerMap = ups.getListenerMap();
//		
//		
//		
//		for(UploadProgressListener p : listenerMap.values()){
//			
//		}
		
		String fuTimeStamp = req.getParameter(UploadFormField.FU_TIMESTAMP.toString());
		UploadProgressSession fups = injector.getInstance(UploadProgressSession.class);
		UploadProgressListener fupl = null;
		if(fups != null){
			fupl = fups.getListener(fuTimeStamp);
		}
		
		if(fupl == null){
			fupl = injector.getInstance(UploadProgressListener.class);
		}
		
		PrintWriter out = res.getWriter();
		out.write(fupl.toJsonString());
		out.close();
	}
	
	

}
