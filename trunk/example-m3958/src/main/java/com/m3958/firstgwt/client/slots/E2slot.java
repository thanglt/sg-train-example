package com.m3958.firstgwt.client.slots;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.E2SplitLayer;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.layout.HLayout;

@Singleton
public class E2slot extends HLayout implements ISlot{

	//控制的layer包括单面layout和splitlayout。
	
	
	@Inject
	private  E2SplitLayer e2split;//这个不会出现在history token当中
	
	@Inject
	private ViewService vservice;
	
	@Inject
	private VblockService bservice;
	
	
	private SlotAssit slotAssit;

	@Inject
	public E2slot(SlotAssit slotAssit,E2SplitLayer e2split){
		this.slotAssit = slotAssit;
		this.slotAssit.setSlotLayout(this);
		this.e2split = e2split;
		addMember(e2split);
	}

	@Override
	public void onApplicationEvent() {
		Iview vg = vservice.getView(bservice.e2.getViewName());
		slotAssit.addMember(vg.asLayout());
		setVisibleMember(vg.asLayout());
		vg.changeDisplay(bservice.e2.getViewAction(),bservice.e2.getParas());
	}

	@Override
	public void hiddenAllMembers() {
		setVisibleMember(e2split);
	}

	@Override
	public String getName() {
		return BlockName.E2;
	}

	@Override
	public void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype, String... paras) {
		Iview iv = vservice.getView(vname);
		switch (rtype) {
		case SELECT:
			vservice.getStrip().changeStatus(iv.getStripStatus());
			break;
		default:
			break;
		}
	}

	@Override
	public void onStripEvent(ViewNameEnum vname, Btname sourceBt) {
		Iview curv = vservice.getView(bservice.e2.getViewName());
		Iview nextv;
		boolean fireev = false;
		switch (sourceBt) {
		case REMOVE:
			curv.changeDisplay(ViewAction.REMOVE);
			vservice.getStrip().changeStatus(curv.getStripStatus());
			break;
		case ADD:
			nextv = vservice.getView(curv.nextViewName(Btname.ADD));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.e2.setValues(nextv.getViewName(),ViewAction.ADD,curv.getParas());
			fireev = true;
			vservice.getStrip().changeStatus(nextv.getStripStatus());
			break;
		case EDIT:
			nextv = vservice.getView(curv.nextViewName(Btname.EDIT));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.e2.setValues(nextv.getViewName(),ViewAction.EDIT,curv.getParas());
			fireev = true;
			vservice.getStrip().changeStatus(nextv.getStripStatus());
			break;
		case HOSTNAME:
			nextv = vservice.getView(curv.nextViewName(Btname.HOSTNAME));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.e2.setValues(nextv.getViewName(),ViewAction.CONTAINER_CLICKED,curv.getParas());
			fireev = true;
			vservice.getStrip().changeStatus(nextv.getStripStatus());
			break;
		case STATIC_FILE:
			nextv = vservice.getView(curv.nextViewName(Btname.STATIC_FILE));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.e2.setValues(nextv.getViewName(),ViewAction.NO_ACTION,curv.getParas());
			fireev = true;
			break;
		case ARTICLE://当前肯定是site视图。
			bservice.g1.setValues(ViewNameEnum.SECTION, ViewAction.NO_ACTION, curv.getParas());
			bservice.g2.setValues(ViewNameEnum.ARTICLE, ViewAction.NO_ACTION);
			bservice.e2.clearValues();
			bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
			bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
			bservice.e1.clearValues();
			fireev = true;
			break;
		case MEDIA:
			bservice.g1.setValues(ViewNameEnum.ASSET_FOLDER, ViewAction.NO_ACTION, curv.getParas());
			bservice.g2.setValues(ViewNameEnum.ASSET, ViewAction.NO_ACTION);
			bservice.e2.clearValues();
			bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
			bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
			bservice.e1.clearValues();
			fireev = true;
			break;
		case LINK:
			nextv = vservice.getView(curv.nextViewName(Btname.LINK));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.e2.setValues(nextv.getViewName(),ViewAction.CONTAINER_CLICKED,curv.getParas());
			fireev = true;
			vservice.getStrip().changeStatus(nextv.getStripStatus());
			break;
		case VOTE:
			nextv = vservice.getView(curv.nextViewName(Btname.VOTE));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.e2.setValues(nextv.getViewName(),ViewAction.CONTAINER_CLICKED,curv.getParas());
			fireev = true;
			vservice.getStrip().changeStatus(nextv.getStripStatus());
			break;
		case COMMENT:
			nextv = vservice.getView(curv.nextViewName(Btname.COMMENT));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.e2.setValues(nextv.getViewName(),ViewAction.CONTAINER_CLICKED,curv.getParas());
			fireev = true;
			vservice.getStrip().changeStatus(nextv.getStripStatus());
			break;
		case PAGE_MISTAKE:
			nextv = vservice.getView(curv.nextViewName(Btname.PAGE_MISTAKE));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.e2.setValues(nextv.getViewName(),ViewAction.CONTAINER_CLICKED,curv.getParas());
			fireev = true;
			vservice.getStrip().changeStatus(nextv.getStripStatus());
			break;
		case VOTE_HIT:
			nextv = vservice.getView(curv.nextViewName(Btname.VOTE_HIT));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.e2.setValues(nextv.getViewName(),ViewAction.CONTAINER_CLICKED,curv.getParas());
			fireev = true;
			vservice.getStrip().changeStatus(nextv.getStripStatus());
			break;
		case XINJIAN:
			bservice.g1.setValues(ViewNameEnum.XJ_CAT, ViewAction.NO_ACTION, curv.getParas());
			bservice.g2.setValues(ViewNameEnum.XINJIAN, ViewAction.NO_ACTION);
			bservice.e2.clearValues();
			bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
			bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
			bservice.e1.clearValues();
			fireev = true;
			break;
		case SHOW_ALL:
			curv.changeDisplay(ViewAction.SHOW_ALL);
			break;
		case CONFIRM:
			break;
		default:
			break;
		}
		if(fireev)bservice.addHistoryItemAndFireApplicationEvent();
		
	}
}
