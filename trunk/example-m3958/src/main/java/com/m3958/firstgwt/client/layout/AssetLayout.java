package com.m3958.firstgwt.client.layout;

import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.m3958.firstgwt.client.ICanConfirmSome;
import com.m3958.firstgwt.client.datasource.AssetDataSource;
import com.m3958.firstgwt.client.datasource.AssetFolderDataSource;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.service.AppUtilService;
import com.m3958.firstgwt.client.types.AssetField;
import com.m3958.firstgwt.client.types.AssetFolderField;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.IHasAttachment;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SubmitItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class AssetLayout extends HasMMRelationGridLayout implements Iview,IHasAttachment,ICanConfirmSome{
	
	@Inject
	private AssetDataSource ads;
	
	@Inject
	public AssetLayout(@Named("ASSET") AssetUploadLayout aul){
		addMember(aul);
		addMember(getRemoteAssetLayout());
	}
	
	private Layout getRemoteAssetLayout(){
		VLayout v = new VLayout();
		v.setWidth100();
		final HLayout h = new HLayout();
		final DynamicForm df = new DynamicForm();
		TextItem urlItem = new TextItem("url","远程路径");
		df.setItems(urlItem);
		df.setWidth100();
		IButton ir = new IButton("开始抓取");
		ir.setWidth(80);
		ir.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String url = df.getValueAsString("url");
				if(url == null || url.isEmpty()){
					SC.warn("请输入地址");
					return;
				}
				RPCRequest r = auService.getCustomRemoteAssetRequest(url,relationModelId,bservice.getSiteId());
				
				RPCManager.sendRequest(r, new MyRpcCallback() {
					@Override
					public void afterSuccess(RPCResponse response, Object rawData,
							RPCRequest request, JSONValue data) {
						df.clearValues();
						grid.invalidateCache();
					}
				});
				
			}
		});
		
		IButton ib = new IButton("远程附件", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(h.isVisible()){
					h.hide();
				}else{
					h.show();
				}
				
			}
		});

		h.addMember(df);
		h.addMember(ir);
		h.hide();
		v.addMember(ib);
		v.addMember(h);
		return v;
	}
	
	public Btname[] btStatus0 = new Btname[]{Btname.REFRESH,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	public Btname[] btStatus1 = new Btname[]{Btname.REMOVE,Btname.EDIT,Btname.LEAVE_FOLDER,Btname.REFRESH,Btname.MINE,Btname.OTHERS,Btname.GROUPS,Btname.CONFIRM};
	public Btname[] btStatus2 = new Btname[]{Btname.REMOVE,Btname.REFRESH,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	
	public Btname[] btStatus00 = new Btname[]{Btname.REFRESH,Btname.SHOW_ALL,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	public Btname[] btStatus11 = new Btname[]{Btname.REMOVE,Btname.EDIT,Btname.SHOW_ALL,Btname.LEAVE_FOLDER,Btname.REFRESH,Btname.CONFIRM};
	public Btname[] btStatus22 = new Btname[]{Btname.REMOVE,Btname.REFRESH,Btname.SHOW_ALL,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
		grid.setCellHeight(48);
	    grid.setWidth100();
	    grid.setHeight100();
	    grid.setShowRowNumbers(true);
	    grid.setShowFilterEditor(true);
	    grid.setDataSource(ads);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setAutoFetchData(false);
	    grid.setCanDragRecordsOut(true);
	    
	    ListGridField thumbField = new ListGridField(AssetField.THUMBNAIL.getEname(),AssetField.THUMBNAIL.getCname());
	    thumbField.setType(ListGridFieldType.IMAGE);
	    
	    thumbField.setImageHeight(48);
	    thumbField.setImageWidth(48);
	    
	    
        ListGridField descriptionField = new ListGridField(AssetField.DESCRIPTION.getEname(),AssetField.DESCRIPTION.getCname());
        

        ListGridField filePathField = new ListGridField(AssetField.FILE_PATH.getEname(),AssetField.FILE_PATH.getCname());
        filePathField.setHidden(true);
        ListGridField fileIdField = new ListGridField(AssetField.FILE_ID.getEname(),AssetField.FILE_ID.getCname());
        fileIdField.setHidden(true);
        ListGridField fileSizeField = new ListGridField(AssetField.FILE_SIZE.getEname(),AssetField.FILE_SIZE.getCname());
        
        fileSizeField.setCellFormatter(new AppUtilService.fileSizeFormatter());
        
        ListGridField mimeTypeField = new ListGridField(AssetField.MIME_TYPE.getEname(),AssetField.MIME_TYPE.getCname());
        
        ListGridField originNameField = new ListGridField(AssetField.ORIGIN_NAME.getEname(),AssetField.ORIGIN_NAME.getCname());
        
        originNameField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				return "<a href='" + record.getAttributeAsString("url") + "' target='_blank'>" + value + "</a>";
			}
		});
        
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),auiService.getCreatedAtField(false),thumbField,originNameField,mimeTypeField,fileIdField,filePathField,fileSizeField,descriptionField);
	    grid.addSelectionChangedHandler(getSelectionChangeHandler());
	}

	
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.ASSET;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.ASSET_EDIT;
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
				return btStatus22;
			}
		}else{
			if(grid == null)return btStatus0;
			ListGridRecord[] rs = grid.getSelection();
			if(rs.length == 0){
				return btStatus0;
			}else if(rs.length == 1){
				return btStatus1;
			}else{
				return btStatus2;
			}
		}
	}
	
	
	@Override
	public void setUnInitialized() {
		if(grid != null){
			removeMember(grid);
			grid = null;
		}
		initialized = false;
	}

	@Override
	public Record[] getConfirmedThing() {
		return grid.getSelection();
	}

	@Override
	public String getModelName() {
		return AssetDataSource.className;
	}

	@Override
	public String getRelationModelName() {
		return AssetFolderDataSource.className;
	}

	@Override
	public String getMasterSideProperty() {
		return AssetFolderField.ASSETS.getEname();
	}

	@Override
	public boolean isMaster() {
		return false;
	}

	@Override
	public String getRelationHint() {
		return null;
	}

	@Override
	public void addAttachment(AssetJso assetJso, UploadFor uploadFor) {
		RPCRequest r = auService.getManageRelationRpcRequest(AssetFolderDataSource.className,relationModelId, true, AssetDataSource.className,  assetJso.getId()+"", true, "");
		
		RPCManager.sendRequest(r, new MyRpcCallback() {
			@Override
			public void afterSuccess(RPCResponse response, Object rawData,
					RPCRequest request, JSONValue data) {
				grid.invalidateCache();
			}
		});
		
	}
}
