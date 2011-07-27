package com.m3958.firstgwt.client.slots;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.Layout;

public class SlotAssit {
	
	private Layout slotLayout;
	
	
	public void setSlotLayout(Layout slotLayout){
		this.slotLayout = slotLayout;
	}
	
	public void addMember(Canvas display){
		if(!alreadyAdd(display)){
			slotLayout.addMember(display);
		}
	}
	
	public void hideAllMembers(){
		for(Canvas c: slotLayout.getMembers()){
			slotLayout.hideMember(c);
		}
			
	}
	
	
	private boolean alreadyAdd(Canvas display){
		for(Canvas c:slotLayout.getMembers()){
			if(c == display)return true;
		}
		return false;
	}
	

}
