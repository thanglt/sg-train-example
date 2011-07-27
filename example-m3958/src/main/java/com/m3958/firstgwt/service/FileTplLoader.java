package com.m3958.firstgwt.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.server.types.CharactSetName;

import freemarker.cache.TemplateLoader;


@Singleton
public class FileTplLoader implements TemplateLoader{
	
	@Inject
	private SiteConfigService scs;

	@Override
	public void closeTemplateSource(Object ftl) throws IOException {
		;
	}

	@Override
	public Object findTemplateSource(String tplName) throws IOException {
		File f = new File(scs.getSiteRoot(),tplName);
		if(f.exists() && f.isFile())return f;
		return null;
	}

	@Override
	public long getLastModified(Object ftl) {
		return ((File)ftl).lastModified();
	}

	@Override
	public Reader getReader(Object ftl, String encode) throws IOException {
		return new BufferedReader(new InputStreamReader(new FileInputStream((File)ftl),CharactSetName.UTF_8));
	}

}
