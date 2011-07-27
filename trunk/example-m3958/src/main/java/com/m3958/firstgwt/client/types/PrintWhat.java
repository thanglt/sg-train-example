package com.m3958.firstgwt.client.types;

public enum PrintWhat {
	RESULT_SET("查询结果"),SINGLE("选定记录");
	
	private String cname;
	
	private PrintWhat(String cname) {
		this.cname = cname;
	}
	
	public String getCname(){
		return this.cname;
	}
}
