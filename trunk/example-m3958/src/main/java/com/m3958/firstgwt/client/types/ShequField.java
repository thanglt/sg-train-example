package com.m3958.firstgwt.client.types;


public enum ShequField{
	NAME("name","社区名称",""),DIZHI("dizhi","办公地址",""),SQFZR("sqfzr","社区负责人",""),DIANHUA("dianhua","电话",""),SHOUJI("shouji","负责人手机",""),
	BZ("bz","备注",""),DEPARTMENT("department","",""),DEPARTMENT_ID("departmentId","","");
	
	String ename;
	String cname;
	String alias;
	
	ShequField(String ename,String cname,String alias) {
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
	
    public static ShequField getFieldEnumByEname(String ename){
    	ShequField result = null;
    	for(ShequField lf : ShequField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ShequField getFieldEnumByCname(String cname){
    	ShequField result = null;
    	for(ShequField lf : ShequField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ShequField getFieldEnumByAlias(String alias){
    	ShequField result = null;
    	for(ShequField lf : ShequField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}