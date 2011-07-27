package com.m3958.firstgwt.client.types;


public enum HelpMessageField{
	HELP_ID("helpId","帮助ID",""),HELP_MESSAGE("helpMessage","帮助内容","");
	
	String ename;
	String cname;
	String alias;
	
	HelpMessageField(String ename,String cname,String alias) {
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
	
    public static HelpMessageField getFieldEnumByEname(String ename){
    	HelpMessageField result = null;
    	for(HelpMessageField lf : HelpMessageField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HelpMessageField getFieldEnumByCname(String cname){
    	HelpMessageField result = null;
    	for(HelpMessageField lf : HelpMessageField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HelpMessageField getFieldEnumByAlias(String alias){
    	HelpMessageField result = null;
    	for(HelpMessageField lf : HelpMessageField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}