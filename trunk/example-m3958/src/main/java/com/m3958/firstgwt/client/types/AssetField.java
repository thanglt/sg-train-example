package com.m3958.firstgwt.client.types;

public enum AssetField {
	FILE_PATH("filePath","文件路径",""),FILE_ID("fileId","文件ID",""),FILE_SIZE("fileSize","大小",""),MIME_TYPE("mimeType","类型",""),
	ORIGIN_NAME("originName","原文件名",""),DESCRIPTION("description","描述",""),
	SAVE_TO("saveTo","保存方式",""),THUMBNAIL("thumbnail","缩略图",""),FOLDER_ID("folderId","","");
	
	String ename;
	String cname;
	String alias;
	
	AssetField(String ename,String cname,String alias) {
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
	
    public static AssetField getFieldEnumByEname(String ename){
    	AssetField result = null;
    	for(AssetField lf : AssetField.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static AssetField getFieldEnumByCname(String cname){
    	AssetField result = null;
    	for(AssetField lf : AssetField.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static AssetField getFieldEnumByAlias(String alias){
    	AssetField result = null;
    	for(AssetField lf : AssetField.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}
