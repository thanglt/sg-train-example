package com.m3958.firstgwt.client.rpc;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.m3958.firstgwt.client.types.SmartResponseFieldName;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;

public abstract class MyRpcCallback implements RPCCallback{

	@Override
	public void execute(RPCResponse response, Object rawData, RPCRequest request) {
		JSONObject jo = JSONParser.parseStrict((String)rawData).isObject();
		JSONObject resjo = jo.get("response").isObject();
		int status = (int) resjo.get(SmartResponseFieldName.STATUS).isNumber().doubleValue();
		if(status == RPCResponse.STATUS_SUCCESS){
			JSONValue data = resjo.get("data");
			afterSuccess(response, rawData, request, data);
		}else{
			JSONArray ja =  resjo.get("data").isArray();
			
			JSONValue ess = ja.get(0).isObject().get(SmartResponseFieldName.ERROR_MESSAGE);
			if(ess != null){
				SC.warn(ess.isString().stringValue());
			}else{
				SC.warn("发生错误，但是没有错误消息！");
			}
			
		}
		SC.clearPrompt();
	}
	
	public abstract void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data);

}
