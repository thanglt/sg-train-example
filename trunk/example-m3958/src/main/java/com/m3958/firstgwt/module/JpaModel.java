package com.m3958.firstgwt.module;

import nl.captcha.servlet.SimpleCaptchaServlet;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.m3958.firstgwt.service.FirstRunCheckService;
import com.m3958.firstgwt.service.ModelAndDao;
import com.wideplay.warp.persist.jpa.JpaUnit;

public class JpaModel extends AbstractModule {

	@Override
	protected void configure() {
		bindConstant().annotatedWith(JpaUnit.class).to("p-unit");
		bind(JpaInitializer.class).asEagerSingleton();
		bind(ModelAndDao.class).asEagerSingleton();
		bind(FirstRunCheckService.class).asEagerSingleton();
		bind(SimpleCaptchaServlet.class).in(Singleton.class);
	}
}
