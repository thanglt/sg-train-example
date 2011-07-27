package com.m3958.firstgwt.server.response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.m3958.firstgwt.client.types.UploadResponseFields;
import com.m3958.firstgwt.client.types.UploadResponseStatus;

public class UploadResponse{
	
	private JSONObject jsonObject = new JSONObject();
	
	public UploadResponse() {
		jsonObject.element(UploadResponseFields.STATUS.toString(), UploadResponseStatus.SUCCESS);
		jsonObject.element(UploadResponseFields.DATA.toString(), new JSONArray());
		jsonObject.element(UploadResponseFields.OTHERS.toString(), new JSONObject());
	}
	
	public UploadResponse(int status){
		jsonObject.element(UploadResponseFields.STATUS.toString(), status);
	}

	
	public UploadResponse(int status,JSONArray ja){
		jsonObject.element(UploadResponseFields.STATUS.toString(), status);
		jsonObject.element(UploadResponseFields.DATA.toString(), ja);
	}
	
	public UploadResponse(int status,JSONObject ja){
		jsonObject.element(UploadResponseFields.STATUS.toString(), status);
		jsonObject.element(UploadResponseFields.DATA.toString(), ja);
	}

	
	public UploadResponse(int status,JSONArray ja,JSONObject jo){
		jsonObject.element(UploadResponseFields.STATUS.toString(), status);
		jsonObject.element(UploadResponseFields.DATA.toString(), ja);
		jsonObject.element(UploadResponseFields.OTHERS.toString(), jo);
	}
	
	public UploadResponse(int status,JSONObject dataJo,JSONObject jo){
		jsonObject.element(UploadResponseFields.STATUS.toString(), status);
		jsonObject.element(UploadResponseFields.DATA.toString(), dataJo);
		jsonObject.element(UploadResponseFields.OTHERS.toString(), jo);
	}
	
	
	public UploadResponse setStatus(int status){
		jsonObject.element(UploadResponseFields.STATUS.toString(), status);
		return this;
	}
	
	public UploadResponse setData(JSONArray ja){
		jsonObject.element(UploadResponseFields.DATA.toString(), ja);
		return this;
	}
	
	public UploadResponse setData(JSONObject jo){
		jsonObject.element(UploadResponseFields.DATA.toString(), jo);
		return this;
	}
	
	
	public UploadResponse setOthers(JSONObject jo){
		jsonObject.element(UploadResponseFields.OTHERS.toString(), jo);
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