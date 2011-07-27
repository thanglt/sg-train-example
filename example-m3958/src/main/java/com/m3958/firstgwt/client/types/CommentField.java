package com.m3958.firstgwt.client.types;



public enum CommentField{
	NICKNAME("nickName","昵称",""),EMAIL("email","email",""),
	MESSAGE("message","内容",""),FOLLOWNUM("followNum","跟贴数",""),TITLE("title","标题","");
	
	String ename;
	String cname;
	String alias;
	
	CommentField(String ename,String cname,String alias) {
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
	
    public static CommentField getFieldEnumByEname(String ename){
    	CommentField result = null;
    	for(CommentField lf : CommentField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static CommentField getFieldEnumByCname(String cname){
    	CommentField result = null;
    	for(CommentField lf : CommentField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static CommentField getFieldEnumByAlias(String alias){
    	CommentField result = null;
    	for(CommentField lf : CommentField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

