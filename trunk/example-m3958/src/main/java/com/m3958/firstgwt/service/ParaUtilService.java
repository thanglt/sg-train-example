package com.m3958.firstgwt.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.types.BooleanSearchValue;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.HeadTailIncludeType;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.TextMatchStyle;

@Singleton
public class ParaUtilService {
	
	@Inject
	private PropertyTypeService pts;
	
	@Inject
	private Injector injector;
	
	public Date parseDate(String dateStr) {
		if(dateStr == null)return null;
		int size = dateStr.length();
		
		if(size> 0 && size < 4){
			int age = 0;
			try {
				age = Integer.parseInt(dateStr);
			} catch (NumberFormatException e) {
				return null;
			}
			if(age > 0){
				Calendar c = Calendar.getInstance();
				c.add(Calendar.YEAR, -age);
				return c.getTime();
			}
		}
		HttpServletRequest req = injector.getInstance(HttpServletRequest.class);
		Locale l = req.getLocale();
		String tz = req.getParameter(CommonField.TIMEZONE.getEname());
		TimeZone timezone;
		if(tz == null){
			timezone = TimeZone.getDefault();
		}else{
			timezone = TimeZone.getTimeZone(tz);
		}
		try {
			if(dateStr.contains("-")){
				if(dateStr.indexOf(":") == -1){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",l);
					sdf.setTimeZone(timezone);
					return sdf.parse(dateStr);
				}else{
					SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",l);
					stf.setTimeZone(timezone);
					dateStr = dateStr.replace("T", " ");
					return stf.parse(dateStr);
				}
			}else if(dateStr.contains("/")){
				if(dateStr.indexOf(":") == -1){
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd",l);
					sdf1.setTimeZone(timezone);
					return sdf1.parse(dateStr);
				}else{
					SimpleDateFormat stf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",l);
					stf1.setTimeZone(timezone);
					dateStr = dateStr.replace("T", " ");
					return stf1.parse(dateStr);
				}
			}else{
				if(dateStr.indexOf(":") == -1){
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd",l);
					sdf2.setTimeZone(timezone);
					return sdf2.parse(dateStr);
				}else{
					SimpleDateFormat stf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss",l);
					stf2.setTimeZone(timezone);
					dateStr = dateStr.replace("T", " ");
					return stf2.parse(dateStr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	
	public int getIdValue(String pValue){
		int i = SmartConstants.NONE_EXIST_MODEL_ID;
		try {
			i = Integer.parseInt(pValue);
		} catch (NumberFormatException e) {}
		return i;
	}
	
	public int getIntegerValue(String pValue){
		int i = 0;
		try {
			i = Integer.parseInt(pValue);
		} catch (NumberFormatException e) {}
		return i;
	}
	
	public Boolean getBooleanValueObject(String pValue){
		if(pValue == null)return null;
		if("true".equalsIgnoreCase(pValue) || "1".equalsIgnoreCase(pValue) || "yes".equalsIgnoreCase(pValue) || "是".equalsIgnoreCase(pValue)){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean getBooleanValue(String pValue,boolean defaultValue){
		if(pValue == null)return defaultValue;
		if("true".equalsIgnoreCase(pValue) || "1".equalsIgnoreCase(pValue) || "yes".equalsIgnoreCase(pValue) || "是".equalsIgnoreCase(pValue)){
			return true;
		}else{
			return false;
		}
	}

	
	public Boolean getBooleanValue(String pValue){
		if("true".equalsIgnoreCase(pValue) || "1".equalsIgnoreCase(pValue) || "yes".equalsIgnoreCase(pValue) || "是".equalsIgnoreCase(pValue)){
			return true;
		}else{
			return false;
		}
	}
	

	@SuppressWarnings("rawtypes")
	public String getWhereString(String modelName,Set<String> ignoreWhereFields,Map<String, String> allParas,TextMatchStyle tms){
		String whereString = "";
		for(String pKey : allParas.keySet()){
			if(ignoreWhereFields.contains(pKey))continue;
			if(pKey.startsWith("_"))continue;
			String value = allParas.get(pKey);
			Class c = pts.getPropertyType(modelName,pKey);
			if(c != null  && !"null".equals(value)){
				if(c == String.class){
					whereString += getStrClause(tms,pKey,value);
				}else if(c == int.class || c == Integer.class){
					int i = getIntegerValue(value);
					whereString += getIntClause(pKey,i);
				}else if(c == Date.class){
					whereString += getDateEqualClause(pKey,parseDate(value));
				}else if(c == boolean.class || c == Boolean.class){
					boolean b = getBooleanValueObject(value);
					whereString += getBooleanClause(pKey,b);
				}else if(Enum.class.isAssignableFrom(c)){
					whereString += getEnumClause(modelName,pKey,value);
				}else{
					;
				}
			}
		}
		return whereString;
	}
	
	@SuppressWarnings("rawtypes")
	public String getWhereString(String asObjectName,String modelName,Set<String> ignoreWhereFields,Map<String, String> allParas,TextMatchStyle tms){
		String whereString = "";
		for(String pKey : allParas.keySet()){
			if(ignoreWhereFields.contains(pKey))continue;
			if(pKey.startsWith("_"))continue;
			String value = allParas.get(pKey);
			Class c = pts.getPropertyType(modelName,pKey);
			if(c != null  && !"null".equals(value)){
				if(c == String.class){
					whereString += getStrClause(asObjectName,tms,pKey,value);
				}else if(c == int.class || c == Integer.class){
					int i = getIntegerValue(value);
					whereString += getIntClause(asObjectName,pKey,i);
				}else if(c == Date.class){
					whereString += getDateEqualClause(asObjectName,pKey,parseDate(value));
				}else if(c == boolean.class || c == Boolean.class){
					boolean b = getBooleanValueObject(value);
					whereString += getBooleanClause(asObjectName,pKey,b);
				}else if(Enum.class.isAssignableFrom(c)){
					whereString += getEnumClause(asObjectName,modelName,pKey,value);
				}else{
					;
				}
			}
		}
		return whereString;
	}
	

	public String getBooleanClause(String pKey, Boolean pValue) {
		String s = "";
		s = " AND P." + pKey + " = " + pValue;
		return s;
	}
	
	public String getIntClause(String pKey, int pValue) {
		String s = "";
		s = " AND P." + pKey + " = " + pValue;
		return s;
	}
	
	public String getDateEqualClause(String pKey,Date pValue){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s= "";
		s = " AND P." + pKey + " = " + sdf.format(pValue);
		return s;
	}
	
	public String getBooleanClause(String asObjectName,String pKey, Boolean pValue) {
		String s = "";
		s = " AND " + asObjectName + "." + pKey + " = " + pValue;
		return s;
	}
	
	public String getIntClause(String asObjectName,String pKey, int pValue) {
		String s = "";
		s = " AND " + asObjectName + "." + pKey + " = " + pValue;
		return s;
	}
	
	public String getDateEqualClause(String asObjectName,String pKey,Date pValue){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s= "";
		s = " AND " + asObjectName + "." + pKey + " = " + sdf.format(pValue);
		return s;
	}
	
	public String getFinalWhereString(String whereString) {
		if(whereString.startsWith(" AND")){
			whereString = whereString.substring(4);
		}
		if(whereString.length() > 0){
			whereString = " WHERE " + whereString;
		}
		return whereString;
	}
	
	public String getStrClause(TextMatchStyle tms,String pKey,String pValue) {
		String s = "";
		if(pValue == null || pValue.isEmpty())return s;
		switch (tms) {
		case EXACT:
			s = " AND P." + pKey + " = '" + pValue + "'";
			break;
		case STARTSWITH:
			s = " AND P." + pKey + " LIKE '" + pValue + "%'";
			break;
		case SUBSTRING:
			s = " AND P." + pKey + " LIKE '%" + pValue + "%'";
			break;
		default:
			break;
		}
		return s;
	}
	
	public String getStrClause(String asObjectName,TextMatchStyle tms,String pKey,String pValue) {
		String s = "";
		if(pValue == null || pValue.isEmpty())return s;
		switch (tms) {
		case EXACT:
			s = " AND " + asObjectName + "." + pKey + " = '" + pValue + "'";
			break;
		case STARTSWITH:
			s = " AND " + asObjectName + "." + pKey + " LIKE '" + pValue + "%'";
			break;
		case SUBSTRING:
			s = " AND " + asObjectName + "." + pKey + " LIKE '%" + pValue + "%'";
			break;
		default:
			break;
		}
		return s;
	}

	
	
	@SuppressWarnings("rawtypes")
	public String getAllStrClause(String modelName,Set<String> ignoreWhereFields,Map<String, String> allParas,TextMatchStyle tms){
		String allStrClause = "";
		for(String pKey : allParas.keySet()){
			if(ignoreWhereFields.contains(pKey))continue;
			if(pKey.startsWith("_"))continue;
			Class c = pts.getPropertyType(modelName,pKey);
			String value = allParas.get(pKey);
			if(c != null){
				if(c == String.class){
					allStrClause += getStrClause(tms,pKey,value);
				}
			}
		}
		return allStrClause;	
	}
	
//	public String getEnumClause(Object o,String pKey,String pValue){
//		String s = "";
//		s = " AND P." + pKey + " = " + getPropertyType(o, pKey).getSimpleName() + "." + pValue;
//		return s;
//	}
	
	
	private String getEnumClause(String modelName,String pKey,String pValue){
		String s = "";
		s = " AND P." + pKey + " = " + pts.getPropertyType(modelName, pKey).getSimpleName() + "." + pValue;
		return s;
	}
	
	private String getEnumClause(String asObjectName,String modelName,String pKey,String pValue){
		String s = "";
		s = " AND " + asObjectName + "." + pKey + " = " + pts.getPropertyType(modelName, pKey).getSimpleName() + "." + pValue;
		return s;
	}
	
	@SuppressWarnings("rawtypes")
	public Class getPropertyType(Object o, String propertyName) {
		PropertyUtilsBean pub = new PropertyUtilsBean();
		try {
			return pub.getPropertyType(o, propertyName);
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (NoSuchMethodException e) {
		}
		return null;
	}
	
	/*
	 * 有对应的boolean字段
	 */
	public String getBooleanRadioStr(Object o,String fname){
		String s = "";
		PropertyUtilsBean pub = new PropertyUtilsBean();
		try {
			BooleanSearchValue bsv = (BooleanSearchValue) pub.getProperty(o, fname + "_radio");
			if(bsv == null)return s;
			switch (bsv) {
			case ALL:
				break;
			case NO:
				s = getBooleanClause(fname, false);
				break;
			case YES:
				s = getBooleanClause(fname, true);
				break;
			default:
				break;
			}
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/*
	 * 无对应的boolean字段，通过字段是否为空来决定有无。
	 */
	public String getEmptyRadioStr(Object o,String fname){
		String s = "";
		PropertyUtilsBean pub = new PropertyUtilsBean();
		try {
			BooleanSearchValue bsv = (BooleanSearchValue) pub.getProperty(o, fname + "_radio");
			if(bsv == null)return s;
			switch (bsv) {
			case ALL:
				break;
			case NO:
				s = " AND p." + fname + " IS NULL ";
				break;
			case YES:
				s = " AND p." + fname + " IS NOT NULL ";
				break;
			default:
				break;
			}
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	@SuppressWarnings("deprecation")
	public String getDateBetweenClause(Object o,String fname){
		String s = "";
		PropertyUtilsBean pub = new PropertyUtilsBean();
		try {
			Date start = null;
			Date end = null;
			
			Object starto = pub.getProperty(o, fname + "_start");
			Object endo = pub.getProperty(o, fname + "_end");
			Object htito = pub.getProperty(o, fname + "_htit");
			if(htito == null)return s;
			if((starto == null) && endo == null)return s;
			
			if(starto instanceof Integer){
				if(((Integer)starto) == 0 && ((Integer)endo) == 0){
					return s;
				}
			}
			
			Date today = new Date();
			if(starto != null && (starto.getClass() == int.class || starto.getClass() == Integer.class)){//年龄
				Integer starti = (Integer)starto;
				end = new Date(today.getYear() - starti, today.getMonth(), today.getDate());
			}
			
			if(endo != null && (endo.getClass() == int.class || endo.getClass() == Integer.class)){
				Integer endi = (Integer)endo;
				start = new Date(today.getYear() - endi, today.getMonth(), today.getDate());
			}
			
			if(starto != null && starto.getClass() == Date.class){
				start = (Date) starto;
			}
			
			if(endo != null && endo.getClass() == Date.class){
				end = (Date) endo; 
			}
			
			HeadTailIncludeType htit = (HeadTailIncludeType) pub.getProperty(o, fname + "_htit");
			String t = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(start != null){
				switch (htit) {
				case BOTH:
					t = ">=" + "{d '"+ sdf.format(start) + "'} ";
					s += " AND P." + fname + t;
					break;
				case HEAD:
					t = ">=" + "{d '"+ sdf.format(start) + "'} ";
					s += " AND P." + fname + t;
					break;
				default:
					t = ">" + "{d '"+ sdf.format(start) + "'} ";
					s += " AND P." + fname + t;
				}
			}
			
			if(end != null){
				switch (htit) {
				case BOTH:
					t = "<=" + "{d '"+ sdf.format(end) + "'} ";
					s += " AND P." + fname + t;
					break;
				case TAIL:
					t = "<=" + "{d '"+ sdf.format(end) + "'} ";
					s += " AND P." + fname + t;
					break;
				default:
					t = "<" + "{d '"+ sdf.format(start) + "'} ";
					s += " AND P." + fname + t;
				}				
			}
			return s;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return s;
	}
}
