package com.m3958.firstgwt.client.types;



public enum AddressField{
	MAIN_ADDRESS("mainAddress","第一地址",""),DIZHI("dizhi","家庭地址",""),SQLXR("sqlxr","社区联系人",""),
	DIANHUA("dianhua","宅电",""),SHOUJI("shouji","手机",""),	BZ("bz","备注",""),SHEQU("shequ","社区","");
	
	String ename;
	String cname;
	String alias;
	
	AddressField(String ename,String cname,String alias) {
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
	
    public static AddressField getFieldEnumByEname(String ename){
    	AddressField result = null;
    	for(AddressField lf : AddressField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static AddressField getFieldEnumByCname(String cname){
    	AddressField result = null;
    	for(AddressField lf : AddressField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static AddressField getFieldEnumByAlias(String alias){
    	AddressField result = null;
    	for(AddressField lf : AddressField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

