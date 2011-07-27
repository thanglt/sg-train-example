package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.HmessageDataSource;
import com.m3958.firstgwt.client.datasource.HmessageReceiverDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.service.VblockService;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.HmessageField;
import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.m3958.firstgwt.client.types.HmessageStatus;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.SearchForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;

@Singleton
public class HmessageReceiveLayout extends MySimpleGridLayout {
	
	
	public static class HmListTabPane extends TabSet{
		
	    private ListGrid hmrList;
	    
	    private ListGrid hmList;
	    
	    private boolean inBoxSelected = true;
	    
	    private VblockService bservice;
	    
	    private HLayout rollOverCanvas_hmr;
	    private ListGridRecord rollOverRecord_hmr;
	    
	    private HLayout rollOverCanvas_hm;
	    private ListGridRecord rollOverRecord_hm;
	    
		public HmListTabPane(VblockService bservice,final AppUiService auiService,DataSource hmrds,DataSource hmDs){
			this.bservice = bservice;
		    
			hmrList = new ListGrid(){
		        @Override
		        protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
		        	rollOverRecord_hmr = this.getRecord(rowNum);
		            if(rollOverCanvas_hmr == null) {
		                rollOverCanvas_hmr = new HLayout(3);
		                rollOverCanvas_hmr.setSnapTo("TR");
		                rollOverCanvas_hmr.setWidth(50);
		                rollOverCanvas_hmr.setHeight(22);

		                ImgButton editImg = new ImgButton();
		                editImg.setShowDown(false);
		                editImg.setShowRollOver(false);
		                editImg.setLayoutAlign(Alignment.CENTER);
		                editImg.setSrc("icons/16/close.png");
		                editImg.setPrompt("删除");
		                editImg.setHeight(16);
		                editImg.setWidth(16);
		                editImg.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
		                    public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
		                    	SC.confirm("删除？", new BooleanCallback() {
									@Override
									public void execute(Boolean value) {
										if(value){
											hmrList.removeData(rollOverRecord_hmr);
										}
									}
								});
		                    }

		                });
		                rollOverCanvas_hmr.addMember(editImg);
		            }
		            return rollOverCanvas_hmr;
		        }
		    };
			
			
			hmrList.setShowRollOverCanvas(true);
			hmrList.setDataSource(hmrds);

	        ListGridField titleField = new ListGridField(HmessageReceiverField.TITLE.getEname(), HmessageReceiverField.TITLE.getCname());
	        titleField.setShowHover(true);
	        
	        ListGridField senderNameField = new ListGridField(HmessageReceiverField.SENDER.getEname(),HmessageReceiverField.SENDER.getCname());
	        senderNameField.setWidth(100);
	        
	        ListGridField statusField = new ListGridField(HmessageReceiverField.STATUS.getEname(),HmessageReceiverField.STATUS.getCname());
	        statusField.setWidth(50);
	        
	        ListGridField ca = auiService.getCreatedAtField(true);
	        ca.setWidth(100);

	        hmrList.setFields(auiService.getIdField(true),titleField, senderNameField,statusField);
	        hmrList.setCanEdit(false);
	        hmrList.setCanDragRecordsOut(true);
	        setHoverWidth(200);
	        setHoverHeight(20);
	        hmrList.setSelectionType(SelectionStyle.SINGLE);
	        
			
			hmList = new ListGrid(){
		        @Override
		        protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
		        	rollOverRecord_hm = this.getRecord(rowNum);
		            if(rollOverCanvas_hm == null) {
		                rollOverCanvas_hm = new HLayout(3);
		                rollOverCanvas_hm.setSnapTo("TR");
		                rollOverCanvas_hm.setWidth(50);
		                rollOverCanvas_hm.setHeight(22);

		                ImgButton editImg = new ImgButton();
		                editImg.setShowDown(false);
		                editImg.setShowRollOver(false);
		                editImg.setLayoutAlign(Alignment.CENTER);
		                editImg.setSrc("icons/16/close.png");
		                editImg.setPrompt("删除");
		                editImg.setHeight(16);
		                editImg.setWidth(16);
		                editImg.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
		                    public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
		                    	SC.confirm("删除？", new BooleanCallback() {
									@Override
									public void execute(Boolean value) {
										if(value){
											hmList.removeData(rollOverRecord_hm);
										}
									}
								});
		                    }

		                });
		                rollOverCanvas_hm.addMember(editImg);
		            }
		            return rollOverCanvas_hm;
		        }
		    };
		    
		    hmList.setShowRollOverCanvas(true);
			hmList.setDataSource(hmDs);

	        ListGridField titleField1 = new ListGridField(HmessageReceiverField.TITLE.getEname(), HmessageReceiverField.TITLE.getCname());
	        titleField.setShowHover(true);
	        
	        
	        ListGridField ca1 = auiService.getCreatedAtField(true);
	        ca1.setWidth(100);

	        hmList.setFields(auiService.getIdField(true),titleField1,ca1);
	        hmList.setCanEdit(false);
	        hmList.setCanDragRecordsOut(true);
	        setHoverWidth(200);
	        setHoverHeight(20);
	        hmList.setSelectionType(SelectionStyle.SINGLE);
	        

	        
	        
	        Tab inBoxTab = new Tab("收件箱");
	        inBoxTab.setIcon("silk/application_form.png");
	        inBoxTab.setWidth(70);
	        inBoxTab.setPane(hmrList);

	        Tab outBoxTab = new Tab("发件箱");
	        outBoxTab.setIcon("silk/icon_edit.png");
	        outBoxTab.setWidth(70);
	        outBoxTab.setPane(hmList);

	        setTabs(inBoxTab, outBoxTab);
	        
	        addTabSelectedHandler(new TabSelectedHandler() {
				
				@Override
				public void onTabSelected(TabSelectedEvent event) {
					String s = event.getTab().getTitle();
					if("收件箱".equals(s)){
						inBoxSelected = true;
						 fetchGridData();
					}else{
						inBoxSelected = false;
						 fetchGridData1();
					}
					
				}
			});
	        
		}
		
		public void addRecordClickHandler(final ItemDetailTabPane itemDetailTabPane){
	        hmList.addRecordClickHandler(new RecordClickHandler() {
	            public void onRecordClick(RecordClickEvent event) {
	                itemDetailTabPane.updateDetails();
	            }
	        });	
	        hmrList.addRecordClickHandler(new RecordClickHandler() {
	            public void onRecordClick(RecordClickEvent event) {
	                itemDetailTabPane.updateDetails();
	                Record selectedRecord = event.getRecord();
	                if(HmessageStatus.UNREAD.toString().equals(selectedRecord.getAttributeAsString(HmessageReceiverField.STATUS.getEname()))){
	                	selectedRecord.setAttribute(HmessageReceiverField.STATUS.getEname(), HmessageStatus.READED.toString());
	                	hmrList.updateData(selectedRecord);
	                }
	            }
	        });	
		}
		
		public Record getSelectedRecord(){
			if(inBoxSelected){
				return hmrList.getSelectedRecord();
			}else{
				return hmList.getSelectedRecord();
			}
		}
		
		public void fetchGridData() {
		    Criteria c = new Criteria();
		    c.addCriteria(SmartParameterName.MODEL_NAME,HmessageReceiverDataSource.className);
		    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.NAMED_QUERY.toString());
		    c.addCriteria(SmartParameterName.NAMED_QUERY_NAME,"findMyHmrByStatuses");
		    c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		    hmrList.filterData(c);
		}
		
		public void fetchGridData1() {
		    Criteria c = new Criteria();
		    c.addCriteria(SmartParameterName.MODEL_NAME,HmessageDataSource.className);
		    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE,SmartSubOperationName.NAMED_QUERY.toString());
		    c.addCriteria(SmartParameterName.NAMED_QUERY_NAME,"findMyHmByStatus");
		    c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		    hmList.filterData(c);
		}
		
		
		public void refreshInbox(){
			hmrList.invalidateCache();
		}

	}
	
	public static class ItemDetailTabPane extends TabSet {

	    private DetailViewer itemViewer;
	    private DynamicForm editorForm;

	    private HmListTabPane htp;
	    
	    private HmessageReceiverDataSource hmrDS;
	    
	    private HmessageDataSource hmDs;

	    public ItemDetailTabPane(final AppUiService auiService,HmessageReceiverDataSource hmrDS,HmessageDataSource hmDs, HmListTabPane htp) {
	        this.htp = htp;
	        this.hmrDS = hmrDS;
	        this.hmDs = hmDs;
	        itemViewer = new DetailViewer();
	        itemViewer.setDataSource(hmrDS);
	        itemViewer.setWidth100();
	        itemViewer.setMargin(25);
	        itemViewer.setEmptyMessage("选择消息之后，这里会显示消息的详细内容！");

	        
	        editorForm = new DynamicForm();
	        editorForm.setWidth(650);
	        editorForm.setMargin(25);
	        editorForm.setNumCols(4);
	        editorForm.setCellPadding(5);
	        editorForm.setAutoFocus(false);
	        editorForm.setDataSource(hmDs);
	        
	        TextItem sendToItem  = new TextItem(HmessageField.RECEIVER_IDENTITY.getEname(), HmessageField.RECEIVER_IDENTITY.getCname());
	        sendToItem.setRequired(true);
	        
	        TextItem titleItem  = new TextItem(HmessageField.TITLE.getEname(), HmessageField.TITLE.getCname());
	        titleItem.setRequired(true);
	        TextAreaItem msgItem = new TextAreaItem(HmessageField.MESSAGE.getEname(),HmessageField.MESSAGE.getCname());
	        msgItem.setWidth(300);
	        msgItem.setRowSpan(3);
	        msgItem.setRequired(true);

	        ButtonItem saveButton = new ButtonItem("sendItem", "发送");
	        saveButton.setAlign(Alignment.CENTER);
	        saveButton.setWidth(100);
	        saveButton.setColSpan(4);
	        saveButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(
						com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
					if(editorForm.validate(false)){
						editorForm.saveData(new MyDsCallback() {
							@Override
							public void afterSuccess(DSResponse response, Object rawData,
									DSRequest request) {
								editorForm.clearValues();
								auiService.showTmpPrompt("已发送！");
							}
						});
					}
				}
			});

	        editorForm.setFields(sendToItem,titleItem, msgItem,saveButton);
	        editorForm.setColWidths(100, 200, 100, 200);


	        Tab viewTab = new Tab("消息内容");
	        viewTab.setIcon("silk/application_form.png");
	        viewTab.setWidth(70);
	        viewTab.setPane(itemViewer);

	        Tab editTab = new Tab("发送消息");
	        editTab.setIcon("silk/icon_edit.png");
	        editTab.setWidth(70);
	        editTab.setPane(editorForm);

	        setTabs(viewTab, editTab);
	    }

		public void clearDetails(Record selectedCategoryRecord) {
	        int selectedTab = getSelectedTabNumber();
	        if (selectedTab == 0) {
	            //view tab : show empty message
	            itemViewer.setData((Record[]) null);
	        } else {
	            // edit tab : show new record editor, or empty message
	            if (selectedCategoryRecord != null) {
//	                updateTab(1, editorForm);
//	                Map initialValues = new HashMap();
//	                initialValues.put("category", selectedCategoryRecord.getAttribute("categoryName"));
//	                editorForm.editNewRecord(initialValues);
	            } else {
//	                updateTab(1, editorLabel);
	            }
	        }
	    }

	    public void updateDetails() {
	        Record selectedRecord  = htp.getSelectedRecord();
	        int selectedTab = getSelectedTabNumber();
	        if (selectedTab == 0) {
	            //view tab : show empty message
	            itemViewer.setData(new Record[]{selectedRecord});
	        } else {
	            // edit tab : show record editor
//	            editorForm.editRecord(selectedRecord);
	        }
	    }
	}


	
    private SearchForm searchForm;

    private ItemDetailTabPane itemDetailTabPane;
    
    private HmListTabPane htp;
	
	@Inject
	private HmessageReceiverDataSource hmrds;
	
	
	@Inject
	private HmessageDataSource hds;
	
	public void refreshInbox(){
		if(htp!=null)
			htp.refreshInbox();
	}
	
	
	private Canvas initMe(){
        
        searchForm = new SearchForm();
        searchForm.setDataSource(hmrds);

        searchForm.setTop(20);
        searchForm.setCellPadding(6);
        searchForm.setNumCols(7);
        searchForm.setStyleName("defaultBorder");

        ButtonItem findItem = new ButtonItem("Find");
        findItem.setIcon("silk/find.png");
        findItem.setWidth(70);
        findItem.setEndRow(false);

        TextItem titleItem = new TextItem(HmessageReceiverField.TITLE.getEname(),HmessageReceiverField.TITLE.getCname());
        
        SelectItem statusItem = new SelectItem(HmessageReceiverField.STATUS.getEname(),HmessageReceiverField.STATUS.getCname());
        statusItem.setValueMap(HmessageStatus.UNREAD.toString(),HmessageStatus.READED.toString(),HmessageStatus.TRASHED.toString());


        searchForm.setItems(findItem, titleItem,statusItem);
        
        findItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				findItems(null);
			}
		});
        

//        public ComboBoxItem getItemNameField() {
//            return itemName;
//        }
//
//        public void addFindListener(ClickHandler handler) {
//            findItem.addClickHandler(handler);
//        }
        
        htp = new HmListTabPane(bservice,auiService, hmrds, hds);
        
        
        SectionStackSection findSection = new SectionStackSection("查找消息");
        findSection.setItems(searchForm);
        findSection.setExpanded(true);

        SectionStackSection supplyItemsSection = new SectionStackSection("消息列表");
        supplyItemsSection.setItems(htp);
        supplyItemsSection.setExpanded(true);

        itemDetailTabPane = new ItemDetailTabPane(auiService,hmrds,hds, htp);
        
        htp.addRecordClickHandler(itemDetailTabPane);
        
        SectionStackSection itemDetailsSection = new SectionStackSection("消息内容");
        itemDetailsSection.setItems(itemDetailTabPane);
        itemDetailsSection.setExpanded(true);
        
		
        SectionStack rightSideLayout = new SectionStack();
        rightSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);
        rightSideLayout.setAnimateSections(true);
        
        rightSideLayout.setSections(findSection, supplyItemsSection, itemDetailsSection);
        
        return rightSideLayout;
	}


	@Override
	protected void initGrid() {

	}
	
	   public void findItems(String categoryName) {
//	        Criteria findValues;
//	        String formValue = searchForm.getValueAsString("findInCategory");
//	        ListGridRecord selectedCategory = categoryTree.getSelectedRecord();
//	        if (formValue != null && selectedCategory != null) {
//	            categoryName = selectedCategory.getAttribute("categoryName");
//	            findValues = searchForm.getValuesAsCriteria();
//	            findValues.addCriteria("category", categoryName);
//
//	        } else if (categoryName == null) {
//	            findValues = searchForm.getValuesAsCriteria();
//	        } else {
//	            findValues = new Criteria();
//	            findValues.addCriteria("category", categoryName);
//	        }
//
//	        itemList.filterData(findValues);
//	        itemDetailTabPane.clearDetails(categoryTree.getSelectedRecord());
	    }

	
	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(!initialized && !firstCall){
			addMember(initMe());
			initialized = true;
			firstCall = true;
		}
		switch (va) {
		case REMOVE:
			grid.removeSelectedData();
			break;
		case SHOW_ALL:
			fetchAll();
			break;
		default:
			fetchGridData();
			break;
		}
	}
	

	
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.HMESSAGE_RECEIVE;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}
	
	@Override
	public Btname[] getStripStatus() {
		return new Btname[]{};
	}
	
	
	@Override
	public void fetchGridData() {
	}

}
