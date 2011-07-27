package com.m3958.firstgwt.client.types;

public enum SmartOperationName implements ValueEnum {
	ADD("add"),FETCH("fetch"),UPDATE("update"),REMOVE("remove"),CUSTOM("custom"),NO_OPERATION("noOperation"),RPCM_REQ("rpcmReq"),ADMIN("admin");
	
	private String value;
	
	private SmartOperationName(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
