package com.m3958.firstgwt.server.response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.m3958.firstgwt.client.types.GwtResponseStatus;

public class GwtRpcResponse{
	
	private JSONObject jsonObject = new JSONObject();
	
	public GwtRpcResponse() {
		jsonObject.element("status", GwtResponseStatus.SUCCESS.getValue());
		jsonObject.element("data", new JSONArray());
		jsonObject.element("others", new JSONObject());
	}
	
	public GwtRpcResponse(GwtResponseStatus status){
		jsonObject.element("status", status.getValue());
	}

	
	public GwtRpcResponse(GwtResponseStatus status,JSONArray ja){
		jsonObject.element("status", status.getValue());
		jsonObject.element("data", ja);
	}
	
	public GwtRpcResponse(GwtResponseStatus status,JSONObject ja){
		jsonObject.element("status", status.getValue());
		jsonObject.element("data", ja);
	}

	
	public GwtRpcResponse(GwtResponseStatus status,JSONArray ja,JSONObject jo){
		jsonObject.element("status", status.getValue());
		jsonObject.element("data", ja);
		jsonObject.element("others", jo);
	}
	
	public GwtRpcResponse(GwtResponseStatus status,JSONObject dataJo,JSONObject jo){
		jsonObject.element("status", status.getValue());
		jsonObject.element("data", dataJo);
		jsonObject.element("others", jo);
	}
	
	
	public GwtRpcResponse setStatus(GwtResponseStatus status){
		jsonObject.element("status", status.getValue());
		return this;
	}
	
	public GwtRpcResponse setData(JSONArray ja){
		jsonObject.element("data", ja);
		return this;
	}
	
	public GwtRpcResponse setData(JSONObject jo){
		jsonObject.element("data", jo);
		return this;
	}
	
	
	public GwtRpcResponse setOthers(JSONObject jo){
		jsonObject.element("others", jo);
		return this;
	}
	
	public String getStringValue(){
		return jsonObject.toString();
	}
	
	@Override
	public String toString(){
		return jsonObject.toString();
	}
}