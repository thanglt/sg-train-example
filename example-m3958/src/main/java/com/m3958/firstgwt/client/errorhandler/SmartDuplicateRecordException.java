package com.m3958.firstgwt.client.errorhandler;


@SuppressWarnings("serial")
public class SmartDuplicateRecordException extends SmartBaseException {
	
	public SmartDuplicateRecordException(Exception exp) {
		super(exp);
	}
	
	
	public SmartDuplicateRecordException() {
		message = "重复的记录！";
	}


	@Override
	protected void createNewMessage(Exception exp) {
		message = exp.getMessage();
	}

}
