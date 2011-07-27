package com.m3958.firstgwt.service;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;


@Singleton
public class AllSitesFMCfg extends Configuration{
	
	@Inject
	public AllSitesFMCfg(DefaultObjectWrapper dow,SiteConfigService scs,DbTplLoader dbLoader) throws IOException {
		FileTemplateLoader fileLoader = null;
		if(scs.getSiteRoot() != null && !scs.getSiteRoot().isEmpty()){
			fileLoader = new FileTemplateLoader(new File(scs.getSiteRoot()));
		}
		TemplateLoader[] loaders = new TemplateLoader[]{fileLoader,dbLoader};
		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
		setTemplateLoader(mtl);
		setObjectWrapper(dow);
		setLocalizedLookup(false);
//		setLocale(Locale.CHINA);
		setDefaultEncoding("utf8");
	}
	
//	@Inject
//	public AllSitesFMCfg(DefaultObjectWrapper dow,FileTplLoader fileLoader,DbTplLoader dbLoader) throws IOException {
//		TemplateLoader[] loaders = new TemplateLoader[]{fileLoader,dbLoader};
//		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
//		setTemplateLoader(mtl);
//		setObjectWrapper(dow);
////		setLocale(Locale.CHINA);
//		setDefaultEncoding("utf8");
//	}

}
