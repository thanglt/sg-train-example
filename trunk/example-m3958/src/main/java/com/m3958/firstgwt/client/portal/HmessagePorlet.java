package com.m3958.firstgwt.client.portal;

import java.util.HashMap;
import java.util.Map;

import com.m3958.firstgwt.client.datasource.HmessageDataSource;
import com.m3958.firstgwt.client.datasource.HmessageReceiverDataSource;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.HmessageField;
import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.UserField;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.Portlet;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;


public class HmessagePorlet extends Portlet{
	
	private HmessageWindow hw;
	
	private HmessageDetail detailWindow;
	
	private HmessageDetail getDw(){
		if(detailWindow == null){
			detailWindow = new HmessageDetail(this);
		}
		return detailWindow;
	}
	
	private HmessageWindow getHw(){
		if(hw == null){
			hw = new HmessageWindow();
		}
		return hw;
	}

	
	private ListGrid inboxGrid;
	
	private ListGrid outboxGrid;
	
	
	public HmessagePorlet(){
		setTitle("通知/消息/公告");
		addItem(initStack());
		IButton b = new IButton("新建消息");
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
//				getHw().newHmessage();
			}
		});
		addItem(b);
	}
	
	private Canvas initStack(){
        final SectionStack sectionStack = new SectionStack();        
        sectionStack.setWidth100();
        sectionStack.setHeight100();
        sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
        sectionStack.setAnimateSections(true);
        sectionStack.setOverflow(Overflow.HIDDEN);

        SectionStackSection inSection = new SectionStackSection();
        inSection.setTitle("收件箱");
        inSection.setExpanded(true);
        inSection.setShowHeader(true);
        inSection.setItems(initInboxGrid());
        
        SectionStackSection outSection = new SectionStackSection();
        outSection.setTitle("发件箱");
        outSection.setExpanded(true);
        outSection.setShowHeader(true);
        outSection.setItems(initoutBoxLayout());

        sectionStack.setSections(inSection, outSection);
        sectionStack.expandSection(0);
        
		return sectionStack;
	}

	private Canvas initoutBoxLayout() {
		outboxGrid = new ListGrid();
		outboxGrid.setWidth100();
		outboxGrid.setHeight100();
		outboxGrid.setAutoFetchData(false);
//		outboxGrid.setDataSource(HmessageDataSource.getInstance());
		
		outboxGrid.setShowRowNumbers(true);
		outboxGrid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
		
		SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
		outboxGrid.setInitialSort(ssf);
		
	    ListGridField pkField = new ListGridField("id");
	    pkField.setHidden(true);
	    ListGridField versionField = new ListGridField("version");
	    versionField.setHidden(true);
	    ListGridField createdAtField = new ListGridField(CommonField.CREATED_AT.getEname(),CommonField.CREATED_AT.getCname());
	    createdAtField.setHidden(true);
		
		ListGridField titlefField = new ListGridField(HmessageField.TITLE.getEname(), HmessageField.TITLE.getCname());
		ListGridField messageField = new ListGridField(HmessageField.MESSAGE.getEname(), HmessageField.MESSAGE.getCname());
		
		outboxGrid.setFields(pkField,versionField,createdAtField,titlefField,messageField);
		
		fetchOutMessage();
		return outboxGrid;
	}

	private Canvas initInboxGrid() {
		inboxGrid = new ListGrid();
		inboxGrid.setWidth100();
		inboxGrid.setHeight100();
		inboxGrid.setAutoFetchData(false);
//		inboxGrid.setDataSource(HmessageReceiverDataSource.getInstance());
		
		inboxGrid.setShowRowNumbers(true);
		inboxGrid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
		SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
		inboxGrid.setInitialSort(ssf);
		
		inboxGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
					getDw().showMessage(event.getRecord());
				}
			});
		
	    ListGridField pkField = new ListGridField("id");
	    pkField.setHidden(true);
	    ListGridField versionField = new ListGridField("version");
	    versionField.setHidden(true);
	    ListGridField createdAtField = new ListGridField(CommonField.CREATED_AT.getEname(),CommonField.CREATED_AT.getCname());
	    createdAtField.setHidden(true);
		
	    ListGridField fromField = new ListGridField(HmessageReceiverField.HMESSAGE.getEname(),HmessageField.SENDER.getCname());
	    
	    fromField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				Record hm = new Record(record.getAttributeAsJavaScriptObject(HmessageReceiverField.HMESSAGE.getEname()));
				Record sender = new Record(hm.getAttributeAsJavaScriptObject(HmessageField.SENDER.getEname()));
				return sender.getAttributeAsString(UserField.LOGIN_NAME.getEname());
			}
		});
	    fromField.setWidth(50);
	    
		ListGridField titlefField = new ListGridField(HmessageReceiverField.HMESSAGE.getEname(),HmessageField.TITLE.getCname());
		
	    titlefField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				Record hm = new Record(record.getAttributeAsJavaScriptObject(HmessageReceiverField.HMESSAGE.getEname()));
				return hm.getAttributeAsString(HmessageField.TITLE.getEname());
			}
		});
		
		ListGridField messageField = new ListGridField(HmessageReceiverField.HMESSAGE.getEname(), HmessageField.MESSAGE.getCname());
		
		messageField.setHidden(true);
		
		inboxGrid.setFields(pkField,versionField,createdAtField,fromField,titlefField,messageField);
		
		inboxGrid.setContextMenu(getMyContextMenu());
		
		fetchInMessage();
		return inboxGrid;
	}

	private void fetchInMessage() {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.MODEL_NAME,HmessageReceiverDataSource.className);
//	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, AppVars.getSu().getId());
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME,HmessageReceiverField.RECEIVER.getEname());
	    c.addCriteria(SmartParameterName.IS_MASTER, "1");
	    inboxGrid.fetchData(c);
	}
	
	private void fetchOutMessage() {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.MODEL_NAME,HmessageDataSource.className);
//	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, AppVars.getSu().getId());
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME,HmessageField.SENDER.getEname());
	    c.addCriteria(SmartParameterName.IS_MASTER, "1");
	    outboxGrid.fetchData(c);
	}
	
	private DSRequest createDsRequest(){
		DSRequest dr = new DSRequest();
		Map<String, String> m = new HashMap<String, String>();
		dr.setParams(m);
		return dr;
	}
	
	private Menu getMyContextMenu(){
        Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(3);
        
        MenuItem refreshItem = new MenuItem("刷新");
        MenuItem deleteItem = new MenuItem("删除", "icons/16/close.png");

        menu.setItems(refreshItem,deleteItem);
        
        menu.addItemClickHandler(new ItemClickHandler(){

			@Override
			public void onItemClick(ItemClickEvent event) {
				if("删除".equals(event.getItem().getTitle())){
						SC.confirm("真的删除？", new BooleanCallback(){
							public void execute(Boolean value) {
								if(value){
									inboxGrid.removeData(inboxGrid.getSelectedRecord(),new MyDsCallback(){
										@Override
										public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
											;
										}},createDsRequest());
								}
							}
						});
				}else if("刷新".equals(event.getItem().getTitle())){
					inboxGrid.invalidateCache();
				}else{
					;
				}
			}});
        return menu;
	}

	public void replyHmessage(Record hm, Record sender, Record[] attas) {
//		getHw().replyHmessage(hm,sender,attas);
	}
}
