package com.m3958.firstgwt.client.types;


public enum PermissionField{
	NAME("name","权限名称",""),OPCODE("opCode","操作代码","");
	
	String ename;
	String cname;
	String alias;
	
	PermissionField(String ename,String cname,String alias) {
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
	
    public static PermissionField getFieldEnumByEname(String ename){
    	PermissionField result = null;
    	for(PermissionField lf : PermissionField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static PermissionField getFieldEnumByCname(String cname){
    	PermissionField result = null;
    	for(PermissionField lf : PermissionField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static PermissionField getFieldEnumByAlias(String alias){
    	PermissionField result = null;
    	for(PermissionField lf : PermissionField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}