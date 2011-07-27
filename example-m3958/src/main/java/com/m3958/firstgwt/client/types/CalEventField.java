package com.m3958.firstgwt.client.types;


public enum CalEventField{
	NAME("name","事件名称",""),DESCRIPTION("description","事件描述",""),START_DATE("startDate","开始时间",""),END_DATE("enddate","结束时间",""),
	CALEVENT_TYPE("calEventType","事件类型","");
	
	String ename;
	String cname;
	String alias;
	
	CalEventField(String ename,String cname,String alias) {
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
	
    public static CalEventField getFieldEnumByEname(String ename){
    	CalEventField result = null;
    	for(CalEventField lf : CalEventField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static CalEventField getFieldEnumByCname(String cname){
    	CalEventField result = null;
    	for(CalEventField lf : CalEventField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static CalEventField getFieldEnumByAlias(String alias){
    	CalEventField result = null;
    	for(CalEventField lf : CalEventField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}