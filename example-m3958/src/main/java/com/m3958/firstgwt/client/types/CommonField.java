package com.m3958.firstgwt.client.types;

public enum CommonField {
	ID("id","id",""),VERSION("version","version",""),CREATED_AT("createdAt","创建时间",""),HIDDEN("hidden","隐藏",""),
	POSITION("position","排序值",""),PARENT_ID("parentId","父节点ID",""),RS_MODENAME("rsModelName","",""),
	SITE_ID("siteId","",""),PURPOSE("purpose","",""),AUDIT("audit","审核",""),NAME("name","名称",""),
	TPL_NAME("tplName","模板",""),PER_PAGE("perpage","分页条数",""),IP("ip","IP",""),OBNAME("obname","对象名称",""),OBID("obid","对象id",""),
	TIMEZONE("_tz","timeZone",""),COMMENT_LEVEL("commentLevel","评论设定",""),KEY_VALUES("keyValues","参数",""),
	TAG_NAMES("tagnames","标签名",""),META_KEYWORDS("metaKeywords","网页关键字",""),META_DESCRIPTION("metaDescription","网页描述",""),PROTECT_LEVEL("protectLevel","防护级别","");
	
	String ename;
	String cname;
	String alias;
	
	CommonField(String ename,String cname,String alias) {
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
	
    public static WebSiteField getFieldEnumByEname(String ename){
    	WebSiteField result = null;
    	for(WebSiteField lf : WebSiteField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static WebSiteField getFieldEnumByCname(String cname){
    	WebSiteField result = null;
    	for(WebSiteField lf : WebSiteField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static WebSiteField getFieldEnumByAlias(String alias){
    	WebSiteField result = null;
    	for(WebSiteField lf : WebSiteField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}
