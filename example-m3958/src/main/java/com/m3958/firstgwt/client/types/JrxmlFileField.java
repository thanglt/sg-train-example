package com.m3958.firstgwt.client.types;


public enum JrxmlFileField{
	NAME("name","名称",""),MODEL_NAME("modelName","类名称",""),JRXML("jrxml","源文件",""),REPORT("report","编译文件",""),
	PRINTLIST("printList","打印列表",""),PARENTID("parentId","父报表ID",""),previewImg("previewImg","缩略图","");
	
	String ename;
	String cname;
	String alias;
	
	JrxmlFileField(String ename,String cname,String alias) {
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
	
    public static JrxmlFileField getFieldEnumByEname(String ename){
    	JrxmlFileField result = null;
    	for(JrxmlFileField lf : JrxmlFileField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static JrxmlFileField getFieldEnumByCname(String cname){
    	JrxmlFileField result = null;
    	for(JrxmlFileField lf : JrxmlFileField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static JrxmlFileField getFieldEnumByAlias(String alias){
    	JrxmlFileField result = null;
    	for(JrxmlFileField lf : JrxmlFileField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}