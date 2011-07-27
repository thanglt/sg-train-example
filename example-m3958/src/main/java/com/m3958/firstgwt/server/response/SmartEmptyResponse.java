package com.m3958.firstgwt.server.response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartResponseFieldName;

public class SmartEmptyResponse {
	
	private JSONObject res = new JSONObject();

	public SmartEmptyResponse(){
		JSONObject resObject = new JSONObject();
		resObject.element(SmartResponseFieldName.STATUS, ServerErrorCode.NO_ERROR);
		resObject.element(SmartResponseFieldName.TOTAL_ROWS, 0);
		resObject.element(SmartResponseFieldName.START_ROW, 0);
		resObject.element(SmartResponseFieldName.END_ROW, 0);
		
		JSONArray data = new JSONArray();
		resObject.element(SmartResponseFieldName.DATA, data);
		res.element(SmartResponseFieldName.RESPONSE, resObject);
	}

	@Override
	public String toString(){
		return res.toString();
	}
}
