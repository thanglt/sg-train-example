package com.m3958.firstgwt.client.types;


public enum RoleField{
	ORNAME("orname","对象角色名",""),CNAME("cname","中文名",""),users("users","关联用户",""),permissions("permissions","具有权限","");
	
	String ename;
	String cname;
	String alias;
	
	RoleField(String ename,String cname,String alias) {
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
	
    public static RoleField getFieldEnumByEname(String ename){
    	RoleField result = null;
    	for(RoleField lf : RoleField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static RoleField getFieldEnumByCname(String cname){
    	RoleField result = null;
    	for(RoleField lf : RoleField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static RoleField getFieldEnumByAlias(String alias){
    	RoleField result = null;
    	for(RoleField lf : RoleField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}