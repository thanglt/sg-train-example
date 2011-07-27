package com.m3958.firstgwt.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.slots.C1slot;
import com.m3958.firstgwt.client.slots.C2slot;
import com.smartgwt.client.widgets.layout.VLayout;


@Singleton
public class MainScaffold extends VLayout{
	
	@Inject
	public MainScaffold(C1slot c1,C2slot c2){
		setWidth100();
		setHeight100();
		addMember(c1);
		addMember(c2);
	}
}
