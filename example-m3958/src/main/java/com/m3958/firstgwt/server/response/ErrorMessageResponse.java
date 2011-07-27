package com.m3958.firstgwt.server.response;

import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.SmartResponseFieldName;
import com.m3958.firstgwt.session.ErrorMessages;

public class ErrorMessageResponse {
	
	private int ERROR_RESPONSE_STATUS_CODE = - 110;
	
	private JSONObject res = new JSONObject().element(
			SmartResponseFieldName.RESPONSE, new JSONObject());//.element(SmartResponseFieldName.DATA, new JSONArray()));
	
	private ErrorMessages ems;

	@Inject
	public ErrorMessageResponse(ErrorMessages ems){
		this.setEms(ems);
		res.getJSONObject(SmartResponseFieldName.RESPONSE).element(SmartResponseFieldName.DATA,ems.getJsArray());//.add(errorRes);
		res.getJSONObject(SmartResponseFieldName.RESPONSE).element(SmartResponseFieldName.STATUS, ERROR_RESPONSE_STATUS_CODE);
		res.getJSONObject(SmartResponseFieldName.RESPONSE).element(SmartResponseFieldName.TOTAL_ROWS, 0);
		res.getJSONObject(SmartResponseFieldName.RESPONSE).element(SmartResponseFieldName.START_ROW, 0);
		res.getJSONObject(SmartResponseFieldName.RESPONSE).element(SmartResponseFieldName.END_ROW, 0);
	}
	
	public boolean hasError(){
		return !getEms().isEmpty();
	}
	
	
	@Override
	public String toString(){
		return res.toString();
	}


	public void setEms(ErrorMessages ems) {
		this.ems = ems;
	}


	public ErrorMessages getEms() {
		return ems;
	}
	
}
