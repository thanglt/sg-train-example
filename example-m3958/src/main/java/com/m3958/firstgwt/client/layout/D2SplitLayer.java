package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.slots.E1slot;
import com.m3958.firstgwt.client.slots.E2slot;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class D2SplitLayer extends VLayout {
	
	@Inject
	public D2SplitLayer(E1slot e1slot,E2slot e2slot){
		setWidth100();
		setHeight100();
		e1slot.setWidth100();
		e1slot.setAutoHeight();
		e1slot.setOverflow(Overflow.VISIBLE);
		
		e2slot.setWidth100();
		e2slot.setHeight100();
		
		addMember(e1slot);
		addMember(e2slot);
	}
}
