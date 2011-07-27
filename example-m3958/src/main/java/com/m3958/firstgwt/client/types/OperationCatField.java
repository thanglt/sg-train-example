package com.m3958.firstgwt.client.types;



public enum OperationCatField{
	NAME("name","操作組合名稱","");
	
	String ename;
	String cname;
	String alias;
	
	OperationCatField(String ename,String cname,String alias) {
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
	
    public static OperationCatField getFieldEnumByEname(String ename){
    	OperationCatField result = null;
    	for(OperationCatField lf : OperationCatField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static OperationCatField getFieldEnumByCname(String cname){
    	OperationCatField result = null;
    	for(OperationCatField lf : OperationCatField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static OperationCatField getFieldEnumByAlias(String alias){
    	OperationCatField result = null;
    	for(OperationCatField lf : OperationCatField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

