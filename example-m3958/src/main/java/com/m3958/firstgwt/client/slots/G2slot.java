package com.m3958.firstgwt.client.slots;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.layout.HLayout;

@Singleton
public class G2slot extends HLayout implements ISlot{

	
	@Inject
	private ViewService vservice;
	
	@Inject
	private G1slot g1slot;
	
	@Inject
	private F1slot f1slot;
	
	@Inject
	private VblockService bservice;
	
	
	private SlotAssit slotAssit;
	
	@Inject
	public G2slot(SlotAssit slotAssit){
		this.slotAssit = slotAssit;
		this.slotAssit.setSlotLayout(this);
	}

	@Override
	public void onApplicationEvent() {
		Iview iv = vservice.getView(bservice.g2.getViewName());
		slotAssit.addMember(iv.asLayout());
		setVisibleMember(iv.asLayout());
		vservice.getRightStrip().changeStatus(iv.getStripStatus());
		iv.changeDisplay(bservice.g2.getViewAction(),bservice.g2.getParas());
	}

	@Override
	public void hiddenAllMembers() {
		slotAssit.hideAllMembers();
	}

	@Override
	public String getName() {
		return BlockName.G2;
	}

	@Override
	public void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype, String... paras) {
		Iview civ = vservice.getView(vname);
		switch (rtype) {
		case SELECT:
			vservice.getRightStrip().changeStatus(civ.getStripStatus());
			break;
		case GRID_CLICK:
			vservice.getRightStrip().changeStatus(civ.getStripStatus());
			break;
		default:
			break;
		}
	}

	@Override
	public void onStripEvent(ViewNameEnum vname, Btname sourceBt) {
		Iview curv = vservice.getView(bservice.g2.getViewName());
		Iview nextv;
		boolean fireev = false;
		switch (sourceBt) {
		case REMOVE:
			curv.changeDisplay(ViewAction.REMOVE);
			vservice.getRightStrip().changeStatus(curv.getStripStatus());
			break;
		case ADD:
			nextv = vservice.getView(curv.nextViewName(Btname.ADD));
			if(nextv == null)return;
			if(nextv.getViewName() == ViewNameEnum.LGB_EDIT || nextv.getViewName() == ViewNameEnum.ARTICLE_EDIT){
				g1slot.hide();
				f1slot.hide();
			}
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.g2.setValues(nextv.getViewName(),ViewAction.ADD,curv.getParas());
			vservice.getRightStrip().changeStatus(nextv.getStripStatus());
			fireev = true;
			break;
		case EDIT:
			nextv = vservice.getView(curv.nextViewName(Btname.EDIT));
			if(nextv.getViewName() == ViewNameEnum.LGB_EDIT || nextv.getViewName() == ViewNameEnum.ARTICLE_EDIT){
				g1slot.hide();
				f1slot.hide();
			}
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			String[] paras = curv.getParas();
			bservice.g2.setValues(nextv.getViewName(),ViewAction.EDIT,paras);
			fireev = true;
			vservice.getRightStrip().changeStatus(nextv.getStripStatus());
			break;
		case LEAVE_FOLDER:
			curv.changeDisplay(ViewAction.LEAVE_FOLDER);
			break;
		case MINE:
			curv.changeDisplay(ViewAction.MINE);
			break;
		case OTHERS:
			curv.changeDisplay(ViewAction.OTHERS);
			break;
		case GROUPS:
			curv.changeDisplay(ViewAction.GROUPS);
			break;
		case SHOW_ALL:
			curv.changeDisplay(ViewAction.SHOW_ALL);
			break;
		default:
			break;
		}
		if(fireev)bservice.addHistoryItemAndFireApplicationEvent();
	}
}
