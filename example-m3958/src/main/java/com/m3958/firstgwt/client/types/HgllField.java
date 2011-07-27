package com.m3958.firstgwt.client.types;


public enum HgllField{
	XM("xm","姓名",""),XB("xb","性别",""),SR("sr","出生日期",""),DH("dh","电话",""),
	JG("jg","籍贯",""),SFZ("sfz","身份证",""),LXDZ("lxdz","联系地址",""),GZDW("gzdw","工作单位",""),
	MZ("mz","民族",""),SSXZ("ssxz","所属乡镇",""),LY("ly","留言",""),SJ("sj","手机",""),EMAIL("email","email",""),CSLB("cslb","参赛类别",""),
	CSGQ("csgq","参赛歌曲",""),WHCD("whcd","文化程度",""),ZPS("zps","照片","");
	
	String ename;
	String cname;
	String alias;
	
	HgllField(String ename,String cname,String alias) {
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
	
    public static HgllField getFieldEnumByEname(String ename){
    	HgllField result = null;
    	for(HgllField lf : HgllField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HgllField getFieldEnumByCname(String cname){
    	HgllField result = null;
    	for(HgllField lf : HgllField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static HgllField getFieldEnumByAlias(String alias){
    	HgllField result = null;
    	for(HgllField lf : HgllField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}