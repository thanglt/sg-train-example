package com.m3958.firstgwt.client.types;

public enum HmessageField{
		
		TITLE("title","标题",""),MESSAGE("message","内容",""),SENDER("sender","发送者",""),
		ATTACHMENTS("attachments","附件",""),RECEIVER_IDENTITY("receiverIdentity","接收者","");
		
		
		String ename;
		String cname;
		String alias;
		
		HmessageField(String ename,String cname,String alias) {
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
		
	    public static HmessageField getFieldEnumByEname(String ename){
	    	HmessageField result = null;
	    	for(HmessageField lf : HmessageField.values()){
	    		if(ename.equals(lf.getEname())){
	    			result = lf;
	    			break;
	    		}
	    	}
	    	return result;
	    }
	    
	    public static HmessageField getFieldEnumByCname(String cname){
	    	HmessageField result = null;
	    	for(HmessageField lf : HmessageField.values()){
	    		if(cname.equals(lf.getCname())){
	    			result = lf;
	    			break;
	    		}
	    	}
	    	return result;
	    }
	    
	    public static HmessageField getFieldEnumByAlias(String alias){
	    	HmessageField result = null;
	    	for(HmessageField lf : HmessageField.values()){
	    		if(alias.equals(lf.getAlias())){
	    			result = lf;
	    			break;
	    		}
	    	}
	    	return result;
	    }
	}

