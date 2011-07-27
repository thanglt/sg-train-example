package com.m3958.firstgwt.client.types;

public enum GwtResponseStatus  implements IntegerValueEnum {
	SUCCESS(0),
	ACCESS_DENIED(-1),
	LOGIN_REQUIRED(-2);

	public Integer getValue() {
		return status;
	}
	
	private Integer status;
	
	private GwtResponseStatus(Integer status){
		this.status = status;
	}

}
