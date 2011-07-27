package com.m3958.firstgwt.client.types;


public enum SectionField {
	ARTICLES("articles","",""),SECTION_CONTAINER("sectionContainer","目录容器","");
	
	
	String ename;
	String cname;
	String alias;
	
	SectionField(String ename,String cname,String alias) {
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
	
    public static SectionField getFieldEnumByEname(String ename){
    	SectionField result = null;
    	for(SectionField lf : SectionField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static SectionField getFieldEnumByCname(String cname){
    	SectionField result = null;
    	for(SectionField lf : SectionField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static SectionField getFieldEnumByAlias(String alias){
    	SectionField result = null;
    	for(SectionField lf : SectionField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}
