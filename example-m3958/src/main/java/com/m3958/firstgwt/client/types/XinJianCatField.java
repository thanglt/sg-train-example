package com.m3958.firstgwt.client.types;



public enum XinJianCatField{
	XINJIANS("xinjians","信件",""),
	KEY_VALUES("keyValues","参数对(一行一行，=号分隔)",""),
	XJCAT_CONTAINER("xjcatContainer","目录容器","");
	
	String ename;
	String cname;
	String alias;
	
	XinJianCatField(String ename,String cname,String alias) {
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
	
    public static XinJianCatField getFieldEnumByEname(String ename){
    	XinJianCatField result = null;
    	for(XinJianCatField lf : XinJianCatField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static XinJianCatField getFieldEnumByCname(String cname){
    	XinJianCatField result = null;
    	for(XinJianCatField lf : XinJianCatField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static XinJianCatField getFieldEnumByAlias(String alias){
    	XinJianCatField result = null;
    	for(XinJianCatField lf : XinJianCatField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

