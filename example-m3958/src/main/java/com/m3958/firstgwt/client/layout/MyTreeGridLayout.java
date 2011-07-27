package com.m3958.firstgwt.client.layout;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.m3958.firstgwt.client.ICanRefresh;
import com.m3958.firstgwt.client.IhasRightSiblingView;
import com.m3958.firstgwt.client.SelectedRecordService;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.event.RecordEvent;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.events.FolderDropEvent;
import com.smartgwt.client.widgets.tree.events.FolderDropHandler;

public abstract class MyTreeGridLayout extends MyVlayout implements Iview,IhasRightSiblingView,ICanRefresh{
	
	protected String selectedRecordId;
	
	protected TreeGrid treeGrid;
	
	protected Record selectedRecord;
	
	protected String relationModelId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
	
	public Btname[] btsNoAdd0 = new Btname[]{Btname.REFRESH};
	public Btname[] btsNoAdd1 = new Btname[]{Btname.REMOVE,Btname.EDIT,Btname.LEAVE_FOLDER,Btname.REFRESH};
	public Btname[] btsNoAdd2 = new Btname[]{Btname.REFRESH};
	
	public Btname[] simpleBts0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] simpleBts1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH};
	public Btname[] simpleBts2 = new Btname[]{Btname.ADD,Btname.REFRESH};
	
	protected SelectionChangedHandler getSelectionChangeHandler(){
		return new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				Record[] rs = event.getSelection();
				if(rs.length == 1){
					selectedRecordId = rs[0].getAttributeAsString("id");
					srservice.putRecord(selectedRecordId,rs[0]);
				}
				
				for(Record r:rs){
					r.setAttribute(CommonField.RS_MODENAME.getEname(), getModelName());
				}
				RecordEvent re = new RecordEvent(getViewName(), RecordEventType.SELECT,auService.getRecordsIds(rs));
				eventBus.fireEvent(re);
			}
		};
	}
	
	
	protected FolderDropHandler getFdh(){
		return new FolderDropHandler() {
				@Override
				public void onFolderDrop(FolderDropEvent event) {
					Record[] beDroped = event.getNodes();
		        	if(beDroped.length == 0){
		        		SC.say("请选择要移动的对象！");
		        		return;
		        	}
		        	String rsModelName = beDroped[0].getAttribute(CommonField.RS_MODENAME.getEname());
		        	if(!getModelName().equals(rsModelName) && rsModelName != null){
						Record  targetContainer = event.getFolder();
						if(targetContainer.getJsObj() == null){
							event.cancel();
							return;
						}
						String targetContainerId = targetContainer.getAttributeAsString("id");
						
						event.cancel();
		            	RPCManager.sendRequest(auService.getManageRelationRpcRequest(getModelName(),targetContainerId,true,rsModelName,beDroped, true,getViewName().toString()),new MyRpcCallback(){
							@Override
							public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
								JSONArray dataa = data.isArray();
								if(dataa.size() > 0){
									auiService.showTmpPrompt("已加入该组！");
								}else{
									auiService.showTmpPrompt("你已经在组中！");
								}
							}});
		        	}else{
		        		;
		        	}
				}
			};
	}

	@Inject
	protected SelectedRecordService srs;
	
	protected abstract String getModelName();
	
	protected RecordClickHandler getRecordClickHandler(){
		RecordClickHandler rch = new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				selectedRecord = event.getRecord();
				selectedRecordId = selectedRecord.getAttributeAsString("id");
				srs.putRecord(selectedRecordId,selectedRecord);
				RecordEvent se = new RecordEvent(getViewName(), RecordEventType.CLICK_SELECT,selectedRecordId);
				eventBus.fireEvent(se);
			}
		};
		return rch;
	}
	
	
	@Override
	public Btname[] getStripStatus() {
		if(treeGrid == null)return simpleBts0;
		ListGridRecord[] rs = treeGrid.getSelection();
		if(rs.length == 0){
			return simpleBts0;
		}else if(rs.length == 1){
			return simpleBts1;
		}else{
			return simpleBts2;
		}
	}
	
	protected abstract void initTreeGrid();
	
	
	@Override
	public void setUnInitialized() {
		if(treeGrid != null){
			removeMember(treeGrid);
			treeGrid = null;
		}
		initialized = false;
	}
	
	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(!initialized){
			initTreeGrid();
			addMember(treeGrid);
			fetchChildren();
			initialized = true;
		}
		switch (va) {
		case REMOVE:
			treeGrid.removeSelectedData();
			break;
		default:
			if(!relationModelId.equals(auService.getStringIdPara(paras, 0))){
				relationModelId = auService.getStringIdPara(paras, 0);
				selectedRecordId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
				fetchChildren();
			}
			break;
		}
	}
	
	@Override
	public String[] getParas() {
		return new String[]{selectedRecordId};
	}
	
	protected void fetchChildren(){
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_CHILDREN.toString());
	    if(!SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(relationModelId)){
	    	c.addCriteria(SmartParameterName.RELATION_MODEL_ID, relationModelId);
	    }
	    c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
	    treeGrid.filterData(c);
	}
	
	@Override
	public void refreshContent() {
		selectedRecordId = null;
		treeGrid.invalidateCache();
	}
	
}
