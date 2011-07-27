package com.m3958.firstgwt.client.slots;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanRefresh;
import com.m3958.firstgwt.client.ICanShare;
import com.m3958.firstgwt.client.IHasRole;
import com.m3958.firstgwt.client.Vblock;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.event.NextViewEvent;
import com.m3958.firstgwt.client.event.NextViewEventHandler;
import com.m3958.firstgwt.client.event.StripEvent;
import com.m3958.firstgwt.client.event.StripEventHandler;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.layout.RoleLayout;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.window.AssignPermissionWindow;
import com.m3958.firstgwt.client.window.AssignRoleWindow;
import com.m3958.firstgwt.client.window.ShareToGroupWindow;
import com.m3958.firstgwt.client.window.ShareToUserWindow;



@Singleton
public class GlobalSlot {

	private MyEventBus eventBus;
	
	@Inject
	private ViewService vservice;
	
	@Inject
	private AssignRoleWindow arw;
	
	@Inject
	private AssignPermissionWindow apw;
	
	@Inject
	private ShareToUserWindow suw;
	
	@Inject
	private ShareToGroupWindow sgw;
	
	@Inject
	private VblockService bservice;
	
	
	@Inject
	public GlobalSlot(MyEventBus eventBus){
		this.eventBus = eventBus;
		initListener();
	}
	
	private NextViewEventHandler nveh = new NextViewEventHandler() {
		
		@Override
		public void onNextViewEvent(NextViewEvent event) {
			Vblock b = bservice.getBlock(event.getVname());
			if(b == null)return;
			b.setValues(event.getNextViewName(), ViewAction.NEXTVIEW, event.getParas());
			bservice.addHistoryItemAndFireApplicationEvent();
		}
	};
	
	private StripEventHandler stripH = new StripEventHandler() {
		@Override
		public void onViewEvent(StripEvent event) {
			Vblock b = bservice.getBlock(event.getVname());
			if(b == null)return;
			Iview curv = null;
			String bname = b.getName();
			if(BlockName.F1.equals(bname)){
				curv = vservice.getView(bservice.g1.getViewName());
			}else if(BlockName.F2.equals(bname)){
				curv = vservice.getView(bservice.g2.getViewName());
			}else if(BlockName.E1.equals(bname)){
				curv = vservice.getView(bservice.e2.getViewName());
			}
			if(curv == null)return;
			ICanShare ich;
			switch (event.getSourceBt()) {
			case ROLE:
				IHasRole ihr = (IHasRole) curv;
				arw.showme(ihr.getOid(), ihr.getModelName(), ihr.getOname());
				break;
			case PERMISSION:
				if(curv instanceof RoleLayout){
					RoleLayout rl = (RoleLayout) curv;
					apw.showme(rl.getRid(),rl.getRoleTitle());
				}
				break;
			case SHARE_TO_USER: 
				ich = (ICanShare) curv;
				suw.showMe(ich.getOid(), ich.getModelName(), ich.getOname());
				break;
			case SHARE_TO_GROUP:
				ich = (ICanShare) curv;
				sgw.showMe(ich.getOid(), ich.getModelName(), ich.getOname());
				break;
			case REFRESH:
				if(curv instanceof ICanRefresh){
					((ICanRefresh)curv).refreshContent();
				}
				break;
			default:
				break;
			}
		}
	};
	
	public void initListener(){
		eventBus.addHandler(StripEvent.TYPE, stripH);
		eventBus.addHandler(NextViewEvent.TYPE, nveh);
	}
}
