package com.m3958.firstgwt.client.types;


public enum GroupField {
	USERS("users","","");
	
	String ename;
	String cname;
	String alias;
	
	GroupField(String ename,String cname,String alias) {
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
	
    public static GroupField getFieldEnumByEname(String ename){
    	GroupField result = null;
    	for(GroupField lf : GroupField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static GroupField getFieldEnumByCname(String cname){
    	GroupField result = null;
    	for(GroupField lf : GroupField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static GroupField getFieldEnumByAlias(String alias){
    	GroupField result = null;
    	for(GroupField lf : GroupField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}
