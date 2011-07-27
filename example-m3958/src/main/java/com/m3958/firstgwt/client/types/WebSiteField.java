package com.m3958.firstgwt.client.types;


public enum WebSiteField{
	CNAME("cname","中文名",""),ENAME("ename","英文名",""),
	STYLE_NAME("styleName","风格",""),WEBHOSTS("webhosts","",""),
	LOGO_URL("logoUrl","后台Logo",""),CACHE_ENABLE("cacheEnable","开启缓存",""),
	STOP("stop","停止站点",""),SEARCH_FRIEND_URL("searchFriendUrl","搜索友好url",""),
	TITLE("title","标题",""),IMG_SIZES("imgSizes","图片尺寸",""),
	DISPLAY_AUTHORS("displayAuthors","常用作者",""),
	ARTICLE_SOURCES("articleSources","文章来源",""),
	ARTICLE_FLAGS("articleFlags","文章标记",""),COMMENT_CLOSED("commentClosed","关闭评论",""),
	EDITOR_CSS("richEditorCssPath","编辑器Css",""),RSS_DATE_NUM("rssDateNum","RSS天数","");
	
	String ename;
	String cname;
	String alias;
	
	WebSiteField(String ename,String cname,String alias) {
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