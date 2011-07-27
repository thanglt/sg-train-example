package com.m3958.firstgwt.client.types;


public enum FeedbackField{
	FEEDBACK_TYPE("feedbackType","反馈类型",""),
	FEEDBACK_STATUS("feedbackStatus","处理状态",""),CONTENT("content","内容",""),REPLY_CONTENT("replyContent","回复","");
	
	String ename;
	String cname;
	String alias;
	
	FeedbackField(String ename,String cname,String alias) {
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
	
    public static FeedbackField getFieldEnumByEname(String ename){
    	FeedbackField result = null;
    	for(FeedbackField lf : FeedbackField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static FeedbackField getFieldEnumByCname(String cname){
    	FeedbackField result = null;
    	for(FeedbackField lf : FeedbackField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static FeedbackField getFieldEnumByAlias(String alias){
    	FeedbackField result = null;
    	for(FeedbackField lf : FeedbackField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}