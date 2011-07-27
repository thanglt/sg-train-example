package com.m3958.firstgwt.client.types;


public enum ComponentPreferenceField{
	NAME("name","视图名称",""),VIEW_STATE("viewState","状态",""),MODEL_NAME("modelName","类名称",""),LAST_REQUEST("lastRequest","","最后试图");
	
	String ename;
	String cname;
	String alias;
	
	ComponentPreferenceField(String ename,String cname,String alias) {
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
	
    public static ComponentPreferenceField getFieldEnumByEname(String ename){
    	ComponentPreferenceField result = null;
    	for(ComponentPreferenceField lf : ComponentPreferenceField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ComponentPreferenceField getFieldEnumByCname(String cname){
    	ComponentPreferenceField result = null;
    	for(ComponentPreferenceField lf : ComponentPreferenceField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ComponentPreferenceField getFieldEnumByAlias(String alias){
    	ComponentPreferenceField result = null;
    	for(ComponentPreferenceField lf : ComponentPreferenceField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}