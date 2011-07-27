package com.m3958.firstgwt.client.errorhandler;

@SuppressWarnings("serial")
public class BeanUtilsException extends SmartBaseException {

	public BeanUtilsException(Exception exp) {
		super(exp);
	}


	@Override
	protected void createNewMessage(Exception exp) {
		message = exp.getMessage();
	}

}
