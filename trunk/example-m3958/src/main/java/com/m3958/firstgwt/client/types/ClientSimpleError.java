package com.m3958.firstgwt.client.types;

public class ClientSimpleError implements ClientError {
	
	private String msg;

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;

	}

}
