package com.m3958.firstgwt.client.types;



public enum DiskFileField{
	FILE_NAME("name","文件名",""),CONTENT("content","内容",""),FILE_SIZE("fileSize","大小",""),
	IS_FOLDER("folder","文件夹",""),LAST_MODIFIED("lastModified","修改时间",""),SITE_ID("siteId","",""),
	OLD_FILE_NAME("oldFileName","",""),DIR_PATH("dirPath","",""),SHOW_BACKUP("showbackup","","");
	
	String ename;
	String cname;
	String alias;
	
	DiskFileField(String ename,String cname,String alias) {
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
	
    public static DiskFileField getFieldEnumByEname(String ename){
    	DiskFileField result = null;
    	for(DiskFileField lf : DiskFileField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static DiskFileField getFieldEnumByCname(String cname){
    	DiskFileField result = null;
    	for(DiskFileField lf : DiskFileField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static DiskFileField getFieldEnumByAlias(String alias){
    	DiskFileField result = null;
    	for(DiskFileField lf : DiskFileField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}

