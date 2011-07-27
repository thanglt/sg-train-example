package com.m3958.firstgwt.client.types;

import java.util.LinkedHashMap;

public enum DateTimeRangeInclude {
	INCLUDE_BOTH("包含头尾"),INCLUDE_START("包含头"),INCLUDE_END("包含尾"),INCLUDE_NEITHER("不含头尾");
	
	DateTimeRangeInclude(String dname){
		this.displayName = dname;
	}
	String displayName;
	public String getDisplayName() {
		return displayName;
	}
	
	private static LinkedHashMap<String, String> configMap = new LinkedHashMap<String, String>(); 
	
	static public LinkedHashMap<String, String> getConfigMap(){
		if(configMap.size() != 4){
			configMap.put(INCLUDE_BOTH.toString(), INCLUDE_BOTH.getDisplayName());
			configMap.put(INCLUDE_START.toString(), INCLUDE_START.getDisplayName());
			configMap.put(INCLUDE_END.toString(), INCLUDE_END.getDisplayName());
			configMap.put(INCLUDE_NEITHER.toString(), INCLUDE_NEITHER.getDisplayName());
		}
		return configMap;
	}
	
}
