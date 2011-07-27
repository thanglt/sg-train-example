package com.m3958.firstgwt.client.types;


public enum WebHostField{
	NAME("name","域名",""),SITE_PATH("sitePath","站点路径",""),THEME("theme","風格",""),WEBSITE("webSite","","");
	
	String ename;
	String cname;
	String alias;
	
	WebHostField(String ename,String cname,String alias) {
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
	
    public static WebHostField getFieldEnumByEname(String ename){
    	WebHostField result = null;
    	for(WebHostField lf : WebHostField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static WebHostField getFieldEnumByCname(String cname){
    	WebHostField result = null;
    	for(WebHostField lf : WebHostField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static WebHostField getFieldEnumByAlias(String alias){
    	WebHostField result = null;
    	for(WebHostField lf : WebHostField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}