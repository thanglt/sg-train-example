package com.m3958.firstgwt.client.types;


public enum StepCareerField{
	START("start","开始日期",""),END("end","结束日期",""),ZYSJ("zysj","主要事迹",""),
	BZ("bz","备注","");
	
	String ename;
	String cname;
	String alias;
	
	StepCareerField(String ename,String cname,String alias) {
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
	
    public static StepCareerField getFieldEnumByEname(String ename){
    	StepCareerField result = null;
    	for(StepCareerField lf : StepCareerField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static StepCareerField getFieldEnumByCname(String cname){
    	StepCareerField result = null;
    	for(StepCareerField lf : StepCareerField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static StepCareerField getFieldEnumByAlias(String alias){
    	StepCareerField result = null;
    	for(StepCareerField lf : StepCareerField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}