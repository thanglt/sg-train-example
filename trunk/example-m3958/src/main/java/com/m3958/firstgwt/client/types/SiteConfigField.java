package com.m3958.firstgwt.client.types;


public enum SiteConfigField{
	CONFIG_KEY("configKey","配置名称",""),CONFIG_VALUE("configValue","配置值",""),DESCRIPTION("description","描述",""),
	CLIENT_SIDE("clientSide","浏览器端配置","");
	
	String ename;
	String cname;
	String alias;
	
	SiteConfigField(String ename,String cname,String alias) {
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
	
    public static SiteConfigField getFieldEnumByEname(String ename){
    	SiteConfigField result = null;
    	for(SiteConfigField lf : SiteConfigField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static SiteConfigField getFieldEnumByCname(String cname){
    	SiteConfigField result = null;
    	for(SiteConfigField lf : SiteConfigField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static SiteConfigField getFieldEnumByAlias(String alias){
    	SiteConfigField result = null;
    	for(SiteConfigField lf : SiteConfigField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}