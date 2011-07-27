package com.m3958.firstgwt.client.types;



public enum MenuItemField{
	TITLE("title","名称",""),UNIQUE_NAME("uniqueName","uniqueName",""),MENUITEMCAT("menuItemCat","菜单组",""),EXTRA_MENULEVEL_ID("mlid","",""),
	MENULEVELS("menuLevels","",""),CUSTOM_ACTION("ca","",""),NAMEDQUERY_ID("_nqid","","");
	
	String ename;
	String cname;
	String alias;
	
	MenuItemField(String ename,String cname,String alias) {
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
	
    public static MenuItemField getFieldEnumByEname(String ename){
    	MenuItemField result = null;
    	for(MenuItemField lf : MenuItemField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static MenuItemField getFieldEnumByCname(String cname){
    	MenuItemField result = null;
    	for(MenuItemField lf : MenuItemField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static MenuItemField getFieldEnumByAlias(String alias){
    	MenuItemField result = null;
    	for(MenuItemField lf : MenuItemField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

