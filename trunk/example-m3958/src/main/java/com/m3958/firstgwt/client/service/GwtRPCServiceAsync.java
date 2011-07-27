package com.m3958.firstgwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GwtRPCServiceAsync {
	
	void loginStatus(AsyncCallback<Boolean> isLogin);
	
	void login(String userIdentity,String password,AsyncCallback<Boolean> isLogin);
	
	void initLgbApp(AsyncCallback<String> callback);
	
	void logout(AsyncCallback<Void> callback);

//	void setLastPreference(Integer lastPreferenceId,
//			AsyncCallback<Void> asyncCallback);

}
