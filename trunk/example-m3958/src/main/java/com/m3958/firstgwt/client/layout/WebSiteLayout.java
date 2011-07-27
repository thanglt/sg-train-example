package com.m3958.firstgwt.client.layout;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanShare;
import com.m3958.firstgwt.client.datasource.WebSiteDataSource;
import com.m3958.firstgwt.client.event.SiteChangeEvent;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.WebSiteField;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

@Singleton
public class WebSiteLayout extends MySimpleGridLayout implements Iview,ICanShare{
	
	@Inject
	private WebSiteDataSource wsds;
	private Btname[] btStatus00 = new Btname[]{Btname.ADD,Btname.REFRESH,Btname.SHOW_ALL};
	private Btname[] btStatus1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.HOSTNAME,Btname.ARTICLE,Btname.MEDIA,Btname.LINK,Btname.STATIC_FILE,Btname.XINJIAN,Btname.REFRESH,Btname.SHARE_TO_USER,Btname.SHARE_TO_GROUP,Btname.VOTE,Btname.COMMENT,Btname.PAGE_MISTAKE};
	private Btname[] btStatus11 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.HOSTNAME,Btname.ARTICLE,Btname.MEDIA,Btname.LINK,Btname.STATIC_FILE,Btname.XINJIAN,Btname.REFRESH,Btname.SHOW_ALL,Btname.SHARE_TO_USER,Btname.SHARE_TO_GROUP,Btname.VOTE,Btname.COMMENT,Btname.PAGE_MISTAKE};
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
		grid.setDataSource(wsds);
		grid.setAutoFetchData(false);
		grid.setCanAcceptDroppedRecords(true);
		grid.setShowFilterEditor(true);
		
        ListGridField cnameField = new ListGridField(WebSiteField.CNAME.getEname(),WebSiteField.CNAME.getCname());
        ListGridField enameField = new ListGridField(WebSiteField.ENAME.getEname(),WebSiteField.ENAME.getCname());
        ListGridField logoUrlField = new ListGridField(WebSiteField.LOGO_URL.getEname(),WebSiteField.LOGO_URL.getCname());
        ListGridField cacheEnableField = new ListGridField(WebSiteField.CACHE_ENABLE.getEname(),WebSiteField.CACHE_ENABLE.getCname());
        ListGridField stopField = new ListGridField(WebSiteField.STOP.getEname(),WebSiteField.STOP.getCname());
        ListGridField updateCacheField = new ListGridField("updateCache","更新缓存");
        updateCacheField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return "<span style=\"cursor:pointer;\">更新缓存</span>";
			}
		});
        updateCacheField.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				Map<String, String> m = new HashMap<String, String>();
		    	m.put(SmartParameterName.MODEL_NAME, WebSiteDataSource.className);
		    	m.put(CommonField.SITE_ID.getEname(), event.getRecord().getAttributeAsString(CommonField.ID.getEname()));
		    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.CUSTOM.getValue());
		    	m.put(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.REFRESH_SITE_CACHE.toString());
		    	RPCRequest rr = new RPCRequest();
		    	rr.setHttpMethod("POST");
		    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
		    	rr.setParams(m);
		    	RPCManager.sendRequest(rr,new MyRpcCallback(){
					@Override
					public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
						boolean b = data.isBoolean().booleanValue();
						if(b){
							auiService.showTmpPrompt("已刷新！");
						}
					}});
				
			}
		});
        
        
        ListGridField rebuildSolrField = new ListGridField("rebuildSolr","重建索引");
        rebuildSolrField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return "<span style=\"cursor:pointer;\">重建索引</span>";
			}
		});
        rebuildSolrField.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				final Record er = event.getRecord();
				SC.confirm("重建索引比较费时，你确认重建？", new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if(value){
							Map<String, String> m = new HashMap<String, String>();
					    	m.put(SmartParameterName.MODEL_NAME, WebSiteDataSource.className);
					    	m.put(CommonField.SITE_ID.getEname(), er.getAttributeAsString(CommonField.ID.getEname()));
					    	m.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.CUSTOM.getValue());
					    	m.put(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.REBUILD_SOLR_INDEX.toString());
					    	RPCRequest rr = new RPCRequest();
					    	rr.setHttpMethod("POST");
					    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
					    	rr.setParams(m);
					    	RPCManager.sendRequest(rr,new MyRpcCallback(){
								@Override
								public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
									boolean b = data.isBoolean().booleanValue();
									if(b){
										auiService.showTmpPrompt("重建指令已发送");
									}
								}});
						}
						
					}
				});
			}
		});
        
        
        logoUrlField.setHidden(true);
        grid.setFields(auiService.getIdField(true),auiService.getCreatedAtField(false),cnameField,enameField,auiService.getVersionField(),logoUrlField,cacheEnableField,updateCacheField,rebuildSolrField,stopField);
        grid.addSelectionChangedHandler(getSelectionChangedHandler());
        grid.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				Record r = event.getRecord();
				if(r != null){
					SiteChangeEvent sce = new SiteChangeEvent(r.getAttributeAsString(CommonField.ID.getEname()), r.getAttributeAsString(CommonField.ID.getEname()));
					eventBus.fireEvent(sce);
				}
			}
		});
	}
		
	@Override
	public void fetchGridData(){
	    Criteria c = new Criteria();
	    if(!aservice.isSuperman()){
	    	c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.HAS_PERMISSION.toString());
	    }
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}
	
	
	@Override
	public Btname[] getStripStatus() {
		if(aservice.isSuperman()){
			if(grid == null)return btStatus00;
			ListGridRecord[] rs = grid.getSelection();
			if(rs.length == 0){
				return btStatus00;
			}else if(rs.length == 1){
				return btStatus11;
			}else{
				return btStatus00;
			}
		}else{
			if(grid == null)return simpleBts0;
			ListGridRecord[] rs = grid.getSelection();
			if(rs.length == 0){
				return simpleBts0;
			}else if(rs.length == 1){
				return btStatus1;
			}else{
				return simpleBts0;
			}
		}
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.WEBSITE;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		switch (btname) {
		case EDIT:
			return ViewNameEnum.WEBSITE_EDIT;
		case ADD:
			return ViewNameEnum.WEBSITE_EDIT;
		case HOSTNAME:
			return ViewNameEnum.WEBHOST;
		case STATIC_FILE:
			return ViewNameEnum.DISKFILE;
		case LINK:
			return ViewNameEnum.LINK;
		case VOTE:
			return ViewNameEnum.VOTE;
		case COMMENT:
			return ViewNameEnum.COMMENT;
		case PAGE_MISTAKE:
			return ViewNameEnum.PAGE_MISTAKE;
		default:
			break;
		}
		return null;
	}

	@Override
	public String getOid() {
		return bservice.getSiteId();
	}

	@Override
	public String getModelName() {
		return WebSiteDataSource.className;
	}

	@Override
	public String getOname() {
		if(grid.getSelectedRecord() != null){
			return grid.getSelectedRecord().getAttributeAsString(WebSiteField.CNAME.getEname());
		}else{
			return "";
		}
	}
}
