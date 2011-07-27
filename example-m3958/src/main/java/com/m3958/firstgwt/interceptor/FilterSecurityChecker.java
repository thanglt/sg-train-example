package com.m3958.firstgwt.interceptor;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.accesschecker.LgbChecker;
import com.m3958.firstgwt.client.types.SmartParameterName;

//这个filte也许没有必要，因为可以在dao一侧做控制。

@Singleton
public class FilterSecurityChecker implements Filter{
	
	private ServletContext context;
	
	@Inject
	LgbChecker lgbChecker;
	
	@Override
	public void destroy() {
		context = null;
		
	}
//	用户：只有Read，smartFetch(params.getCriteria());
//	role：smartFetch(params.getCriteria());
//	fieldenum：add，update，remove
//	objectclass：add,upate,remove
//	日历：smartRelationFetch



	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if("com.m3958.firstgwt.model.Lgb".equals(request.getParameter(SmartParameterName.MODEL_NAME))){
//			if(!lgbChecker.checkAccess(request.getParameter("departmentIds"), new Lgb(), OperationType.LIST)){
//				Writer writer = response.getWriter();
//				writer.write("Access denied!");
//				writer.close();
//				return;
//			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		context = filterConfig.getServletContext();
	}

}
