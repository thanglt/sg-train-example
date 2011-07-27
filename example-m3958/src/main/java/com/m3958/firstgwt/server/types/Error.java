package com.m3958.firstgwt.server.types;

public class Error {
	
	private String message;
	
	private int code;
	
	public Error(){}
	
	public Error(String message,int code){
		this.setMessage(message);
		this.setCode(code);
	}


	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}


	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
