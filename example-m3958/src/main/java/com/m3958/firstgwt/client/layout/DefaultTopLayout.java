package com.m3958.firstgwt.client.layout;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.IHasSiteChangHandler;
import com.m3958.firstgwt.client.IhasLoginEventHandler;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.event.ApplicationChangeEvent;
import com.m3958.firstgwt.client.event.ApplicationChangeEventHandler;
import com.m3958.firstgwt.client.event.LoginEvent;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.service.GwtRPCServiceAsync;
import com.m3958.firstgwt.client.service.MainMenuToViewService;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.HelpId;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.window.MessageWindow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;


@Singleton
public class DefaultTopLayout extends HLayout implements Iview,IhasLoginEventHandler,IHasSiteChangHandler{

	private ToolStrip toolStrip;
	
	public static List<HelpId> helpIds = new ArrayList<HelpId>();
	
	@Inject
	private GwtRPCServiceAsync gwtRPCService;
	
	private Label nicknameLabel;
	private Img avatar;
	
	private Label currentSite;
	
	private AppStatusService aservice;
	
	@Inject
	private VblockService bservice;
	
	@Inject
	private MessageWindow mw;
	
	
	@Inject
	private MainMenuToViewService mmtsw;
	
	private MyEventBus eventBus;
	
	private Img logoImg;


	@Inject
	public DefaultTopLayout(AppStatusService aservice,MyEventBus eventBus){
		this.aservice = aservice;
		this.eventBus = eventBus;
		setHeight(40);
		setWidth100();
		initContent();
		initListener();
		
	}
	
	private ApplicationChangeEventHandler ah = new ApplicationChangeEventHandler() {
		@Override
		public void onApplicationChange(ApplicationChangeEvent event) {
			if(!bservice.e2.isEmpty()){
			}else if(!bservice.g1.isEmpty()){
				
			}else{
				
			}
		}
	};
	
	public void initListener(){
		eventBus.addHandler(ApplicationChangeEvent.TYPE,ah);
	}
	
	private void initContent(){
		toolStrip = new ToolStrip();
		toolStrip.setHeight100();
		toolStrip.setWidth100();
		
		nicknameLabel = new Label();
		nicknameLabel.setValign(VerticalAlignment.CENTER);
		
		avatar = new Img();
		avatar.setWidth(30);
		avatar.setHeight(30);
		
		currentSite = new Label();
		currentSite.setValign(VerticalAlignment.CENTER);
		logoImg  = new Img("/images/logo/psalmslogo.jpg");
		logoImg.setWidth(200);
		logoImg.setHeight(30);
		toolStrip.addMember(logoImg);
		toolStrip.addSpacer(20);
		toolStrip.addMember(avatar);
		toolStrip.addMember(nicknameLabel);
		toolStrip.addMember(currentSite);
		toolStrip.addFill();

		
        ToolStripButton backButton = new ToolStripButton();
        backButton.setIcon("icons/16/back.png");
        backButton.setTitle("后退");
        
        backButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				bservice.back();
			}
		});
		
        ToolStripButton helpButton = new ToolStripButton();
//        helpButton.setIcon("icons/16/help.png");
        helpButton.setTitle("消息");

        ToolStripButton logoutButton = new ToolStripButton();
        logoutButton.setIcon("icons/16/Log-Out-icon.png");
        logoutButton.setTitle("退出");
        

        ToolStripButton feedbackButton = new ToolStripButton();
        feedbackButton.setIcon("icons/16/feedback.png");
        feedbackButton.setTitle("意见");
        
        feedbackButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mmtsw.newViewState(MainMenuEnum.FEED_BACK);
			}
		});
        
        helpButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mmtsw.changeToHmessageReceiveView();
//				mw.showme();
//				int i = helpIds.size();
//				@SuppressWarnings("unused")
//				HelpId hid;
//				if(i > 0){
//					hid = helpIds.get(helpIds.size());
//				}
			}
		});
        
		logoutButton.addClickHandler(new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			SC.confirm("确定退出？", new BooleanCallback(){
				@Override
				public void execute(Boolean value) {
					if(value){
						gwtRPCService.logout(new AsyncCallback<Void>() {
							@Override
							public void onSuccess(Void result) {
								aservice.setInitToken(History.getToken());
								aservice.setLogined(false);
								eventBus.fireEvent(new LoginEvent(false));
								bservice.clearAllBlockContents();
								bservice.b.setValues(ViewNameEnum.LOGIN, ViewAction.NO_ACTION);
								bservice.addHistoryItemAndFireApplicationEvent();
							}
							@Override
							public void onFailure(Throwable caught) {
								SC.say("注销出错！请关闭浏览器！");
							}
						});
					}
				}});
		}});
        
        toolStrip.addButton(backButton);
		toolStrip.addButton(helpButton);
		toolStrip.addButton(feedbackButton);
		toolStrip.addButton(logoutButton);
		addMember(toolStrip);
	}


	@Override
	public void changeDisplay(ViewAction va,String...paras) {
	}



	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.TOP;
	}



	@Override
	public Layout asLayout() {
		return this;
	}


	@Override
	public Btname[] getStripStatus() {
		return null;
	}


	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}
	
	@Override
	public String[] getParas() {
		return null;
	}


	@Override
	public void setUnInitialized() {
		
	}

	@Override
	public void onLoginEvent(boolean login) {
		if(login){
			setEmailAndImg();
		}
	}
	
	private void setEmailAndImg(){
		if(nicknameLabel != null && aservice.getSu() != null){
			nicknameLabel.setContents(aservice.getSu().getNickname() + "<a href=\"/openid.html#tab=3\" target=\"_blank\"><img src=\"/images/silk/icon_edit.png\" alt=\"编辑\" style=\"border:0;\"/></a>");
		}
		if(logoImg != null){
			setLogoImgSrc();
		}
		if(avatar != null && aservice.getSu() != null){
			avatar.setSrc(aservice.getSu().getAvatar());
		}
	}
	
	private void setLogoImgSrc(){
		String imgsrc = "/images/logo/psalmslogo.jpg";
		if(aservice.getWebSite() != null){
			String logoUrl = aservice.getWebSite().getLogoUrl();
			if(logoUrl != null && logoUrl.trim().length() > 0){
				imgsrc = logoUrl;
			}
		}
		logoImg.setSrc(imgsrc);
		logoImg.resetSrc();
	}

	@Override
	public void onSiteChange() {
		if(currentSite!=null)
			currentSite.setContents(aservice.getWebSite().getCname());
	}
}
