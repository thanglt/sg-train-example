package com.m3958.firstgwt.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

import org.apache.commons.beanutils.PropertyUtils;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Lgb;

public class TestBeanDataSource implements JRDataSource,JRRewindableDataSource{
	
	public static JRDataSource getJrds(){
		return new TestBeanDataSource();
	}
	
	private List<Lgb> bms;
	
	private BaseModel b;
	
	private int i = 0;
	
	PropertyUtils pu = new PropertyUtils();
	
	
	public TestBeanDataSource(){
		bms = new ArrayList<Lgb>();
		Lgb l = new Lgb();
		l.setXm("a");
		l.setSr(new Date());
		bms.add(l);
	}
	

	@Override
	public Object getFieldValue(JRField jrf) throws JRException {
		try {
			return PropertyUtils.getProperty(b, jrf.getName());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean next() throws JRException {
		if(i < bms.size()){
			b = bms.get(i);
			i++;
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public void moveFirst() throws JRException {
		i = 0;
	}

}
