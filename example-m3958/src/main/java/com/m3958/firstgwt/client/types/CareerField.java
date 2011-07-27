package com.m3958.firstgwt.client.types;


public enum CareerField{
	START("start","开始日期",""),END("end","结束日期",""),DANWEI("danwei","单位",""),ZHIWU("zhiwu","职务",""),
	BZ("bz","备注","");
	
	String ename;
	String cname;
	String alias;
	
	CareerField(String ename,String cname,String alias) {
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
	
    public static CareerField getFieldEnumByEname(String ename){
    	CareerField result = null;
    	for(CareerField lf : CareerField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static CareerField getFieldEnumByCname(String cname){
    	CareerField result = null;
    	for(CareerField lf : CareerField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static CareerField getFieldEnumByAlias(String alias){
    	CareerField result = null;
    	for(CareerField lf : CareerField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}