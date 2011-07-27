package com.m3958.firstgwt.client.window;

import java.util.Date;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.AppStatusService;
import com.m3958.firstgwt.client.datasource.HmessageReceiverDataSource;
import com.m3958.firstgwt.client.event.LoginEvent;
import com.m3958.firstgwt.client.event.LoginEventHandler;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.layout.HmessageReceiveLayout;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.m3958.firstgwt.client.types.HmessageStatus;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;


@Singleton
public class MessageWindow extends Window{
	
	private Timer t;
	
//	private ListGrid inboxGrid;
	
    private Label label;  
	
	private HmessageReceiverDataSource hrds;
	
	@Inject
	private HmessageReceiveLayout hrl;
	
	@Inject
	private AppStatusService aservice;
	
	@Inject
	private VblockService bservice;
	
	private MyEventBus eventBus;
	
	@Inject
	public MessageWindow(MyEventBus eventBus,HmessageReceiverDataSource hrds){
		this.eventBus = eventBus;
		this.hrds = hrds;
		setTitle("消息窗口");
		setWidth(300);
		setHeight(200);
		setLeft(0);
		setTop(-300);
		setAnimateMoveTime(2000);
		addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClientEvent event) {
				hideme();
			}
		});
		
		label = new Label();  
	    label.setWidth100();  
	    label.setHeight100();  
	    label.setPadding(5);  
	    label.setValign(VerticalAlignment.TOP);
		addItem(label);
		IButton cb = new IButton("不再提醒", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				t.cancel();
				hideme();
			}
		});
		addItem(cb);
		eventBus.addHandler(LoginEvent.TYPE, leh);
	}
	
	public void showme(){
		show();
		animateMove(0, 0);
	}
	
	public void hideme(){
		animateMove(0,-300,new AnimationCallback() {
			@Override
			public void execute(boolean earlyFinish) {
				hide();
			}
		},2000);
	}
	
	
	private void fillLabel(Record[] records){
		String s = "";
		for(Record r : records){
			s = "<b>";
			s += r.getAttributeAsString(HmessageReceiverField.TITLE.getEname()); 
			s += "</b><br/>";
			s += r.getAttributeAsString(HmessageReceiverField.MSG_BODY.getEname());
			s += "</br></br>";
		}
		label.setContents(s);
	}
	
	
//	private Canvas initInboxGrid() {
//		inboxGrid = new ListGrid();
//		inboxGrid.setWidth100();
//		inboxGrid.setHeight100();
//		inboxGrid.setAutoFetchData(false);
//		inboxGrid.setDataSource(hrds);
//		
//		inboxGrid.setShowRowNumbers(true);
//		inboxGrid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
//		SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
//		inboxGrid.setInitialSort(ssf);
//		
//	    ListGridField pkField = new ListGridField("id");
//	    pkField.setHidden(true);
//	    ListGridField versionField = new ListGridField("version");
//	    versionField.setHidden(true);
//	    ListGridField createdAtField = new ListGridField(CommonField.CREATED_AT.getEname(),CommonField.CREATED_AT.getCname());
//	    createdAtField.setHidden(true);
//		
//	    ListGridField fromField = new ListGridField(HmessageField.SENDER.getEname(),HmessageField.SENDER.getCname());
//	    
//		ListGridField titlefField = new ListGridField(HmessageField.TITLE.getEname(),HmessageField.TITLE.getCname());
//		
//		ListGridField messageField = new ListGridField(HmessageField.MESSAGE.getEname(), HmessageField.MESSAGE.getCname());
//		
//		messageField.setHidden(true);
//		
//		inboxGrid.setFields(pkField,versionField,createdAtField,fromField,titlefField,messageField);
//
//		return inboxGrid;
//	}
	
	private Criteria getCriteria() {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.MODEL_NAME,HmessageReceiverDataSource.className);
	    c.addCriteria(SmartParameterName.NAMED_QUERY_NAME, "findMyHmrByStatus");
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.NAMED_QUERY.toString());
	    c.addCriteria(HmessageReceiverField.STATUS.getEname(),HmessageStatus.UNREAD.toString());
	    return c;
	}
	
	private void fetchInMessage(){
		hrds.filterData(getCriteria(), new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == 0 && response.getData().length > 0){
					fillLabel(response.getData());
					hrl.refreshInbox();
					showme();
				}
			}
		});
	}
	
	
	private void initAndStartTimer(){
		fetchInMessage();
		t = new Timer(){
			@Override
			public void run() {
				Long lastMove = new Date().getTime() - bservice.getLastMouseMoveTimeStamp();
				if(lastMove > 3*60*1000){//如果3分钟没有移动鼠标，假设用户已经离开。
					;
				}else{
					fetchInMessage();
				}
			}
		};
		t.scheduleRepeating(180000);
	}
	
	private LoginEventHandler leh =  new LoginEventHandler() {
		@Override
		public void onLoginEvent(LoginEvent event) {
				boolean login = event.isLogin();
				if(login){
					initAndStartTimer();
				}else{
					t.cancel();
				}
			}
		};

}
