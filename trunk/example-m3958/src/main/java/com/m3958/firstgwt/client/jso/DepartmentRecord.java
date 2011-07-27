package com.m3958.firstgwt.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.m3958.firstgwt.client.types.DepartmentField;


public class DepartmentRecord extends BaseRecord{
	
	public DepartmentRecord(){}
	
    public DepartmentRecord(JavaScriptObject jsObj) {
        super(jsObj);
    }
	
	public String getBz(){
		return getAttribute(DepartmentField.BZ.getEname());
	}
	
	public String getCname(){
		return getAttribute(DepartmentField.NAME.getEname());
	}
	
	public boolean isShequContainer(){
		return getAttributeAsBoolean(DepartmentField.SHEQU_CONTAINER.getEname());
	}
}
