package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.slots.F1slot;
import com.m3958.firstgwt.client.slots.F2slot;
import com.smartgwt.client.widgets.layout.HLayout;

@Singleton
public class E1SplitLayer extends HLayout {
	
	@Inject
	public E1SplitLayer(F1slot f1slot,F2slot f2slot){
		setWidth100();
		setHeight100();
		f1slot.setWidth("30%");
		f1slot.setHeight100();
		f1slot.setShowResizeBar(true);
		f2slot.setWidth("70%");
		f2slot.setHeight100();
		addMember(f1slot);
		addMember(f2slot);
	}
}
