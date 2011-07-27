package com.m3958.firstgwt.module;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.m3958.firstgwt.annotation.HowManyMilliSec;
import com.m3958.firstgwt.interceptor.HowManyMilliSecInterceptor;

public class HowManyMilliSecModule extends AbstractModule {

	@Override
	protected void configure() {
		HowManyMilliSecInterceptor tuic = new HowManyMilliSecInterceptor();
		requestInjection(tuic);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(HowManyMilliSec.class), tuic);
	}

}
