package com.m3958.firstgwt.client.utils;

import com.google.gwt.json.client.JSONArray;
import com.smartgwt.client.data.Record;

public class JSONAndRecord {
	public static Record[] jsonArrayToRecords(JSONArray ja){
		Record[] rs = new Record[ja.size()];
		for(int i = 0;i < ja.size();i++){
			rs[i] = new Record(ja.get(i).isObject().getJavaScriptObject());
		}
		return rs;
	}
}
