package com.m3958.firstgwt.client.layout;


import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.MenuItemDataSource;
import com.m3958.firstgwt.client.datasource.MenuLevelDataSource;
import com.m3958.firstgwt.client.event.RecordEvent;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.MenuLevelField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;

@Singleton
public class MenuLevelLayout extends MySimpleGridLayout implements Iview{
	
	@Inject
	private MenuLevelDataSource mlds;
	

	

	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
		grid.setDataSource(mlds);
		grid.setAutoFetchData(false);
		grid.setCanAcceptDroppedRecords(true);
		grid.setSelectionType(SelectionStyle.SINGLE);
		
		grid.addRecordDropHandler(new RecordDropHandler() {
			@Override
			public void onRecordDrop(RecordDropEvent event) {
				Record[] menuItems = event.getDropRecords();
				Record ml = event.getTargetRecord();
				String mlId = ml.getAttributeAsString("id");
				event.cancel();
            	
            	if(menuItems.length == 0){
            		SC.say("请选择要移动的菜单项！");
            		return;
            	}
            	RPCManager.sendRequest(auService.getManageRelationRpcRequest(MenuLevelDataSource.className,mlId,true,MenuItemDataSource.className, menuItems, true,this.getClass().getName()),new MyRpcCallback(){
					@Override
					public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
							auiService.showTmpPrompt("已加入！");
					}});
			}
		});
		
        ListGridField nameField = new ListGridField(MenuLevelField.NAME.getEname(),MenuLevelField.NAME.getCname());

        grid.setFields(auiService.getIdField(true),auiService.getCreatedAtField(false),nameField,auiService.getVersionField());
	    
	    grid.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				Record r = event.getRecord();
				selectedRecordId = r.getAttributeAsString("id");
				srservice.putRecord(selectedRecordId,r);
				RecordEvent se = new RecordEvent(getViewName(), RecordEventType.CLICK_SELECT,r.getAttributeAsString("id"));
				eventBus.fireEvent(se);
			}
		});
	   
	}
		

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.MENULEVEL;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		switch (btname) {
		case EDIT:
			return ViewNameEnum.MENULEVEL_EDIT;
		case ADD:
			return ViewNameEnum.MENULEVEL_EDIT;
		default:
			break;
		}
		return null;
	}

	@Override
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}


}
