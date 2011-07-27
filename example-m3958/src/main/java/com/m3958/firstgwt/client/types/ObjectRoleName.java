package com.m3958.firstgwt.client.types;

public enum ObjectRoleName {
	OWNER("所有者"),READER("閱讀者"),EDITOR("編輯者"),
	CONTENT_ADDER("内容添加者"),CONTENT_EDITOR("内容编辑者"),
	CONTENT_READER("内容阅读者"),CONTENT_AUDIT("内容审核者"),SITE_EDITOR("站点编辑者");
	
	private String displayName;
	
	private ObjectRoleName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName(){
		return displayName;
	}
}
