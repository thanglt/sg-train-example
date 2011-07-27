package com.m3958.firstgwt.client.types;

import java.util.EnumMap;
import java.util.LinkedHashMap;

public enum DepartmentOperationType  implements ValueEnum {
	ADD_CHILD("addChild"),READ("read"),UPDATE("update"),DELETE("delete"),LIST("list");
	
	private String value;
	
	private DepartmentOperationType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
	
	public static EnumMap<DepartmentOperationType, String> em = new EnumMap<DepartmentOperationType, String>(DepartmentOperationType.class);
	
	static {
		em.put(DepartmentOperationType.ADD_CHILD, "addChild");
		em.put(DepartmentOperationType.READ, "read");
		em.put(DepartmentOperationType.UPDATE, "update");
		em.put(DepartmentOperationType.DELETE, "delete");
		em.put(DepartmentOperationType.LIST, "list");
		
	}
	
	public static LinkedHashMap<String, String> stringMap = new LinkedHashMap<String,String>();
	
	static {
		stringMap.put("ADD_CHILD", "addChild");
		stringMap.put("READ", "read");
		stringMap.put("UPDATE", "update");
		stringMap.put("DELETE", "delete");
		stringMap.put("LIST", "list");
	}

}
