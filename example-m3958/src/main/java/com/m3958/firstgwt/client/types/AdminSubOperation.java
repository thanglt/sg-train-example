package com.m3958.firstgwt.client.types;

public enum AdminSubOperation {
	
	NO_SUB_OPERATION("空操作"),CREATE_OC("创建对象类名称")/*,ASSIGN_DEFAULT_ROLES("所有用户默认权限")*/,CLEAN_UP_PERMISSIONS("清理权限"),CLEAN_UP_ROLES("清理角色"),
	/*CREATE_SPECIAL_FOLDER("创建特殊文件目录"),*/FIX_PARENT_ID("修复父节点ID");
	
	private String value;
	
	AdminSubOperation(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
