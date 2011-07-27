package com.m3958.firstgwt.client.types;



public enum VoteHitField{
	REFERER("referer","引用域名",""),VOTE_IP("voteIp","ip地址","");
	
	String ename;
	String cname;
	String alias;
	
	VoteHitField(String ename,String cname,String alias) {
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
	
    public static VoteHitField getFieldEnumByEname(String ename){
    	VoteHitField result = null;
    	for(VoteHitField lf : VoteHitField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static VoteHitField getFieldEnumByCname(String cname){
    	VoteHitField result = null;
    	for(VoteHitField lf : VoteHitField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static VoteHitField getFieldEnumByAlias(String alias){
    	VoteHitField result = null;
    	for(VoteHitField lf : VoteHitField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

