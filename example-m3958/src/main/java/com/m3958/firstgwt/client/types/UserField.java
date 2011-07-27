package com.m3958.firstgwt.client.types;


public enum UserField{
	THUMBNAIL_URL("thumbnailUrl","照片",""),FCUSER("fcUser","fcUser",""),FCID("fcId","生日","年龄"),LOGIN_NAME("loginName","登录名",""),
	PASSWORD("password","密码",""),	CONFIRM_PASSWORD("confirmPasswordJG", "重复密码",""),OLD_PASSWORD("oldPassword", "原密码",""),OPENID("openId", "openId",""),
	PASSWORD_HINT("passwordHint", "密码提示",""),	FIRSTNAME("firstName", "名",""),LASTNAME("lastName", "姓",""),	EMAIL("email", "电子邮件",""),
	PHONENUMBER("phoneNumber", "电话",""),ADDRESS("address", "地址",""),MOBILE("mobile", "手机",""),
	BIRTHDAY("birthday", "生日","年龄"),MENULEVEL_ID("menuLevelId","",""),INIT_GROUP_ID("initGroupId","",""),
	NICKNAME("nickname", "昵称",""),AVATAR("avatar","头像","");
	
	String ename;
	String cname;
	String alias;
	
	UserField(String ename,String cname,String alias) {
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
	
    public static UserField getFieldEnumByEname(String ename){
    	UserField result = null;
    	for(UserField lf : UserField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static UserField getFieldEnumByCname(String cname){
    	UserField result = null;
    	for(UserField lf : UserField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static UserField getFieldEnumByAlias(String alias){
    	UserField result = null;
    	for(UserField lf : UserField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}