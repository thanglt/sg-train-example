package com.m3958.firstgwt.service;

import javax.servlet.ServletContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

@Singleton
public class WebContextFMCfg extends Configuration{
	
	@Inject
	public WebContextFMCfg(ServletContext sctx,DefaultObjectWrapper dow) {
		setServletContextForTemplateLoading(sctx, "/");
		setObjectWrapper(dow);
		setDefaultEncoding("utf8");
	}

}
