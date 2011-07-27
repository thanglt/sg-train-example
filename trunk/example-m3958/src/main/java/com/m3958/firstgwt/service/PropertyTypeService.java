package com.m3958.firstgwt.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
@SuppressWarnings("unchecked")
public class PropertyTypeService {
	
	@Inject
	Injector injector;
	
	private Map<String, Class> types = new HashMap<String, Class>();
	
	public Class getPropertyType(String modelName,String propertyName){
		if(types.get(modelName + propertyName) != null){
			return types.get(modelName + propertyName);
		}else{
			return putToMap(modelName,propertyName);
		}
	}


	private Class putToMap(String modelName, String propertyName) {
		PropertyUtilsBean pub = new PropertyUtilsBean();
		Class c = null;
		try {
			Object o = injector.getInstance(Class.forName(modelName));
			c = pub.getPropertyType(o, propertyName);
		} catch (ClassNotFoundException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (NoSuchMethodException e) {
		}
		if(c != null){
			types.put(modelName + propertyName, c);
			return c;
		}
		return null;
	}
}
