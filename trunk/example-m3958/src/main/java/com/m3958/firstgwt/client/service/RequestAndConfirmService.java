package com.m3958.firstgwt.client.service;

import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.ICanAcceptConfirm;
import com.m3958.firstgwt.client.ICanConfirmSome;
import com.m3958.firstgwt.client.Vblock;
import com.m3958.firstgwt.client.Vblock.BlockName;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.ViewService;
import com.m3958.firstgwt.client.event.RequestSomeEvent;
import com.m3958.firstgwt.client.event.RequestSomeEvent.AskForWhat;
import com.m3958.firstgwt.client.event.RequestSomeEventHandler;
import com.m3958.firstgwt.client.event.StripEvent;
import com.m3958.firstgwt.client.event.StripEventHandler;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.layout.Iview;
import com.m3958.firstgwt.client.types.ViewNameEnum;

@Singleton
public class RequestAndConfirmService {
	
	@Inject
	private AppStatusService aservice;
	
	@Inject
	private ViewService vservice;

	private String latestRequestToken;
	
	private AskForWhat latestAskFor;
	
	private Iview requestView;
	
	private String confirmHint;
	
	@Inject
	private VblockService bservice;

	private RequestSomeEventHandler rseh = new RequestSomeEventHandler() {
		@Override
		public void onRequestSome(RequestSomeEvent event) {
			setLatestRequestToken(History.getToken());
			latestAskFor = event.getAskFor();
			confirmHint = event.getConfirmHint();
			requestView = vservice.getView(event.getRequestViewName());
			aservice.setConfirmInProgress(true);
			switch (latestAskFor) {
			case FOLDER_ASSET:
				//G1,G2,F1,F2改变。E1，E2删除！
				bservice.g1.setValues(ViewNameEnum.ASSET_FOLDER, ViewAction.NO_ACTION);
				bservice.g2.setValues(ViewNameEnum.ASSET, ViewAction.NO_ACTION);
				bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
				bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
				bservice.e1.clearValues();
				bservice.e2.clearValues();
				break;
			case SECTION_ARTICLE:
				bservice.g1.setValues(ViewNameEnum.SECTION, ViewAction.NO_ACTION);
				bservice.g2.setValues(ViewNameEnum.ARTICLE, ViewAction.NO_ACTION);
				bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
				bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
				bservice.e1.clearValues();
				bservice.e2.clearValues();
				break;
			case XJCAT_XJ:
				bservice.g1.setValues(ViewNameEnum.XJ_CAT, ViewAction.NO_ACTION);
				bservice.g2.setValues(ViewNameEnum.XINJIAN, ViewAction.NO_ACTION);
				bservice.f1.setValues(ViewNameEnum.LEFT_STRIP, ViewAction.NO_ACTION);
				bservice.f2.setValues(ViewNameEnum.RIGHT_STRIP, ViewAction.NO_ACTION);
				bservice.e1.clearValues();
				bservice.e2.clearValues();
				break;
			default:
				break;
			}
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
			}else if(BlockName.F2.endsWith(bname)){
				curv = vservice.getView(bservice.g2.getViewName());
			}else if(BlockName.E1.equals(bname)){
				curv = vservice.getView(bservice.e2.getViewName());
			}
			if(curv == null)return;
			switch (event.getSourceBt()) {
			case CONFIRM:
				if(requestView != null){
					((ICanAcceptConfirm)requestView).giveConfirmedThing(((ICanConfirmSome)curv).getConfirmedThing(),confirmHint);
					History.newItem(latestRequestToken, true);
				}
				requestView = null;
				confirmHint = "";
				break;
			default:
				break;
			}
		}
	};
	
	@Inject
	public RequestAndConfirmService(MyEventBus eventBus){
		eventBus.addHandler(RequestSomeEvent.TYPE, rseh);
		eventBus.addHandler(StripEvent.TYPE, stripH);
	}

	public void setLatestRequestToken(String latestRequestToken) {
		this.latestRequestToken = latestRequestToken;
	}

	public String getLatestRequestToken() {
		return latestRequestToken;
	}
	
	
	public Iview getRequestView() {
		return requestView;
	}

	public void setRequestView(Iview requestView) {
		this.requestView = requestView;
	}

	public AskForWhat getLatestAskFor() {
		return latestAskFor;
	}

	public void setLatestAskFor(AskForWhat latestAskFor) {
		this.latestAskFor = latestAskFor;
	}

	public void setConfirmHint(String confirmHint) {
		this.confirmHint = confirmHint;
	}

	public String getConfirmHint() {
		return confirmHint;
	}

}
