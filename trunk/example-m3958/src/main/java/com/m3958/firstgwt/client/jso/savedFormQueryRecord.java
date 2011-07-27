package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.m3958.firstgwt.client.types.SavedFormQueryField;

public class savedFormQueryRecord extends BaseRecord{
	public savedFormQueryRecord(){}
    public savedFormQueryRecord(JavaScriptObject jsObj) {
        super(jsObj);
    }
    
    public String getName(){
    	return getAttributeAsString(SavedFormQueryField.NAME.getEname());
    }
    
    public String getQueryString(){
    	return getAttributeAsString(SavedFormQueryField.QUERY_STRING.getEname());
    }
    
    public void setName(String name){
    	setAttribute(SavedFormQueryField.NAME.getEname(), name);
    }
    
    public void setQueryString(String qs){
    	setAttribute(SavedFormQueryField.QUERY_STRING.getEname(), qs);
    }
}
