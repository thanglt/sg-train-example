package com.m3958.firstgwt.client;

import java.util.Date;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.slots.ISlot;
import com.m3958.firstgwt.client.slots.SlotAssit;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.events.MouseMoveEvent;
import com.smartgwt.client.widgets.events.MouseMoveHandler;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class AppSlot extends VLayout implements ISlot{
	
	
	@Inject
	private ViewService vservice;
	
	@Inject
	private VblockService bservice;

	private SlotAssit slotAssit;
	
	private MainScaffold mainScaffold;
	
	@Inject
	public AppSlot(SlotAssit slotAssit,MainScaffold mainScaffold){
		this.slotAssit = slotAssit;
		this.mainScaffold = mainScaffold;
		this.slotAssit.setSlotLayout(this);
		setWidth100();
		setHeight100();
		addMember(mainScaffold);
		addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				bservice.setLastMouseMoveTimeStamp(new Date().getTime());
			}
		});
	}

	@Override
	public void onApplicationEvent() {
		Iview iv = vservice.getView(bservice.b.getViewName());
		slotAssit.addMember(iv.asLayout());
		setVisibleMember(iv.asLayout());
		iv.changeDisplay(bservice.b.getViewAction(),bservice.b.getParas());
	}


	@Override
	public void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype,
			String... paras) {
	}


	@Override
	public void onStripEvent(ViewNameEnum vname, Btname sourceBt) {
	}


	@Override
	public void hiddenAllMembers() {
		setVisibleMember(mainScaffold);
	}


	@Override
	public String getName() {
		return BlockName.B;
	}
}
