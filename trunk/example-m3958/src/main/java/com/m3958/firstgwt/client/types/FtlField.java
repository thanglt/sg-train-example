package com.m3958.firstgwt.client.types;


public enum FtlField{
	UPDATEDAT("updatedAt","更新时间",""),FTL("ftl","模板内容",""),FTL_HISTORIES("ftlHistories","更新历史",""),
	NAME("name","模板名称",""),DESCRIPTION("description","描述","");
	
	String ename;
	String cname;
	String alias;
	
	FtlField(String ename,String cname,String alias) {
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
	
    public static FtlField getFieldEnumByEname(String ename){
    	FtlField result = null;
    	for(FtlField lf : FtlField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static FtlField getFieldEnumByCname(String cname){
    	FtlField result = null;
    	for(FtlField lf : FtlField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static FtlField getFieldEnumByAlias(String alias){
    	FtlField result = null;
    	for(FtlField lf : FtlField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}