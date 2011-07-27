package com.m3958.firstgwt.service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.model.Ftl;


import freemarker.cache.TemplateLoader;


@Singleton
public class DbTplLoader implements TemplateLoader{
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;

	@Override
	public void closeTemplateSource(Object arg0) throws IOException {
		;
	}

	@Override
	public Object findTemplateSource(String tplName) throws IOException {
		String[] ss = tplName.split("/");
		if(ss.length == 0)return null;
		if(ss[ss.length - 1].contains("_"))return null;
		Query q = emp.get().createNamedQuery(Ftl.NamedQueries.FIND_BY_NAME);
		q.setParameter("name", ss[ss.length-1]);
		Ftl ftl = null;
		try {
			ftl = (Ftl) q.getSingleResult();
		} catch (Exception e) {}
		return ftl;
	}

	@Override
	public long getLastModified(Object ftl) {
		return ((Ftl)ftl).getUpdatedAt().getTime();
	}

	@Override
	public Reader getReader(Object ftl, String encode) throws IOException {
		return new StringReader(((Ftl)ftl).getFtl());
	}

}
