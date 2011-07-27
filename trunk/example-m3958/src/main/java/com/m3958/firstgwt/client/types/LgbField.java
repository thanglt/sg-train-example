package com.m3958.firstgwt.client.types;


public enum LgbField{
	ID("id","id",""),BZ("bz","备注",""),XM("xm","姓名",""),XB("xb","性别",""),SR("sr","生日","年龄"),SFZ("sfz","身份证",""),MINZU("minzu","民族",""),JG("jg", "籍贯",""),CSD("csd", "出生地",""),
	JY("jy", "教育",""),RDSJ("rdsj", "入党时间",""),RWSJ("rwsj", "入伍时间",""),LTXSJ("ltxsj", "离退休时间",""),YGZDW("ygzdw", "原工作单位",""),
	YZW("yzw", "原职务",""),XZGDW("xzgdw", "现主管单位",""),DWXZ("dwxz", "单位性质",""),XZJB("xzjb", "行政级别",""),XSDY("xsdy", "享受待遇",""),
	GBLX("gblx", "干部类型",""),JKZK("jkzk", "健康状况",""),HYZK("hyzk", "婚姻状况",""),JJZK("jjzk", "家庭经济状况",""),POGZ("pogz", "配偶工作",""),
	HJSZD("hjszd", "户籍所在地",""),SWSJ("swsj", "死亡时间",""),PASSAWAY("passAway", "已故",""),SSZB("sszb","所属支部",""),PAIXU("paixu","排序",""),JBMS("jbms","健康状况",""),
	ADDRESSES("addresses","所有地址",""),CAREERS("careers","",""),STEP_CAREERS("stepCareers","",""),
	REWARDS("rewards","",""),HOUSES("houses","",""),FAMILIES("families","",""),
	ATTACHMENTS("attachments","",""),SHEQU_ID("shequId","",""),DEPARTMENT_ID("departmentId","",""),
	DEPARTMENT_IDS("departmentIds","",""),ZP_ID("zpId","",""),ZP("zp","","");
	
	String ename;
	String cname;
	String alias;
	
	LgbField(String ename,String cname,String alias) {
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
	
    public static LgbField getFieldEnumByEname(String ename){
    	LgbField result = null;
    	for(LgbField lf : LgbField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static LgbField getFieldEnumByCname(String cname){
    	LgbField result = null;
    	for(LgbField lf : LgbField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static LgbField getFieldEnumByAlias(String alias){
    	LgbField result = null;
    	for(LgbField lf : LgbField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}