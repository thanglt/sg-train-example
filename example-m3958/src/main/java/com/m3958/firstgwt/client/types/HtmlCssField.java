package com.m3958.firstgwt.client.types;


public enum HtmlCssField{
	CONTENT("content","内容",""),CONTENT_TYPE("contentType","内容类型",""),
	CONTENT_VERSION("contentVersion","内容版本",""),CONTRIBUTOR("contributor","贡献者网站地址",""),DESCRIPTION("description","描述","");
	
	String ename;
	String cname;
	String alias;
	
	HtmlCssField(String ename,String cname,String alias) {
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
	
    public static HtmlCssField getFieldEnumByEname(String ename){
    	HtmlCssField result = null;
    	for(HtmlCssField lf : HtmlCssField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HtmlCssField getFieldEnumByCname(String cname){
    	HtmlCssField result = null;
    	for(HtmlCssField lf : HtmlCssField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HtmlCssField getFieldEnumByAlias(String alias){
    	HtmlCssField result = null;
    	for(HtmlCssField lf : HtmlCssField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}