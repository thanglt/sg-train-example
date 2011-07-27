package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.slots.G1slot;
import com.m3958.firstgwt.client.slots.G2slot;
import com.smartgwt.client.widgets.layout.HLayout;

@Singleton
public class E2SplitLayer extends HLayout {
	
	@Inject
	public E2SplitLayer(G1slot g1,G2slot g2){
		setWidth100();
		setHeight100();
		g1.setWidth("30%");
		g1.setHeight100();
		g1.setShowResizeBar(true);
		g2.setWidth("70%");
		g2.setHeight100();
		addMember(g1);
		addMember(g2);
	}
	
}
