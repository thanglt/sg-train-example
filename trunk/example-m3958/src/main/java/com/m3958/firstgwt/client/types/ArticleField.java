package com.m3958.firstgwt.client.types;



public enum ArticleField{
	TITLE("title","标题",""),EXCERPT("excerpt","摘要",""),CONTENT("content","正文",""),
	TITLE_IMG("titleImg","",""),CONTENT_IMGS("contentImgs","",""),
	ATTACHMENTS("attachments","",""),SECTIONS("sections","",""),
	DEFAULT_SECTION_ID("defaultSectionId","主目录",""),
	FLAG("flag","特征值",""),PUBLISHED_AT("publishedAt","发布时间",""),
	DISPLAY_AUTHOR("displayAuthor","作者",""),SOURCE("source","来源","");
	
	String ename;
	String cname;
	String alias;
	
	ArticleField(String ename,String cname,String alias) {
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
	
    public static ArticleField getFieldEnumByEname(String ename){
    	ArticleField result = null;
    	for(ArticleField lf : ArticleField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ArticleField getFieldEnumByCname(String cname){
    	ArticleField result = null;
    	for(ArticleField lf : ArticleField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ArticleField getFieldEnumByAlias(String alias){
    	ArticleField result = null;
    	for(ArticleField lf : ArticleField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

