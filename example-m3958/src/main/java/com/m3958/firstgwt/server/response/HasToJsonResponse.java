package com.m3958.firstgwt.server.response;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.client.types.SmartResponseFieldName;
import com.m3958.firstgwt.server.types.HasToJson;

public class HasToJsonResponse {
	private JSONObject res = new JSONObject();

	public HasToJsonResponse(List<HasToJson> hasToJsons){
		createResponse(hasToJsons);
	}
	
	
	public HasToJsonResponse(HasToJson hasToJson){
		List<HasToJson> htj = new ArrayList<HasToJson>();
		htj.add(hasToJson);
		createResponse(htj);
	}
	
	public static HasToJsonResponse getEmptyResponse(){
		List<HasToJson> results = new ArrayList<HasToJson>();
		return new HasToJsonResponse(results);
	}
	
	private void createResponse(List<HasToJson> hasToJsons){
		JSONObject resObject = new JSONObject();
		resObject.element(SmartResponseFieldName.STATUS, ServerErrorCode.NO_ERROR);
		resObject.element(SmartResponseFieldName.TOTAL_ROWS, hasToJsons.size());
		resObject.element(SmartResponseFieldName.START_ROW, 0);
		resObject.element(SmartResponseFieldName.END_ROW, hasToJsons.size());
		
		JSONArray data = new JSONArray();
		for(HasToJson hsj:hasToJsons){
			data.add(hsj.toJson());
		}
		resObject.element(SmartResponseFieldName.DATA, data);
		res.element(SmartResponseFieldName.RESPONSE, resObject);
	}

	@Override
	public String toString(){
		return res.toString();
	}
}
