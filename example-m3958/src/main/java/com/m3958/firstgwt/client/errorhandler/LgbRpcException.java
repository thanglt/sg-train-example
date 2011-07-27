package com.m3958.firstgwt.client.errorhandler;

public class LgbRpcException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LgbRpcException(){}
	
	
	public LgbRpcException(LgbExceptionType type) {
		this.exceptionType = type;
	}
	public LgbRpcException(LgbExceptionType type,String msg) {
		super(msg);
		this.exceptionType = type;
	}	
	
	private LgbExceptionType exceptionType;
	
	public void setExceptionType(LgbExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}
	public LgbExceptionType getExceptionType() {
		return exceptionType;
	}
	
}
