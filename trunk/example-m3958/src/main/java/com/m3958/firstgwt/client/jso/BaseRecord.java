package com.m3958.firstgwt.client.jso;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;

public class BaseRecord extends Record{
	
	public BaseRecord(){}
	
    public BaseRecord(JavaScriptObject jsObj) {
        super(jsObj);
    }
	
	public int getId(){
		return getAttributeAsInt("id");
	}
	
	public int getVersion(){
		return getAttributeAsInt("version");
	}
	
	public Date getCreatedAt(){
		return getAttributeAsDate("createdAt");
	}
}
