package com.m3958.firstgwt.client.errorhandler;

import com.m3958.firstgwt.client.types.GwtResponseStatus;

public class GwtRpcPreHandler {
	
	public static boolean handle(int i){
		if(i == GwtResponseStatus.SUCCESS.getValue()){
			return true;
		}
		
		return false;
	}
}
