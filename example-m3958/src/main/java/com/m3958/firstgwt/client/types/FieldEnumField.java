package com.m3958.firstgwt.client.types;


public enum FieldEnumField{
	FIELD_TYPE("fieldType","字段名称",""),FIELD_VALUE("fieldValue","可能值",""),POSITION("position","排序值","");
	
	String ename;
	String cname;
	String alias;
	
	FieldEnumField(String ename,String cname,String alias) {
		this.ename = ename;
		this.cname = cname;
		this.alias = alias;
	}
	public String getEname(){
		return this.ename;
	}
	public String getCname(){
		return this.cname;
	}
	public String getAlias(){
		return this.alias;
	}
	
    public static FieldEnumField getFieldEnumByEname(String ename){
    	FieldEnumField result = null;
    	for(FieldEnumField lf : FieldEnumField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static FieldEnumField getFieldEnumByCname(String cname){
    	FieldEnumField result = null;
    	for(FieldEnumField lf : FieldEnumField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static FieldEnumField getFieldEnumByAlias(String alias){
    	FieldEnumField result = null;
    	for(FieldEnumField lf : FieldEnumField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}