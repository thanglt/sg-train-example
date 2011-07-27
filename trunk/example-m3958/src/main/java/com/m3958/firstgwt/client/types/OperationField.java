package com.m3958.firstgwt.client.types;


public enum OperationField{
	NAME("name","操作名称",""),OPCODE("opCode","操作代码",""),
	DESCRIPTION("description","操作描述",""),OPERATION_CAT("operationCat","操作分類","");
	
	String ename;
	String cname;
	String alias;
	
	OperationField(String ename,String cname,String alias) {
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
	
    public static OperationField getFieldEnumByEname(String ename){
    	OperationField result = null;
    	for(OperationField lf : OperationField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static OperationField getFieldEnumByCname(String cname){
    	OperationField result = null;
    	for(OperationField lf : OperationField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static OperationField getFieldEnumByAlias(String alias){
    	OperationField result = null;
    	for(OperationField lf : OperationField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}