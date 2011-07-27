package com.m3958.firstgwt.client.types;


public enum RewardField{
	JLQK("jlqk","奖励情况",""),CFQK("cfqk","处分情况",""),
	BZ("bz","备注","");
	
	String ename;
	String cname;
	String alias;
	
	RewardField(String ename,String cname,String alias) {
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
	
    public static RewardField getFieldEnumByEname(String ename){
    	RewardField result = null;
    	for(RewardField lf : RewardField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static RewardField getFieldEnumByCname(String cname){
    	RewardField result = null;
    	for(RewardField lf : RewardField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static RewardField getFieldEnumByAlias(String alias){
    	RewardField result = null;
    	for(RewardField lf : RewardField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}