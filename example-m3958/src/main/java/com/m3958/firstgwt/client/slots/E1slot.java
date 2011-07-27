package com.m3958.firstgwt.client.slots;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.layout.E1SplitLayer;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.layout.HLayout;

@Singleton
public class E1slot extends HLayout implements ISlot{
	
	
	@Inject
	private ViewService vservice;
	
	@Inject
	private E1SplitLayer e1split;
	
	@Inject
	private VblockService bservice;
	
	
	private SlotAssit slotAssit;
	
	@Inject
	public E1slot(SlotAssit slotAssit,E1SplitLayer e1split){
		this.slotAssit = slotAssit;
		this.slotAssit.setSlotLayout(this);
		this.e1split = e1split;
		addMember(e1split);
	}



	@Override
	public void onApplicationEvent() {
		Iview iv = vservice.getView(bservice.e1.getViewName());
		slotAssit.addMember(iv.asLayout());
		setVisibleMember(iv.asLayout());
		iv.changeDisplay(ViewAction.NO_ACTION,bservice.e1.getParas());
		if(bservice.e2.getViewName() != null){
			Iview giv = vservice.getView(bservice.e2.getViewName());
			((AppToolStrip)iv).changeStatus(giv.getStripStatus());
		}
		
	}

	@Override
	public void hiddenAllMembers() {
		setVisibleMember(e1split);
		
	}


	@Override
	public String getName() {
		return BlockName.E1;
	}


	@Override
	public void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype, String... paras) {
	}


	@Override
	public void onStripEvent(ViewNameEnum vname, Btname sourceBt) {
	}
}
