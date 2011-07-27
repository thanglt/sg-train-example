package com.m3958.firstgwt.client.types;

import java.util.LinkedHashMap;

public enum HeadTailIncludeType {
	BOTH("包含头尾"),HEAD("包含头"),TAIL("包含尾"),NEITHER("不含头尾");
	
	HeadTailIncludeType(String dname){
		this.displayName = dname;
	}
	String displayName;
	public String getDisplayName() {
		return displayName;
	}
	
	private static LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>(); 
	
	static public LinkedHashMap<String, String> getValueMap(){
		if(valueMap.size() != 4){
			valueMap.put(BOTH.toString(), BOTH.getDisplayName());
			valueMap.put(HEAD.toString(), HEAD.getDisplayName());
			valueMap.put(TAIL.toString(), TAIL.getDisplayName());
			valueMap.put(NEITHER.toString(), NEITHER.getDisplayName());
		}
		return valueMap;
	}

}
