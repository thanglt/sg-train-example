package com.m3958.firstgwt.client.types;

public enum ObjectClassNameField {
	ENAME("ename","对象类名称",""),CNAME("cname","中文名称",""),DAO_NAME("daoName","dao名称",""),
	CHECKER_NAME("checkerName","checker名称","");
	
	String ename;
	String cname;
	String alias;
	
	ObjectClassNameField(String ename,String cname,String alias) {
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
	
    public static ObjectClassNameField getFieldEnumByEname(String ename){
    	ObjectClassNameField result = null;
    	for(ObjectClassNameField lf : ObjectClassNameField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ObjectClassNameField getFieldEnumByCname(String cname){
    	ObjectClassNameField result = null;
    	for(ObjectClassNameField lf : ObjectClassNameField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ObjectClassNameField getFieldEnumByAlias(String alias){
    	ObjectClassNameField result = null;
    	for(ObjectClassNameField lf : ObjectClassNameField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}
