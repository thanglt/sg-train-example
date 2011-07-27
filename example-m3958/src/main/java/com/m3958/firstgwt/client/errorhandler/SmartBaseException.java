package com.m3958.firstgwt.client.errorhandler;

@SuppressWarnings("serial")
public abstract class SmartBaseException extends Exception{
	protected String originMessage;
	
	protected String message;
	
	public SmartBaseException() {}
	
	public SmartBaseException(Exception exp) {
		super(exp);
		exp.printStackTrace();
		originMessage = exp.getMessage();
		createNewMessage(exp);
	}
	
	@Override
	public String getMessage(){
		return message;
	}
	
	public String getOriginMessage(){
		return originMessage;
	}
	

	/*
	 * 根据被包裹的exception提供的信息，
	 * 尽量产生一个更容易被客户所阅读的信息。
	 */
	protected abstract void createNewMessage(Exception exp);
}
