package com.m3958.firstgwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.m3958.firstgwt.client.errorhandler.LgbRpcException;

@RemoteServiceRelativePath("gwtrpcservice")
public interface GwtRPCService extends RemoteService {
	
	String initLgbApp() throws LgbRpcException;

	void logout();

	boolean loginStatus();

	boolean login(String userIdentity, String password);
	
//	void setLastPreference(Integer lastPreferenceId);
}
