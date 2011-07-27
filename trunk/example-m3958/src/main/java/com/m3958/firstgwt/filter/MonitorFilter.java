package com.m3958.firstgwt.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.service.Monitor;

@Singleton
public class MonitorFilter implements Filter{
	
	@Inject
	private Monitor monitor;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			if(request instanceof HttpServletRequest){
				String uri = ((HttpServletRequest)request).getRequestURI();
				if(uri.startsWith("/showtimes")){
					((HttpServletResponse)response).setStatus(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
			}
			monitor.increaseRequestCounter();
			chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
