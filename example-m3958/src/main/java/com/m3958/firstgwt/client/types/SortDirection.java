package com.m3958.firstgwt.client.types;

public enum SortDirection implements ValueEnum {
	ASCENDING("asc"),DESCENDING("desc");
	
	private String value;
	
	private SortDirection(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
