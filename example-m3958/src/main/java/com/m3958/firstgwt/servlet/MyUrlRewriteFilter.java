package com.m3958.firstgwt.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Singleton;

@Singleton
public class MyUrlRewriteFilter implements Filter{
	
	private FilterConfig filterConfig;
	
//	static private String[] appCoreResource = new String[]{
//		"/firstgwt/",
//		"/images/",
//		"/Firstgwt.html",
//		"/favicon.ico"
//	};
//	
//	static int len = appCoreResource.length;

	@Override
	public void destroy() {
		filterConfig = null;
		
	}
	/*
	 * 不用filter，而采用/* servlet来拦截全部请求，对于已存在的文件，自己来写代码发送.
	 * 
	 */

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		/*
		 *  /firstgwt/
		 *  /images/
		 *  /Firstgwt.html
		 *  /favicon.ico
		 */
		
//		String uri = req.getRequestURI();
		String path = filterConfig.getServletContext().getRealPath(req.getRequestURI());
		File f = new File(path);
		if(f.isFile()){
			chain.doFilter(request, response);
			return;
		}
		
//		for(int i=0;i<len;i++){
//			if(uri.startsWith(appCoreResource[i])){
//				chain.doFilter(request, response);
//				return;
//			}
//		}
		
//		String remoteHostName = req.getRemoteHost();
//		String url = req.getRequestURL().toString();
//		System.out.println(url);
//		if(url.startsWith("http://127.0.0.1")){
//			chain.doFilter(request, response);
//		}else{
		RequestDispatcher rd = req.getRequestDispatcher("/webFront/a");
		rd.forward(request, response);
//		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
