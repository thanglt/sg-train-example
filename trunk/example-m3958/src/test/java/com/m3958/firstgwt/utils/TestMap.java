package com.m3958.firstgwt.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestMap {
	
	private String mapToJsonStr(Map<String, String> m){
		String s = "{";
		for (String key : m.keySet()) {
			s += "\"" + key + "\":" + "\"" + m.get(key) +  "\",";
		}
		
		if(s.endsWith(",")){
			s = s.substring(0,s.length() -1);
		}
		
		s += "}";
		return s;
	}
	
	private Map<String, String> map;
	
	@Before
	public void setup(){
		map = new HashMap<String, String>();
		map.put("a", "b");
		map.put("c", "d");
	}
	
	
	@Test
	public void tm(){
		Assert.assertEquals("{\"a\":\"b\",\"c\":\"d\"}", mapToJsonStr(map));
	}
}
