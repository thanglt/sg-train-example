package com.m3958.firstgwt.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.google.inject.Inject;
import com.m3958.firstgwt.dao.FtlDao;
import com.m3958.firstgwt.model.Ftl;

import freemarker.cache.TemplateLoader;

public class DbTemplateLoader implements TemplateLoader{
	
	private static String PREFIX = "ftl";
	
	@Inject
	private FtlDao dao;

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		int ftlId = Integer.parseInt(name.substring(PREFIX.length()));
		return dao.find(ftlId);
	}

	@Override
	public long getLastModified(Object templateSource) {
		 return ((Ftl)templateSource).getUpdatedAt().getTime();
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		StringReader sr = new StringReader(((Ftl)templateSource).getFtl());
		return sr;
	}

}
