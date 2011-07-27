package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.m3958.firstgwt.client.types.AssetField;

public class AssetRecord extends BaseRecord{
	public AssetRecord(){}
	
    public AssetRecord(JavaScriptObject jsObj) {
        super(jsObj);
    }
    
    public String getFilePath(){
    	return getAttributeAsString(AssetField.FILE_PATH.getEname());
    }
    
    public String getFileId(){
    	return getAttributeAsString(AssetField.FILE_ID.getEname());
    }
    
    public int getFileSize(){
    	return getAttributeAsInt(AssetField.FILE_SIZE.getEname());
    }
    
    public String getOriginName(){
    	return getAttributeAsString(AssetField.ORIGIN_NAME.getEname());
    }
    
    public String getDescription(){
    	return getAttributeAsString(AssetField.DESCRIPTION.getEname());
    }

}
