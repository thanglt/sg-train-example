package com.m3958.firstgwt.client.slots;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.layout.D2SplitLayer;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.layout.HLayout;

@Singleton
public class D2slot extends HLayout implements ISlot{

	
	@Inject
	private ViewService vservice;
	
	@Inject
	private VblockService bservice;
	
	
	private D2SplitLayer d2split;
	
	
	private SlotAssit slotAssit;
	
	@Inject
	public D2slot(SlotAssit slotAssit,D2SplitLayer d2split){
		this.slotAssit = slotAssit;
		this.slotAssit.setSlotLayout(this);
		this.d2split = d2split;
		setWidth100();
		setHeight100();
		addMember(d2split);
	}
	

	@Override
	public void onApplicationEvent() {
		Iview iv = vservice.getView(bservice.d2.getViewName());
		slotAssit.addMember(iv.asLayout());
		setVisibleMember(iv.asLayout());
		iv.changeDisplay(ViewAction.NO_ACTION,bservice.d2.getParas());
		
	}


	@Override
	public void hiddenAllMembers() {
		setVisibleMember(d2split);
	}

	@Override
	public String getName() {
		return BlockName.D2;
	}

	@Override
	public void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype, String... paras) {
		
	}

	@Override
	public void onStripEvent(ViewNameEnum vname, Btname sourceBt) {
		
	}

}
