package com.m3958.firstgwt.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.persistence.EntityManager;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.m3958.firstgwt.model.JrxmlFile;

public class JrxmlFileChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<JrxmlFile> {
	
	@Inject
	protected com.google.inject.Provider<EntityManager> emp;
	
	@Inject
	Injector injector;

	@Override
	public boolean extraPersistTask(JrxmlFile model) {
		try {
			InputStream in = new ByteArrayInputStream(model.getJrxml().getBytes("UTF-8"));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperCompileManager.compileReportToStream(in, out);
			model.setReport(out.toByteArray());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean extraUpdateTask(JrxmlFile model,JrxmlFile newModel) {
		try {
			InputStream in = new ByteArrayInputStream(model.getJrxml().getBytes("UTF-8"));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperCompileManager.compileReportToStream(in, out);
			model.setReport(out.toByteArray());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		return true;
	}
	

	@Override
	public boolean extraRemoveTask(JrxmlFile model) {
		return true;
		
	}

	@Override
	public boolean afterPersist(JrxmlFile model) {
		return false;
	}

}
