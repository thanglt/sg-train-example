package com.m3958.firstgwt.client.types;


public enum HouseField{
	ZFXZ("zfxz","住房性质",""),AREA("area","面积",""),STRUCTURE("structure","住房结构",""),
	BZ("bz","备注","");
	
	String ename;
	String cname;
	String alias;
	
	HouseField(String ename,String cname,String alias) {
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
	
    public static HouseField getFieldEnumByEname(String ename){
    	HouseField result = null;
    	for(HouseField lf : HouseField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HouseField getFieldEnumByCname(String cname){
    	HouseField result = null;
    	for(HouseField lf : HouseField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HouseField getFieldEnumByAlias(String alias){
    	HouseField result = null;
    	for(HouseField lf : HouseField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}