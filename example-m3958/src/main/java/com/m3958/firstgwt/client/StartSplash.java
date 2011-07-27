package com.m3958.firstgwt.client;

import com.google.inject.Singleton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class StartSplash extends VLayout{
	
	public StartSplash(){
		setWidth100();
		setHeight100();
		setID("splash");
		Label l = new Label("starting.........");
		addMember(l);
	}

}
