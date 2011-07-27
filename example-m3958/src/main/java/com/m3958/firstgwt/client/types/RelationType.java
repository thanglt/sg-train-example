package com.m3958.firstgwt.client.types;

public enum RelationType implements ValueEnum {
	MANY_TO_MANY("manyToMany"),ONE_TO_MANY("oneToMany"),MANY_TO_ONE("manyToOne");
	
	private String value;
	
	private RelationType(String value){
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
