package com.m3958.firstgwt.client.types;



public enum VoteField{
	START_DATE("startDate","开始时间",""),END_DATE("endDate","结束时间",""),VOTEHITS("voteHits","",""),
	CHART_TYPE("chartType","图类型",""),MIN_SELECT("minSelect","最少选择",""),MAX_SELECT("maxSelect","最多选择","");
	
	String ename;
	String cname;
	String alias;
	
	VoteField(String ename,String cname,String alias) {
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
	
    public static VoteField getFieldEnumByEname(String ename){
    	VoteField result = null;
    	for(VoteField lf : VoteField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static VoteField getFieldEnumByCname(String cname){
    	VoteField result = null;
    	for(VoteField lf : VoteField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static VoteField getFieldEnumByAlias(String alias){
    	VoteField result = null;
    	for(VoteField lf : VoteField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

