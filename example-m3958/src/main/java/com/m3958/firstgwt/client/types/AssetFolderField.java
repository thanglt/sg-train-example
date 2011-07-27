package com.m3958.firstgwt.client.types;


public enum AssetFolderField {
	NAME("name","目录名称",""),ASSETS("assets","","");
	
	String ename;
	String cname;
	String alias;
	
	AssetFolderField(String ename,String cname,String alias) {
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
	
    public static AssetFolderField getFieldEnumByEname(String ename){
    	AssetFolderField result = null;
    	for(AssetFolderField lf : AssetFolderField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static AssetFolderField getFieldEnumByCname(String cname){
    	AssetFolderField result = null;
    	for(AssetFolderField lf : AssetFolderField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static AssetFolderField getFieldEnumByAlias(String alias){
    	AssetFolderField result = null;
    	for(AssetFolderField lf : AssetFolderField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}
