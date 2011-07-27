package com.m3958.firstgwt.client.slots;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.layout.C2SplitLayer;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class C2slot extends VLayout implements ISlot{

	private MyEventBus eventBus;
	
	@Inject
	private ViewService vservice;
	
	private C2SplitLayer c2split;
	
	private SlotAssit slotAssit;
	
	@Inject
	private VblockService bservice;
	
	@Inject
	public C2slot(MyEventBus eventBus,SlotAssit slotAssit,C2SplitLayer c2split){
		this.eventBus = eventBus;
		this.slotAssit = slotAssit;
		this.slotAssit.setSlotLayout(this);
		this.c2split = c2split;
		setWidth100();
		setHeight100();
		addMember(c2split);
	}
	

	@Override
	public void onApplicationEvent() {
		Iview iv = vservice.getView(bservice.c2.getViewName());
		slotAssit.addMember(iv.asLayout());
		setVisibleMember(iv.asLayout());
		iv.changeDisplay(ViewAction.NO_ACTION,bservice.c2.getParas());
		
	}


	@Override
	public void hiddenAllMembers() {
		setVisibleMember(c2split);
	}

	@Override
	public String getName() {
		return BlockName.C2;
	}

	@Override
	public void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype, String... paras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStripEvent(ViewNameEnum vname, Btname sourceBt) {
		// TODO Auto-generated method stub
		
	}
}
