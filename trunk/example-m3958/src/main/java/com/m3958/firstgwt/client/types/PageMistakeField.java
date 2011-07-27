package com.m3958.firstgwt.client.types;


public enum PageMistakeField{
	USERNAME("username","用户名称",""),LXFS("lxfs","联系方式",""),PAGE_URL("pageUrl","路径","")
	,WEBSITE("webSite","",""),ERROR_TYPE("errorType","错误类型",""),DESCRIPTION("description","描述",""),
	FIXED("fixed","修复否","");
	
	String ename;
	String cname;
	String alias;
	
	PageMistakeField(String ename,String cname,String alias) {
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
	
    public static PageMistakeField getFieldEnumByEname(String ename){
    	PageMistakeField result = null;
    	for(PageMistakeField lf : PageMistakeField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static PageMistakeField getFieldEnumByCname(String cname){
    	PageMistakeField result = null;
    	for(PageMistakeField lf : PageMistakeField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static PageMistakeField getFieldEnumByAlias(String alias){
    	PageMistakeField result = null;
    	for(PageMistakeField lf : PageMistakeField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}