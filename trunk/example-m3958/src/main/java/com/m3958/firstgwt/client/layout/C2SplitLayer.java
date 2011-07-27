package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.slots.D1slot;
import com.m3958.firstgwt.client.slots.D2slot;
import com.smartgwt.client.widgets.layout.HLayout;

@Singleton
public class C2SplitLayer extends HLayout {
	
	@Inject
	public C2SplitLayer(D1slot d1slot,D2slot d2slot){
		setWidth100();
		setHeight100();
		addMember(d1slot);
		addMember(d2slot);
	}
}
