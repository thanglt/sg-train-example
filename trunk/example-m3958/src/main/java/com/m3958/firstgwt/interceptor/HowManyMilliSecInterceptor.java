package com.m3958.firstgwt.interceptor;

import java.util.logging.Logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;

public class HowManyMilliSecInterceptor implements MethodInterceptor {
	
	@Inject
	private Logger logger;
	
	@Override
	public Object invoke(MethodInvocation invacation) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object ret = invacation.proceed();
		long endTime = System.currentTimeMillis();
		logger.info(invacation.getMethod().getName() + " took->" + + ( endTime - startTime ) + " ms ");
	    return ret;
	}
	
}
