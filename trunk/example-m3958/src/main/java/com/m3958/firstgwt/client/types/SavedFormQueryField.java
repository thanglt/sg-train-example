package com.m3958.firstgwt.client.types;


public enum SavedFormQueryField{
	NAME("name","查询名称",""),
	QUERY_STRING("queryString","查询字串","");
	
	String ename;
	String cname;
	String alias;
	
	SavedFormQueryField(String ename,String cname,String alias) {
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
	
    public static SavedFormQueryField getFieldEnumByEname(String ename){
    	SavedFormQueryField result = null;
    	for(SavedFormQueryField lf : SavedFormQueryField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static SavedFormQueryField getFieldEnumByCname(String cname){
    	SavedFormQueryField result = null;
    	for(SavedFormQueryField lf : SavedFormQueryField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static SavedFormQueryField getFieldEnumByAlias(String alias){
    	SavedFormQueryField result = null;
    	for(SavedFormQueryField lf : SavedFormQueryField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}