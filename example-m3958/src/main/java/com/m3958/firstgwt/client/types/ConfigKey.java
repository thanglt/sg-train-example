package com.m3958.firstgwt.client.types;

import java.util.LinkedHashMap;

public enum ConfigKey {
	ASSET_PATH("assetPath","附件路径",""),MAX_UPLOAD("maxUpload","最大附件",""),UPLOAD_TMP_DIR("uploadTmpDir","上传临时目录",""),
	ASSET_SAVE_TO("assetSaveTo","附件保存方式",""),JASPER_TEMPLATE_DIR("jasperTemplateDir","jasper模板路径",""),
	ENABLE_CACHE("enableCache","开启缓存",""),SITE_ROOT("siteRoot","站点根目录",""),PERL_EXEC("perlExec","perl执行文件",""),
	APP_DOMAIN("appDomain","站点域名",""),ERROR_FOUROFOUR_FILE("error404File","404错误页面",""),
	ALLOW_EDIT_EXTS("editableFileType","可编辑的文件后缀，空格分隔",""),
	DEMO_HOST_NAME("demoHostName","演示站点域名",""),SOLR_CAMEL_DIR("solrCamelDir","solrCamel消费目录","");
	
	String ename;
	String cname;
	String alias;
	
	ConfigKey(String ename,String cname,String alias) {
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
	
	public static LinkedHashMap<String, String> getLinkedHashMap(){
		LinkedHashMap<String, String> vm = new LinkedHashMap<String, String>();
		for(ConfigKey ck : ConfigKey.values()){
			vm.put(ck.getEname(), ck.getCname());
		}
		return vm;
	}
	
    public static ConfigKey getFieldEnumByEname(String ename){
    	ConfigKey result = null;
    	for(ConfigKey lf : ConfigKey.values()){
    		if(ename.equals(lf.getEname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ConfigKey getFieldEnumByCname(String cname){
    	ConfigKey result = null;
    	for(ConfigKey lf : ConfigKey.values()){
    		if(cname.equals(lf.getCname())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
    
    public static ConfigKey getFieldEnumByAlias(String alias){
    	ConfigKey result = null;
    	for(ConfigKey lf : ConfigKey.values()){
    		if(alias.equals(lf.getAlias())){
    			result = lf;
    			break;
    		}
    	}
    	return result;
    }
}
