package com.m3958.firstgwt.utils;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

import com.m3958.firstgwt.model.Article;


public class FtlUtil {
	
	private static String[] chineseMonthNums = new String[]{"一","二","三","四","五","六","七","八","九","十","十一","十二"};
	
	private String tplName;
	
	
	public int parseInt(String str,int defaultInt){
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultInt;
		}
	}
	
	public String getChineseMonthNumber(String monthNumStr){
		try {
			int i = Integer.parseInt(monthNumStr);
			return getChineseMonthNumber(i);
		} catch (NumberFormatException e) {
			return "范围错误";
		}
	}
	
	public String getChineseMonthNumber(int monthNum){
		if(monthNum>12 || monthNum<1){
			return "范围错误";
		}else{
			return chineseMonthNums[monthNum - 1];
		}
	}

	
	public String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	public String getListProperty(List<Article> articles,String propertyName,String separator,String size){
		String[] ss = new String[articles.size()];
		int i =0;
		for(Article a : articles){
			if("title".equals(propertyName)){
				ss[i++] = a.getTitle();
			}else if("titleImg".equals(propertyName)){
				ss[i++] = a.getTitleImg().getUrl(size);
			}else if("url".equals(propertyName)){
				ss[i++] = a.getUrl();
			}
		}
		return StringUtils.join(ss, separator);
	}
	
	public String getListProperty(List models,String propertyName,String separator) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Object[] ss = new String[models.size()];
		PropertyUtilsBean pub = new PropertyUtilsBean();
		int i = 0;
		for(Object o : models){
			ss[i++] = pub.getProperty(o, propertyName); 
		}
		return StringUtils.join(ss, separator);
	}
	


	public String getRadios(List modelAry,String selectedId){
		if(modelAry == null || modelAry.isEmpty())return "";
		Object o = modelAry.get(0);
		return "";
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getTplName() {
		return tplName;
	}

}
