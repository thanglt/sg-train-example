package com.m3958.firstgwt.client.types;


public enum DepartmentField {
	NAME("name","部门名称",""),BZ("bz","备注",""),SHEQU_CONTAINER("shequContainer","社区容器","");
	
	String ename;
	String cname;
	String alias;
	
	DepartmentField(String ename,String cname,String alias) {
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
	
    public static DepartmentField getFieldEnumByEname(String ename){
    	DepartmentField result = null;
    	for(DepartmentField lf : DepartmentField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static DepartmentField getFieldEnumByCname(String cname){
    	DepartmentField result = null;
    	for(DepartmentField lf : DepartmentField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static DepartmentField getFieldEnumByAlias(String alias){
    	DepartmentField result = null;
    	for(DepartmentField lf : DepartmentField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}
