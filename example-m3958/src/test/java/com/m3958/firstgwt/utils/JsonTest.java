package com.m3958.firstgwt.utils;

import org.junit.Test;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class JsonTest {
	
	@Test
	public void tt(){
		
		JSONObject jo = JSONParser.parse("{abc:\"abc\"}").isObject();
		jo.get("abc");
//		Assert.assertEquals(3, Integer.parseInt(ja.get(2).isString().toString()));
	}

}
