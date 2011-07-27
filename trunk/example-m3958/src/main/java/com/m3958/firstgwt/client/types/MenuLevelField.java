package com.m3958.firstgwt.client.types;



public enum MenuLevelField{
	NAME("name","菜单组合名",""),MENUITEMS("menuitems","","");
	
	String ename;
	String cname;
	String alias;
	
	MenuLevelField(String ename,String cname,String alias) {
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
	
    public static MenuLevelField getFieldEnumByEname(String ename){
    	MenuLevelField result = null;
    	for(MenuLevelField lf : MenuLevelField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static MenuLevelField getFieldEnumByCname(String cname){
    	MenuLevelField result = null;
    	for(MenuLevelField lf : MenuLevelField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static MenuLevelField getFieldEnumByAlias(String alias){
    	MenuLevelField result = null;
    	for(MenuLevelField lf : MenuLevelField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

