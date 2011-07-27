package com.m3958.firstgwt.client.types;



public enum LinkField{
	LINK_TO("linkTo","链接到",""),INIT_LINKCAT_ID("initLinkCatId","",""),
	TPL_NAME("tplName","模板名称",""),LURL("lurl","url",""),MODEL_ID("modelId","对象ID","");
	
	String ename;
	String cname;
	String alias;
	
	LinkField(String ename,String cname,String alias) {
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
	
    public static LinkField getFieldEnumByEname(String ename){
    	LinkField result = null;
    	for(LinkField lf : LinkField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static LinkField getFieldEnumByCname(String cname){
    	LinkField result = null;
    	for(LinkField lf : LinkField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static LinkField getFieldEnumByAlias(String alias){
    	LinkField result = null;
    	for(LinkField lf : LinkField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

