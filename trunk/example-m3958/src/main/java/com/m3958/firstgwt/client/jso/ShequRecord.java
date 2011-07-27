package com.m3958.firstgwt.client.jso;


import com.google.gwt.core.client.JavaScriptObject;
import com.m3958.firstgwt.client.types.ShequField;


public class ShequRecord extends BaseRecord{
	public ShequRecord(){}
	
    public ShequRecord(JavaScriptObject jsObj) {
        super(jsObj);
    }
    
    public String getName(){
    	return getAttributeAsString(ShequField.NAME.getEname());
    }
    
    public String getDizhi(){
    	return getAttributeAsString(ShequField.DIZHI.getEname());
    }
    
    public String getShouji(){
    	return getAttributeAsString(ShequField.SHOUJI.getEname());
    }
    
    public String getDianhua(){
    	return getAttributeAsString(ShequField.DIANHUA.getEname());
    }
    
    public String getSqfzr(){
    	return getAttributeAsString(ShequField.SQFZR.getEname());
    }
    
	
}
