package com.m3958.firstgwt.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SmartClientCriteria {

	private Map<String, String> stringFieldMap = new HashMap<String, String>();
	private Map<String,List<String>> dateFieldMap = new HashMap<String, List<String>>();
	private Map<String,String> booleanFieldMap = new HashMap<String, String>();
	
	public Map<String, String> getBooleanFieldMap() {
		return booleanFieldMap;
	}

	public void setBooleanFieldMap(Map<String, String> booleanFieldMap) {
		this.booleanFieldMap = booleanFieldMap;
	}

	public Map<String, List<String>> getDateFieldMap() {
		return dateFieldMap;
	}

	public void setDateFieldMap(Map<String, List<String>> dateFieldMap) {
		this.dateFieldMap = dateFieldMap;
	}

	public static enum OperationType{
		
	}

	
	public SmartClientCriteria(String[] dss){
		for (int i = 0; i < dss.length; i++) {
			processOneCriteria(dss[i]);
		}
	}
	
	public void processOneCriteria(String ds){
//		{"fieldName":"email","operator":"iContains","value":"uuu"}
//		{"fieldName":"createdAt","operator":"lessThan","value":"2010-06-27T16:00:00"}]}
//		{"_constructor":"AdvancedCriteria","operator":"and","criteria":[{"fieldName":"birthDay","operator":"greaterThan","value":"2010-06-07"},{"fieldName":"birthDay","operator":"lessThan","value":"2010-06-21"}]}
		JSONObject jo = JSONObject.fromObject(ds);
		String _constructor = null;
		
		try {
			_constructor = jo.getString("_constructor");
		} catch (Exception e){}
		
		if(_constructor == null){
			processSimpeCriteria(jo);
		}else if("AdvancedCriteria".equals(_constructor)){
			JSONArray jos = jo.getJSONArray("criteria");
			for (int i = 0; i < jos.size(); i++) {
				processSimpeCriteria(jos.getJSONObject(i));
			}
		}

	}
	
	private void processSimpeCriteria(JSONObject jo){
		String operator = jo.getString("operator");
		if("iContains".equals(operator)){
			stringFieldMap.put(jo.getString("fieldName"), jo.getString("value"));
		}else{
			processOtherField(jo);
		}
	}
	

	private void processOtherField(JSONObject jo) {
		String value = jo.getString("value");
		if("false".equals(value) || "true".equals(value)){
			processBooleanField(jo);
		}else if(StringUtils.isDateString(value) || StringUtils.isTimeStampString(value)){
			processDateField(jo);
		}else if(value.startsWith("{")){
			processOneCriteria(value);
		}else{
			stringFieldMap.put(jo.getString("fieldName"), jo.getString("value"));
		}
	}

	private void processBooleanField(JSONObject jo) {
		String fieldName = jo.getString("fieldName");
		String value = jo.getString("value");
		booleanFieldMap.put(fieldName,value);
		
	}

	private void processDateField(JSONObject jo) {
		String fieldName = jo.getString("fieldName");
		String value = jo.getString("value");
		String operator = jo.getString("operator");
		String lg = " < ";
		if("greaterThan".equals(operator)){
			lg = " > ";
		}
		if(value.indexOf("T") == -1){
			addToDateFieldMap(fieldName, lg + "{d '"+ value + "'} ");
		}else{
			value = value.replace('T', ' ');
			addToDateFieldMap(fieldName, lg + "{ts '"+ value + "'} ");
		}
		
	}
	
	private void addToDateFieldMap(String fieldName,String value){
		if(dateFieldMap.get(fieldName) == null){
			dateFieldMap.put(fieldName, new ArrayList<String>());
		}
		dateFieldMap.get(fieldName).add(value);
	}

	public String[] getStrings(){
		return null;
	}


	public void setStringFieldMap(Map<String, String> stringFieldMap) {
		this.stringFieldMap = stringFieldMap;
	}

	public Map<String, String> getStringFieldMap() {
		return stringFieldMap;
	}

}
