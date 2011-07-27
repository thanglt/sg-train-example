package com.m3958.firstgwt.client.slots;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.layout.HLayout;

@Singleton
public class F2slot extends HLayout implements ISlot{

	
	@Inject
	private ViewService vservice;
	
	@Inject
	private VblockService bservice;
	
	
	private SlotAssit slotAssit;
	
	@Inject
	public F2slot(SlotAssit slotAssit){
		this.slotAssit = slotAssit;
		this.slotAssit.setSlotLayout(this);
		setWidth100();
		setHeight100();
	}
	
	@Override
	public void onApplicationEvent() {
		show();
		Iview iv = vservice.getView(bservice.f2.getViewName());
		slotAssit.addMember(iv.asLayout());
		setVisibleMember(iv.asLayout());
		iv.changeDisplay(bservice.f2.getViewAction(),bservice.f2.getParas());
		if(bservice.g2.getViewName() != null){
			Iview giv = vservice.getView(bservice.g2.getViewName());
			((AppToolStrip)iv).changeStatus(giv.getStripStatus());
		}
		
	}


	@Override
	public void hiddenAllMembers() {
		slotAssit.hideAllMembers();
		
	}

	@Override
	public String getName() {
		return BlockName.F2;
	}

	@Override
	public void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype, String... paras) {
		
	}

	@Override
	public void onStripEvent(ViewNameEnum vname, Btname sourceBt) {
		
	}
}
