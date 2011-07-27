package com.m3958.firstgwt.server.response;

import net.sf.json.JSONObject;

public class JsonObjectResponse {
	
	private JSONObject root = new JSONObject();
	
	public JsonObjectResponse(Object o){
		JSONObject responseField = new JSONObject().element("status", 0);
		responseField.element("data", o);
		root.element("response", responseField);
	}
	
	@Override
	public String toString(){
		return root.toString();
	}
	
}
