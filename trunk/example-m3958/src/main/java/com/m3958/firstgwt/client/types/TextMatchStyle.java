package com.m3958.firstgwt.client.types;

public enum TextMatchStyle implements ValueEnum {
	EXACT("exact") ,STARTSWITH("startsWith") ,SUBSTRING("substring");
	
	
	private String value;
	
	private TextMatchStyle(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}



}
