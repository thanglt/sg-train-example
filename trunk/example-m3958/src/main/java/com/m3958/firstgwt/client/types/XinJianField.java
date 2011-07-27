package com.m3958.firstgwt.client.types;



public enum XinJianField{
	TITLE("title","标题",""),CONTENT("content","正文",""),
	XINGMING("xingming","姓名",""),XINGBIE("xingbie","性別",""),
	SHENGRI("shengri","生日",""),SHOUJI("shouji","手机",""),
	EMAIL("email","电邮",""),DIANHUA("dianhua","电话",""),
	DIZHI("dizhi","地址",""),LEIXING("leixing","类型",""),
	YOUBIAN("youbian","邮编",""),BAOMIMA("baomima","保密码",""),
	GONGKAI("gongkai","公开",""),AUDIT("audit","审核",""),
	REPLY("reply","",""),REPLIEDAT("repliedAt","回复日期",""),
	PUBLISHEDAT("publishedAt","发布日期",""),
	ATTACHMENTS("attachments","",""),XINJIAN_CATS("xjCats","","");
	
	String ename;
	String cname;
	String alias;
	
	XinJianField(String ename,String cname,String alias) {
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
	
    public static XinJianField getFieldEnumByEname(String ename){
    	XinJianField result = null;
    	for(XinJianField lf : XinJianField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static XinJianField getFieldEnumByCname(String cname){
    	XinJianField result = null;
    	for(XinJianField lf : XinJianField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static XinJianField getFieldEnumByAlias(String alias){
    	XinJianField result = null;
    	for(XinJianField lf : XinJianField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

