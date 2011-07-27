package com.m3958.firstgwt.client.types;


public enum HmessageReceiverField{
	RECEIVER("receiver","接收者",""),HMESSAGE("hMessage","消息",""),STATUS("status","状态",""),READ_TIME("readTime","阅读时间",""),SENDER("senderName","发送者",""),
	TITLE("title","标题",""),MSG_BODY("message","内容","");
	
	String ename;
	String cname;
	String alias;
	
	HmessageReceiverField(String ename,String cname,String alias) {
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
	
    public static HmessageReceiverField getFieldEnumByEname(String ename){
    	HmessageReceiverField result = null;
    	for(HmessageReceiverField lf : HmessageReceiverField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HmessageReceiverField getFieldEnumByCname(String cname){
    	HmessageReceiverField result = null;
    	for(HmessageReceiverField lf : HmessageReceiverField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HmessageReceiverField getFieldEnumByAlias(String alias){
    	HmessageReceiverField result = null;
    	for(HmessageReceiverField lf : HmessageReceiverField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}