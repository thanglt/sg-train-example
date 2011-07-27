package com.m3958.firstgwt.client.errorhandler;

public class SmartAccessDenyException extends SmartBaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SmartAccessDenyException(Exception exp) {
		super(exp);
	}
	
	
	public SmartAccessDenyException() {
		message = "没有操作权限";
	}

	@Override
	protected void createNewMessage(Exception exp) {
		
		
	}

}
