package com.m3958.firstgwt.server.types;

public enum ObjectOperation {
	ADD("添加"),FETCH("讀取"),UPDATE("更新"),REMOVE("刪除"),
	FETCH_CONTENT("獲取內容"),REMOVE_CHILD("刪除子目錄"),	ADD_CHILD("添加子目錄"),	LIST_CHILDREN("列出子目錄"),
	UPDATE_CONTENT("更新內容"),REMOVE_CONTENT("刪除內容"),ADD_CONTENT("添加內容"),LIST_CONTENT("列出內容"),
	AUDIT_CONTENT("审核内容");
	
	private String displayName;
	
	private ObjectOperation(String dn) {
		displayName = dn;
	}
	
	public String getDisplayName(){
		return displayName;
	}
}
