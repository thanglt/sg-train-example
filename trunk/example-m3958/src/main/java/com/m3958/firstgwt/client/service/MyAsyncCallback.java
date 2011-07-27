package com.m3958.firstgwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.m3958.firstgwt.client.errorhandler.GwtRpcExceptionHandler;
import com.smartgwt.client.util.SC;

public abstract class MyAsyncCallback implements AsyncCallback<String>{
	
	public MyAsyncCallback(){
		SC.showPrompt("rpc请求中......");
	}

	@Override
	public void onFailure(Throwable caught) {
		SC.clearPrompt();
		GwtRpcExceptionHandler.handler(caught);
	}
	
	public abstract void onOnSuccess(String result);

	@Override
	public  void onSuccess(String result){
		SC.clearPrompt();
		onOnSuccess(result);
	}

}
