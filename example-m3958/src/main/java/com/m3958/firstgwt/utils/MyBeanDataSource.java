package com.m3958.firstgwt.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

import org.apache.commons.beanutils.PropertyUtils;

import com.m3958.firstgwt.model.BaseModel;

public class MyBeanDataSource implements JRDataSource,JRRewindableDataSource{
	
	
	private List<? extends BaseModel> bms = new ArrayList<BaseModel>();
	
	private BaseModel b;
	
	private int i = 0;
	
	PropertyUtils pu = new PropertyUtils();
	
	public MyBeanDataSource(List<? extends BaseModel> bms){
		if(bms != null)
			this.bms = bms;
	}
	
	public void setBeans(List<? extends BaseModel> bms){
		if(bms != null)
			this.bms = bms;
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
