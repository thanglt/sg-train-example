package com.m3958.firstgwt.client;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.m3958.firstgwt.client.gin.FirstgwtGinjector;

public class FirstGwt implements EntryPoint {
	
	public static String defaultToken = "c1/TOP,d1/MMENU,e2/WELCOME";
	
	private final FirstgwtGinjector ginjector = GWT.create(FirstgwtGinjector.class);
	
    private Logger logger = Logger.getLogger("com.m3958.firstgwt");
	
	@Override
	public void onModuleLoad() {
	    try {
	    	App app = ginjector.getApp();
	    	app.getAppSlot().draw();
	    	app.start();
	    }catch (Exception e) {
	    	logger.log(Level.SEVERE,"e:" + e);
	        e.printStackTrace();
	        Window.alert(e.getMessage());
		}
	}
}
