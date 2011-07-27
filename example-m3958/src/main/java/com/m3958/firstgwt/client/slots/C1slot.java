package com.m3958.firstgwt.client.slots;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.HLayout;

@Singleton
public class C1slot extends HLayout implements ISlot{
	
	
	@Inject
	private ViewService vservice;
	
	@Inject
	private VblockService bservice;
	
	
	private SlotAssit slotAssit;
	
	@Inject
	public C1slot(SlotAssit slotAssit){
		this.slotAssit = slotAssit;
		this.slotAssit.setSlotLayout(this);
		setWidth100();
		setAutoHeight();
		setOverflow(Overflow.VISIBLE);
	}
	

	@Override
	public void onApplicationEvent() {
		Iview iv = vservice.getView(bservice.c1.getViewName());
		slotAssit.addMember(iv.asLayout());
		setVisibleMember(iv.asLayout());
		iv.changeDisplay(bservice.c1.getViewAction(),bservice.c1.getParas());
	}


	@Override
	public void hiddenAllMembers() {
		slotAssit.hideAllMembers();
	}


	@Override
	public String getName() {
		return BlockName.C1;
	}


	@Override
	public void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype, String... paras) {
	}


	@Override
	public void onStripEvent(ViewNameEnum vname, Btname sourceBt) {
	}

}
