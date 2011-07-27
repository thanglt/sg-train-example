package com.m3958.firstgwt.client.types;

public enum OperationType implements ValueEnum {
	CREATE("create"),READ("read"),UPDATE("update"),DELETE("delete"),LIST("list");
	
	private String value;
	
	private OperationType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
