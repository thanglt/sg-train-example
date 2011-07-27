package com.m3958.firstgwt.client.layout;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.m3958.firstgwt.client.ICanRefresh;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.AssetDataSource;
import com.m3958.firstgwt.client.datasource.DiskFileDataSource;
import com.m3958.firstgwt.client.datasource.WebSiteDataSource;
import com.m3958.firstgwt.client.event.RecordEvent;
import com.m3958.firstgwt.client.event.RecordEvent.RecordEventType;
import com.m3958.firstgwt.client.jso.AssetJso;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.rpc.MyRpcCallback;
import com.m3958.firstgwt.client.service.AppUtilService;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.DiskFileField;
import com.m3958.firstgwt.client.types.IHasAttachment;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

@Singleton
public class DiskFileLayout extends MySimpleGridLayout implements Iview,IHasAttachment,ICanRefresh{
	
	
	public static class ClonePageWindow extends Window{
		
		private DynamicForm df;
		
		public ClonePageWindow(){
			setTitle("请输入要克隆的页面的Url和保存的文件名");
			df = new DynamicForm();
			TextItem pageUrlItem = new TextItem("pageUrl", "页面路径");
			pageUrlItem.setWidth(250);
			TextItem fnItem = new TextItem("fn", "文件名称");
			ButtonItem surebt = new ButtonItem("surebt","确定");
			surebt.setEndRow(false);
			ButtonItem cancelbt = new ButtonItem("cancelbt","取消");
			cancelbt.setStartRow(false);
			df.setItems(pageUrlItem,fnItem,surebt,cancelbt);
			addItem(df);
			setWidth(450);
			setHeight(250);
			centerInPage();
		}
		
		public String[] getUserValues(){
			String[] ss = new String[2];
			ss[0] = df.getValueAsString("pageUrl");
			ss[1] = df.getValueAsString("fn");
			return ss;
		}
		
		public void addUserClickHandler(com.smartgwt.client.widgets.form.fields.events.ClickHandler ch){
			df.getItem("surebt").addClickHandler(ch);
			df.getItem("cancelbt").addClickHandler(ch);
		}
		
		
	}
	
	@Inject
	private DiskFileDataSource dds;
	
	private String dirPath = "/";
	
	private Label pathLabel;
	
	private String[] copiedPathAndFileName;

	private ToolStripButton upButton;
	
	private ToolStripButton downButton;
	
	private ToolStripButton unzipButton;
	
	private ToolStripButton copyButton;
	
	private ToolStripButton pasterButton;
	
	private ToolStripButton bakButton;
	
	private ToolStripButton clonePageButton;
	
	private ToolStrip toolStrip;
	
	private String fileName;
	
	private boolean isFolder;
	
	private boolean showBak = false;
	private String siteId;
	
	private ClonePageWindow cpw;
	
	public Btname[] btStatus0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] btStatus1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH};
	public Btname[] btStatus2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};
	
	
	@Inject
	public DiskFileLayout(@Named("DISKFILE") AssetUploadLayout aul){
		addMember(initToolStrip());
		addMember(aul);
		cpw = new ClonePageWindow();
	}
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
		grid.setWidth100();
		grid.setHeight100();
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(DiskFileField.LAST_MODIFIED.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setFilterOnKeypress(true);
	    grid.setDataSource(dds);
	    grid.setAutoFetchData(false);
	    
    
	    ListGridField lastModifiedField = new ListGridField(DiskFileField.LAST_MODIFIED.getEname(),DiskFileField.LAST_MODIFIED.getCname());
	    lastModifiedField.setCanFilter(false);
	    lastModifiedField.setWidth(120);
	    
	    ListGridField nameField = new ListGridField(DiskFileField.FILE_NAME.getEname(),DiskFileField.FILE_NAME.getCname());
	    
//        nameField.setCellFormatter(new CellFormatter() {
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum,
//					int colNum) {
//				
//				if(record.getAttributeAsBoolean(DiskFileField.IS_FOLDER.getEname())){
//					return (String) value;
//				}else{
//					Map<String, String> params = new HashMap<String, String>();
//					params.put(SmartParameterName.MODEL_NAME, DiskFileDataSource.className);
//					params.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.FETCH.getValue());
//					params.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE.toString());
//					params.put(DiskFileField.SITE_ID.getEname(), relationModelId);
//					params.put(DiskFileField.DIR_PATH.getEname(), dirPath);
//					params.put(DiskFileField.FILE_NAME.getEname(), record.getAttributeAsString(DiskFileField.FILE_NAME.getEname()));
//					params.put(DiskFileField.IS_FOLDER.getEname(), String.valueOf(false));
//					String url = StringUtils.addUrlParams(SmartConstants.SMART_CRUD_URL, params);
//					return "<a href='" + url + "' target='_blank'>" + value + "</a>";
//				}
//			}
//		});

	    ListGridField fileSizeField = new ListGridField(DiskFileField.FILE_SIZE.getEname(),DiskFileField.FILE_SIZE.getCname());
	    fileSizeField.setCellFormatter(new AppUtilService.fileSizeFormatter());
	    
	    ListGridField folderField = new ListGridField(DiskFileField.IS_FOLDER.getEname(),DiskFileField.IS_FOLDER.getCname());
	    folderField.setWidth(20);
	    
	    grid.setFields(folderField,nameField,fileSizeField,lastModifiedField);
	    grid.addSelectionChangedHandler(selectionChangeHandler);
	    
	    grid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record r = event.getRecord();
				if(r.getAttributeAsBoolean(DiskFileField.IS_FOLDER.getEname())){
					String dp = dirPath;
					if(dp.endsWith("/"))dp = dp.substring(0, dp.length()-1);
					dirPath = dp + "/" + r.getAttributeAsString(DiskFileField.FILE_NAME.getEname());
					fetchGridData();
					upButton.show();
					togglePasterBt();
				}
				
			}
		});
	    
	    grid.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				Record r = event.getRecord();
				isFolder = r.getAttributeAsBoolean(DiskFileField.IS_FOLDER.getEname());
				fileName = r.getAttributeAsString(DiskFileField.FILE_NAME.getEname());
				if(isFolder){
					downButton.show();
				}else{
					downButton.hide();
				}
				if(fileName.toLowerCase().endsWith(".zip") && !isFolder){
					unzipButton.show();
				}else{
					unzipButton.hide();
				}
				copyButton.show();
			}
		});
	    
	}
	
	private void togglePasterBt(){
		copyButton.hide();
		if(copiedPathAndFileName == null || dirPath.equals(copiedPathAndFileName[0])){
			pasterButton.hide();
		}else{
			pasterButton.show();
		}
	}
	
	private Canvas initToolStrip() {
		toolStrip = new ToolStrip();
		toolStrip.setWidth100();
		Label l = new Label("当前目录:");
		pathLabel = new Label("/");
		pathLabel.setWidth100();
		toolStrip.addSpacer(5);
		toolStrip.addMember(l);
		l.setWidth(55);
		toolStrip.addMember(pathLabel);
		
		upButton = new ToolStripButton("上级目录");
		upButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dirPath = dirPath.substring(0, dirPath.lastIndexOf("/"));
				if(dirPath.isEmpty())dirPath = "/";
				if("/".equals(dirPath))upButton.hide();
				fetchGridData();
				togglePasterBt();
			}
		});
		upButton.hide();
		toolStrip.addButton(upButton);
		
		downButton = new ToolStripButton("进入目录");
		downButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				downButton.hide();
				Record r = grid.getSelectedRecord();
				if(r.getAttributeAsBoolean(DiskFileField.IS_FOLDER.getEname())){
					String dp = dirPath;
					if(dp.endsWith("/"))dp = dp.substring(0, dp.length()-1);
					dirPath = dp + "/" + r.getAttributeAsString(DiskFileField.FILE_NAME.getEname());
					fetchGridData();
					upButton.show();
					togglePasterBt();
				}
			}
		});
		downButton.hide();
		toolStrip.addButton(downButton);
		
		
		
		unzipButton = new ToolStripButton("zip解压");
		unzipButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SC.showPrompt("正在解压文件，请耐心等待....");
				Map<String, String> m = auService.getDiskFileMap(bservice.getSiteId(), dirPath, fileName, "", "", false,"0");
		    	RPCManager.sendRequest(auService.getDiskFileRpcRequest(m,SmartOperationName.CUSTOM.getValue(),SmartSubOperationName.UNZIP.toString(),"POST"),new MyRpcCallback(){
					@Override
					public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
						JSONArray ja = data.isArray();
						if(ja.size() > 0){
							auiService.showTmpPrompt("已解压！");
							grid.invalidateCache();
						}
					}});
			}
		});
		
		toolStrip.addButton(unzipButton);
		unzipButton.hide();
		
		
		copyButton = new ToolStripButton("剪切");
		copyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				copiedPathAndFileName = new String[]{dirPath,fileName};
				String s = copiedPathAndFileName[0] + "/" + copiedPathAndFileName[1];
				auiService.showTmpPrompt(s + "已复制！");
			}
		});
		
		toolStrip.addButton(copyButton);
		copyButton.hide();
		
		pasterButton = new ToolStripButton("粘贴");
		pasterButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SC.showPrompt("正在粘贴，请耐心等待....");
				Map<String, String> m = auService.getDiskFileMap(bservice.getSiteId(), dirPath, copiedPathAndFileName[0] + "/" + copiedPathAndFileName[1], "", "", false,"0");
		    	RPCManager.sendRequest(auService.getDiskFileRpcRequest(m,SmartOperationName.CUSTOM.getValue(),SmartSubOperationName.PASTER_DISK_FILE.toString(),"POST"),new MyRpcCallback(){
					@Override
					public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
						JSONArray ja = data.isArray();
						if(ja.size() > 0){
							auiService.showTmpPrompt("已粘贴！");
							copiedPathAndFileName = null;
							pasterButton.hide();
							grid.invalidateCache();
						}
					}});
			}
		});
		
		toolStrip.addButton(pasterButton);
		
		
		
		clonePageButton = new ToolStripButton("克隆页面");
		clonePageButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				cpw.show();
				cpw.addUserClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					
					@Override
					public void onClick(
							com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						if("cancelbt".equals(event.getItem().getName())){
							cpw.hide();
							return;
						}
						String[] ss = cpw.getUserValues();
						if(ss[0] == null || ss[0].isEmpty()){
							SC.warn("请输入远程页面的地址");
							return;
						}
						
				    	Map<String, String> m = new HashMap<String, String>();
				    	m.put(SmartParameterName.MODEL_NAME, WebSiteDataSource.className);
				    	m.put(SmartParameterName.OPERATION_TYPE,SmartOperationName.CUSTOM.getValue());
				    	m.put(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.MQ.toString());
				    	m.put("dowhat","clonepage");
				    	m.put("url", ss[0]);
				    	m.put("fn", ss[1]);
				    	m.put("siteId", siteId);
				    	m.put("path", dirPath);
				    	m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
				    	RPCRequest rr = new RPCRequest();
				    	rr.setHttpMethod("GET");
				    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
				    	rr.setParams(m);
				    	RPCManager.sendRequest(rr,new MyRpcCallback() {
							@Override
							public void afterSuccess(RPCResponse response, Object rawData,
									RPCRequest request, JSONValue data) {
								if(data.isBoolean().booleanValue()){
									SC.say("任务已经提交，完成之后系统会通知您！当然您也可以按刷新来查看！");
								}else{
									SC.say("不好意思，任务无法完成！");
								}
							}
						});
						cpw.hide();
					}
				});
				
//				SC.askforValue("请输入页面地址", "请输入页面地址", new ValueCallback() {
//					@Override
//					public void execute(String value) {
//						if(value == null || value.length()<5){
//							SC.warn("请输入远程页面的地址");
//							return;
//						}else{
//					    	Map<String, String> m = new HashMap<String, String>();
//					    	m.put(SmartParameterName.MODEL_NAME, WebSiteDataSource.className);
//					    	m.put(SmartParameterName.OPERATION_TYPE,SmartOperationName.CUSTOM.getValue());
//					    	m.put(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.MQ.toString());
//					    	m.put("dowhat","clonepage");
//					    	m.put("url", value);
//					    	m.put("siteId", siteId);
//					    	m.put("path", dirPath);
//					    	m.put(CommonField.TIMEZONE.getEname(), bservice.getTimeZone());
//					    	RPCRequest rr = new RPCRequest();
//					    	rr.setHttpMethod("GET");
//					    	rr.setActionURL(SmartConstants.SMART_CRUD_URL);
//					    	rr.setParams(m);
//					    	RPCManager.sendRequest(rr,new MyRpcCallback() {
//								@Override
//								public void afterSuccess(RPCResponse response, Object rawData,
//										RPCRequest request, JSONValue data) {
//									if(data.isBoolean().booleanValue()){
//										SC.say("任务已经提交，完成之后系统会通知您！当然您也可以按刷新来查看！");
//									}else{
//										SC.say("不好意思，任务无法完成！");
//									}
//								}
//							});
//						}
//					}
//				});
			}
		});
		
		toolStrip.addButton(clonePageButton);
		
		
		
		bakButton = new ToolStripButton("显示备份");
		bakButton.setActionType(SelectionType.CHECKBOX);
		bakButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showBak = !showBak;
				fetchGridData();
			}
		});
		
		toolStrip.addButton(bakButton);

		toolStrip.addSpacer(20);
		toolStrip.addSpacer(30);
		return toolStrip;
	}
	
	protected SelectionChangedHandler selectionChangeHandler = new SelectionChangedHandler() {
		@Override
		public void onSelectionChanged(SelectionEvent event) {
			Record[] rs = event.getSelection();
			if(rs.length == 1){
				selectedRecordId = rs[0].getAttributeAsString(CommonField.ID.getEname());
				srservice.putRecord(selectedRecordId,rs[0]);
			}
			RecordEvent re = new RecordEvent(getViewName(), RecordEventType.SELECT,auService.getRecordsIds(rs));
			eventBus.fireEvent(re);
		}
	};
	
	@Override
	public String[] getParas() {
		String filename = "";
		String isfolder = "0";
		if(grid != null && grid.getSelectedRecord() != null){
			filename = grid.getSelectedRecord().getAttributeAsString(DiskFileField.FILE_NAME.getEname());
			if(grid.getSelectedRecord().getAttributeAsBoolean(DiskFileField.IS_FOLDER.getEname())){
				isfolder = "1";
			}
		}
		return new String[]{bservice.getSiteId(),dirPath,filename,isfolder,selectedRecordId};
	}
	
	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(copiedPathAndFileName == null){
			pasterButton.hide();
		}
		if(!initialized){
			initGrid();
			addMember(grid);
			initialized = true;
			firstCall = true;
		}
		switch (va) {
		case REMOVE:
			removeDiskfile();
			break;
		default:
			if(siteId == null ||(!bservice.getSiteId().equals(siteId) && !firstCall)){
				siteId = bservice.getSiteId();
				dirPath = "/";
				pathLabel.setContents("/");
				fetchGridData();
				firstCall = false;
			}
			break;
		}
	}
	
	private void removeDiskfile() {
		String rid = grid.getSelectedRecord().getAttributeAsString(CommonField.ID.getEname());
		boolean isf = grid.getSelectedRecord().getAttributeAsBoolean(DiskFileField.IS_FOLDER.getEname());
		Map<String, String> m = auService.getDiskFileMap(siteId, dirPath, fileName, "", "", isf,rid);
    	RPCManager.sendRequest(auService.getDiskFileRpcRequest(m,SmartOperationName.REMOVE.getValue(),"","POST"),new MyRpcCallback(){
			@Override
			public void afterSuccess(RPCResponse response, Object rawData, RPCRequest request, JSONValue data) {
				JSONArray ja = data.isArray();
				if(ja.size() > 0){
					auiService.showTmpPrompt("已删除！");
					grid.invalidateCache();
				}
			}});
	}

	@Override
	public Btname[] getStripStatus() {
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


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.DISKFILE;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.DISKFILE_EDIT;
	}

	@Override
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.MODEL_NAME, DiskFileDataSource.className);
		c.addCriteria(DiskFileField.SITE_ID.getEname(), siteId);
		c.addCriteria(DiskFileField.DIR_PATH.getEname(), dirPath);
		c.addCriteria(DiskFileField.SHOW_BACKUP.getEname(), showBak);
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c,new MyDsCallback() {
			@Override
			public void afterSuccess(DSResponse response, Object rawData,
					DSRequest request) {
				pathLabel.setContents(dirPath);
			}
		});
	}

	@Override
	public void refreshContent() {
		grid.invalidateCache();
	}

	@Override
	public void addAttachment(AssetJso assetJso, UploadFor uploadFor) {
		if(assetJso != null){
			RPCRequest rr = auService.getManageRelationRpcRequest(AssetDataSource.className, assetJso.getId()+"", true, WebSiteDataSource.className, siteId, true, dirPath + "," + assetJso.getOriginName());
			RPCManager.sendRequest(rr, new MyRpcCallback() {
				@Override
				public void afterSuccess(RPCResponse response, Object rawData,
						RPCRequest request, JSONValue data) {
					grid.invalidateCache();
				}
			});
		}
	}
}
