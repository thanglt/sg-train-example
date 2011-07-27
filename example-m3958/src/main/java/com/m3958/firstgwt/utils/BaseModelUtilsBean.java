package com.m3958.firstgwt.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.service.ParaUtilService;

public class BaseModelUtilsBean {
	@SuppressWarnings("unused")
	private BeanUtilsBean bub;
	@SuppressWarnings("unused")
	private ConvertUtilsBean cub;
	private PropertyUtilsBean pub;
	
	@Inject
	private com.google.inject.Provider<EntityManager> emp;
	
	@Inject
	private ParaUtilService paraUtilService;
	
	@Inject
	public BaseModelUtilsBean(BeanUtilsBean bub,ConvertUtilsBean cub,PropertyUtilsBean pub) {
		this.bub = bub;
		this.cub = cub;
		this.pub = pub;
	}
	
	public void copyProperties(BaseModel dest,BaseModel newModel) throws IllegalAccessException, InvocationTargetException{
		PropertyDescriptor[] pds = pub.getPropertyDescriptors(dest);
		for(PropertyDescriptor pd: pds){
			//如果字段是可以赋值的，并且beanmap里面提供了值，那么就复制它。
			String n = pd.getName();
			if(n == null)continue;
//			if(n != null){
				try {
					Object value = pub.getProperty(newModel, n);//如果新对象是null，说明不是删除的本意，删除必须是提供空值。
					if(value == null)continue;
					Class c = pd.getPropertyType();
					if("version".equals(pd.getName()))continue;
					if(c == String.class || c == Integer.class ||
							c == int.class || c == Date.class || 
							c == Boolean.class || c == boolean.class || 
							c == byte[].class ||c == float.class || 
							c == double.class||c == Float.class || 
							c == Double.class || Enum.class.isAssignableFrom(c)){
						pub.setProperty(dest, n, pub.getProperty(newModel, n));
					}else{
//						if(pub.getProperty(newModel, n + "Id") != null && !"parent".equals(n)){
//							pub.setProperty(dest, n, pub.getProperty(newModel, n));
//						}
						;
					}
				} catch (Exception e) {}
//			}
		}		
	}
	
	
	//这里需要用到ropertyDescriptor，因为需要知道目标basemodel的属性的值的类型!
	@SuppressWarnings("unchecked")
	public void copyProperties(Object ob, Map<String, String> paraMap) throws IllegalAccessException, InvocationTargetException{
		PropertyDescriptor[] pds = pub.getPropertyDescriptors(ob);
		for(PropertyDescriptor pd: pds){
			if("version".equals(pd.getName()))continue;
			String n = pd.getName();
			if(n == null)continue;
			String value = paraMap.get(n);//如果value是null，那么就是没有提供值，不应该计算。如果删除的话，应该是空。
			String ovalue = paraMap.get(n + "Id");
			if(value == null || "null".equals(value)){
				if(ovalue == null || "null".equals(ovalue)){
					continue;
				}
			}
//			if(n != null){
				try {
					Class c = pd.getPropertyType();
					if(c == String.class){
						pub.setProperty(ob, n, value);
					}else if(c == Integer.class || c == int.class){
						pub.setProperty(ob, n, Integer.parseInt(value));
					}else if(c == Float.class || c == float.class){
						pub.setProperty(ob, n, Float.parseFloat(value));
					}else if(c == Boolean.class || c == boolean.class){
						boolean b = paraUtilService.getBooleanValueObject(value);
						pub.setProperty(ob, n, b);
					}else if(c == Date.class){
						pub.setProperty(ob, n, paraUtilService.parseDate(value));
					}else if(Enum.class.isAssignableFrom(c)){
						pub.setProperty(ob, n, Enum.valueOf(c, value.toUpperCase()));
					}else if(BaseModel.class.isAssignableFrom(c)){
//						String ovalue = paraMap.get(n + "Id");
						if("parent".equals(n))continue;
						int oid = paraUtilService.getIdValue(ovalue);
						if(oid != SmartConstants.NONE_EXIST_MODEL_ID){
							BaseModel bbmm = emp.get().find(c, oid);
							pub.setProperty(ob, n, bbmm);
						}
					}
					//只有get，没有set的属性。可能是为了方便程序的逻辑。
				} catch (Exception e) {
//					e.printStackTrace();
				}
//			}
		}
	}
}
