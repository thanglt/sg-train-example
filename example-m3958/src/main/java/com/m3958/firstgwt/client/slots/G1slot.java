package com.m3958.firstgwt.client.slots;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.IhasRightSiblingView;
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
public class G1slot extends HLayout implements ISlot{

	
	
	@Inject
	private ViewService vservice;
	
	@Inject
	private VblockService bservice;
	
	
	private SlotAssit slotAssit;

	
	@Inject
	public G1slot(SlotAssit slotAssit){
		this.slotAssit = slotAssit;
		this.slotAssit.setSlotLayout(this);
	}
	
	@Override
	public void onApplicationEvent() {
		if(bservice.g2.getViewName() == null){
			show();
		}
		if(bservice.g2.getViewName() == ViewNameEnum.LGB_EDIT || 
				bservice.g2.getViewName() == ViewNameEnum.ARTICLE_EDIT ||
				bservice.g2.getViewName() == ViewNameEnum.ADDRESS_EDIT ||
				bservice.g2.getViewName() == ViewNameEnum.HOUSE_EDIT ||
				bservice.g2.getViewName() == ViewNameEnum.REWARD_EDIT ||
				bservice.g2.getViewName() == ViewNameEnum.CAREER_EDIT ||
				bservice.g2.getViewName() == ViewNameEnum.STEP_CAREER_EDIT){
			hide();
		}else{
			show();
		}

		Iview iv = vservice.getView(bservice.g1.getViewName());
		slotAssit.addMember(iv.asLayout());
		setVisibleMember(iv.asLayout());
		vservice.getLeftStrip().changeStatus(iv.getStripStatus());
		iv.changeDisplay(bservice.g1.getViewAction(),bservice.g1.getParas());
		
	}

	@Override
	public void hiddenAllMembers() {
		slotAssit.hideAllMembers();
	}

	@Override
	public String getName() {
		return BlockName.G1;
	}

	@Override
	public void onRecordEvnet(ViewNameEnum vname, RecordEventType rtype, String... paras) {
		Iview civ = vservice.getView(vname);
		switch (rtype) {
		case SELECT:
			vservice.getLeftStrip().changeStatus(civ.getStripStatus());
			break;
		case CLICK_SELECT:
			Iview ivg2 = vservice.getView(bservice.g2.getViewName());
			if(civ instanceof IhasRightSiblingView){
				if(((IhasRightSiblingView)civ).getRightSideView() != null){
					ivg2 = vservice.getView(((IhasRightSiblingView)civ).getRightSideView());
				}
			}
//			ivg2.changeDisplay(ViewAction.CONTAINER_CLICKED, paras);
			bservice.g2.setValues(ivg2.getViewName(), ViewAction.CONTAINER_CLICKED, paras);
			bservice.addHistoryItemAndFireApplicationEvent();
			vservice.getLeftStrip().changeStatus(civ.getStripStatus());
			vservice.getRightStrip().changeStatus(ivg2.getStripStatus());
			break;
		default:
			break;
		}
	}

	@Override
	public void onStripEvent(ViewNameEnum vname, Btname sourceBt) {
		Iview curv = vservice.getView(bservice.g1.getViewName());
		Iview nextv;
		boolean fireev = false;
		switch (sourceBt) {
		case REMOVE:
			curv.changeDisplay(ViewAction.REMOVE);
			vservice.getLeftStrip().changeStatus(curv.getStripStatus());
			break;
		case ADD:
			nextv = vservice.getView(curv.nextViewName(Btname.ADD));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			bservice.g1.setValues(nextv.getViewName(),ViewAction.ADD,curv.getParas());
			fireev = true;
//			nextv.changeDisplay(ViewAction.ADD,curv.getParas());
			vservice.getLeftStrip().changeStatus(nextv.getStripStatus());
			break;
		case EDIT:
			nextv = vservice.getView(curv.nextViewName(Btname.EDIT));
			addMember(nextv.asLayout());
			setVisibleMember(nextv.asLayout());
			String[] paras = curv.getParas();
			bservice.g1.setValues(nextv.getViewName(),ViewAction.EDIT,paras);
			fireev = true;
//			nextv.changeDisplay(ViewAction.EDIT,paras);
			vservice.getLeftStrip().changeStatus(nextv.getStripStatus());
			break;
		default:
			break;
		}
		if(fireev)bservice.addHistoryItemAndFireApplicationEvent();
	}
}
