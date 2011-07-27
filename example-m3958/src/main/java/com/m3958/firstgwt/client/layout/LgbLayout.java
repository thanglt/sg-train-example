package com.m3958.firstgwt.client.layout;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.Anchor;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.ComponentPreferenceDataSource;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.gin.MyEventBus;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.StringUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupNode;
import com.smartgwt.client.widgets.grid.GroupTitleRenderer;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

@Singleton
public class LgbLayout extends HasMMRelationGridLayout implements Iview,IlgbLayout{

	private LgbSearchLayout lgbSearchLayout;
	
	private final DynamicForm quickSearchForm = new DynamicForm();
	
	final SelectItem preferenceSelectItem = new SelectItem("name");
	
	private IButton qb = new IButton("查找");
	private IButton resetButton = new IButton("显示全部");
	private IButton superButton = new IButton("超级查询");
	private IButton exportButton = new IButton("导出Excel");
	private IButton pdfButton = new IButton("导出pdf");
	
	private VLayout contentlayout;
	
	
	private void disableButtons(){
		qb.setDisabled(true);
		resetButton.setDisabled(true);
		superButton.setDisabled(true);
		exportButton.setDisabled(true);
		pdfButton.setDisabled(true);
	}
	
	private void enableButtons(){
		qb.setDisabled(false);
		resetButton.setDisabled(false);
		superButton.setDisabled(false);
		exportButton.setDisabled(false);
		pdfButton.setDisabled(false);
	}

	public DynamicForm getQuickSearchForm() {
		return quickSearchForm;
	}
	
	private LgbDataSource lds;
	private ComponentPreferenceDataSource cpds;

	@Inject
	public LgbLayout(MyEventBus eventBus,LgbSearchLayout lgbSearchLayout,LgbDataSource lds,ComponentPreferenceDataSource cpds){
		this.lds = lds;
		this.cpds = cpds;
		this.eventBus = eventBus;
		this.lgbSearchLayout = (LgbSearchLayout)lgbSearchLayout;
		this.lgbSearchLayout.setLgbLayout(this);
		this.lgbSearchLayout.hide();
		setWidth100();
		setHeight100();
	}
	

	private Layout initContent(){
		contentlayout = new VLayout(3);
		contentlayout.setWidth100();
		contentlayout.setHeight100();
		contentlayout.addMember(initCfilter());
		contentlayout.addMember(lgbSearchLayout);
		contentlayout.addMember(initLgbGridLayout());
	    return contentlayout;
	}


	

	private HLayout initCfilter() {
		HLayout layout = new HLayout(10);
		layout.setHeight(15);
		layout.setWidth100();
		
		HLayout h1 = new HLayout(5);
		quickSearchForm.setNumCols(2);
		quickSearchForm.setWidth(160);
		TextItem filterItem = new TextItem("cvalue","按姓名快速查找");
		filterItem.setWidth(60);
		filterItem.addKeyPressHandler(new com.smartgwt.client.widgets.form.fields.events.KeyPressHandler(){
			@Override
			public void onKeyPress(
					com.smartgwt.client.widgets.form.fields.events.KeyPressEvent event) {
				if(KeyNames.ENTER.equals(event.getKeyName())){
					fetchNameLike();
				}
				
			}});
		
		
		qb.setWidth(35);
		qb.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				fetchNameLike();
			}});
		
		quickSearchForm.setFields(filterItem);
		h1.addMember(quickSearchForm);
		h1.addMember(qb);
		
		
		resetButton.setWidth(65);
		resetButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				showAllRecords();
			}});
		h1.addMember(resetButton);
		
		layout.addMember(h1);
		
		superButton.setWidth(65);
		superButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(lgbSearchLayout.isVisible()){
					lgbSearchLayout.hide();
				}else{
					lgbSearchLayout.show();
				}
			}});
		layout.addMember(superButton);

		
		
		exportButton.setWidth(65);
		exportButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				showExportWindow();
			}});
		layout.addMember(exportButton);
		
		
		pdfButton.setWidth(65);
		pdfButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				pv.showMe(grid);
			}});
		layout.addMember(pdfButton);		
		return layout;
	}
	
	
	@Inject
	private LgbPrintPdfWindow pv;
	
	@SuppressWarnings("unchecked")
	private String getExportUrl(){
		String url = StringUtils.addUrlParams("",grid.getCriteria().getValues());
		Map<String, String> params = new HashMap<String, String>();
		params.put(SmartParameterName.OPERATION_TYPE, SmartOperationName.RPCM_REQ.toString());
		params.put(SmartParameterName.MODEL_NAME, LgbDataSource.className);
		params.put(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.EXPORT_LGB_TO_EXCEL.toString());
		params.put(SmartParameterName.TEXT_MATCHSTYLE, grid.getAutoFetchTextMatchStyle().getValue());
		url = StringUtils.addUrlParams(url,params);
		return url;
	}
	
	
	protected void showExportWindow() {
		Window epw = new Window();
		epw.setTitle("导出");
		epw.setWidth(300);
		epw.setHeight(150);
		epw.setIsModal(true);
		epw.setShowModalMask(true);
		epw.centerInPage();
		VLayout vl = new VLayout(5);
		vl.setWidth100();
		vl.setHeight100();
		vl.setTop(10);
		vl.setAlign(Alignment.CENTER);
		vl.setAlign(VerticalAlignment.CENTER);
		Anchor a = new Anchor("导出查询结果的excel",true, SmartConstants.SMART_CRUD_URL + getExportUrl(),"_blank");
		vl.addMember(a);
		epw.addItem(vl);
		epw.show();
	}
	


	protected void showAllRecords() {
		quickSearchForm.setValue("cvalue", "");
//		getSw().getSearchForm().setValue(LgbField.XM.getEname(), "");
		Criteria oldc = grid.getCriteria();
		for(Object s : oldc.getValues().keySet()){
			if(!LgbField.DEPARTMENT_IDS.getEname().equals(s)){
				oldc.setAttribute(s.toString(), "");
			}
		}
		grid.filterData(oldc);
		
	}

	private void fetchNameLike(){
		Criteria oldc = grid.getCriteria();
		for(Object s : oldc.getValues().keySet()){
			if(!LgbField.DEPARTMENT_IDS.getEname().equals(s)){
				oldc.setAttribute(s.toString(), "");
			}
		}
//		getSw().getSearchForm().setValue(LgbField.XM.getEname(), quickSearchForm.getValueAsString("cvalue"));
		oldc.setAttribute(LgbField.XM.getEname(),quickSearchForm.getValueAsString("cvalue"));
		grid.filterData(oldc);	
	}

	

	private VLayout initLgbGridLayout() {
		VLayout layout = new VLayout(0);
		layout.setWidth100();
		layout.setHeight100();
		grid = new ListGrid();
		grid.setCanSelectAll(true);
		grid.setBooleanFalseImage(null);
		ToolStrip tsp = initTsp();
	    grid.setWidth100();
	    grid.setHeight100();
	    grid.setShowRowNumbers(true);
	    grid.setDataSource(lds);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    
	    grid.setDataPageSize(50);
	    grid.setInitialSort(ssf);
	    grid.setAutoFetchData(false);
	    
		ListGridField bzField = new ListGridField(LgbField.BZ.getEname(),LgbField.BZ.getCname());
		bzField.setHidden(true);
	    
		ListGridField jbmsField = new ListGridField(LgbField.JBMS.getEname(),LgbField.JBMS.getCname());
		jbmsField.setHidden(true);
		
		ListGridField xmField = new ListGridField(LgbField.XM.getEname(),LgbField.XM.getCname(),50);

		ListGridField xbField = new ListGridField(LgbField.XB.getEname(),LgbField.XB.getCname(),35);
		xbField.setCanFilter(false);
		ListGridField srField = new ListGridField(LgbField.SR.getEname(),LgbField.SR.getAlias(),30);
		srField.setCellFormatter(new CellFormatter(){
			@SuppressWarnings("deprecation")
			@Override
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				if(value != null){
					Date dateValue = (Date) value;
					int y = new Date().getYear() - dateValue.getYear() + 1;
					return String.valueOf(y);
				}else{
					return "";
				}
				
			}});
		srField.setCanFilter(false);
		ListGridField sfzField = new ListGridField(LgbField.SFZ.getEname(),LgbField.SFZ.getCname());
		sfzField.setHidden(true);
		ListGridField minzuField = new ListGridField(LgbField.MINZU.getEname(),LgbField.MINZU.getCname(),40);
		minzuField.setCanFilter(false);
		ListGridField jgField = new ListGridField(LgbField.JG.getEname(),LgbField.JG.getCname(),40);
		ListGridField csdField = new ListGridField(LgbField.CSD.getEname(),LgbField.CSD.getCname());
		csdField.setHidden(true);
		ListGridField jyField = new ListGridField(LgbField.JY.getEname(),LgbField.JY.getCname(),60);
		jyField.setCanFilter(false);
		ListGridField rdsjField = new ListGridField(LgbField.RDSJ.getEname(),LgbField.RDSJ.getCname());
		rdsjField.setHidden(true);
		rdsjField.setCanFilter(false);
		rdsjField.setType(ListGridFieldType.DATE);
		rdsjField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		ListGridField rwsjField = new ListGridField(LgbField.RWSJ.getEname(),LgbField.RWSJ.getCname());
		rwsjField.setCanFilter(false);
		rwsjField.setHidden(true);
		rwsjField.setType(ListGridFieldType.DATE);
		rwsjField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		ListGridField ltxsjField = new ListGridField(LgbField.LTXSJ.getEname(),LgbField.LTXSJ.getCname());
		ltxsjField.setHidden(true);
		ltxsjField.setCanFilter(false);
		ltxsjField.setType(ListGridFieldType.DATE);
		ltxsjField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		
		ListGridField ygzdwField = new ListGridField(LgbField.YGZDW.getEname(),LgbField.YGZDW.getCname());
		ListGridField yzwField = new ListGridField(LgbField.YZW.getEname(),LgbField.YZW.getCname(),60);
		ListGridField xzgdwField = new ListGridField(LgbField.XZGDW.getEname(),LgbField.XZGDW.getCname());
		xzgdwField.setHidden(true);
		ListGridField dwxzField = new ListGridField(LgbField.DWXZ.getEname(),LgbField.DWXZ.getCname(),60);
		dwxzField.setCanFilter(false);
		ListGridField xzjbField = new ListGridField(LgbField.XZJB.getEname(),LgbField.XZJB.getCname(),50);
		xzjbField.setCanFilter(false);
		ListGridField sszbField = new ListGridField(LgbField.SSZB.getEname(),LgbField.SSZB.getCname(),50);
		sszbField.setCanFilter(false);
		ListGridField xsdyField = new ListGridField(LgbField.XSDY.getEname(),LgbField.XSDY.getCname(),60);
		xsdyField.setCanFilter(false);
		xsdyField.setHidden(true);
		ListGridField gblxField = new ListGridField(LgbField.GBLX.getEname(),LgbField.GBLX.getCname(),60);
		gblxField.setCanFilter(false);
		gblxField.setHidden(true);
		ListGridField jkzkField = new ListGridField(LgbField.JKZK.getEname(),LgbField.JKZK.getCname(),60);
		jkzkField.setCanFilter(false);
		ListGridField hyzkField = new ListGridField(LgbField.HYZK.getEname(),LgbField.HYZK.getCname(),40);
		hyzkField.setCanFilter(false);
		ListGridField jjzkField = new ListGridField(LgbField.JJZK.getEname(),LgbField.JJZK.getCname(),50);
		jjzkField.setHidden(true);
		jjzkField.setCanFilter(false);
		ListGridField pogwField = new ListGridField(LgbField.POGZ.getEname(),LgbField.POGZ.getCname());
		pogwField.setHidden(true);
		ListGridField hjszdField = new ListGridField(LgbField.HJSZD.getEname(),LgbField.HJSZD.getCname());
		hjszdField.setHidden(true);
		ListGridField swsjField = new ListGridField(LgbField.SWSJ.getEname(),LgbField.SWSJ.getCname());
		swsjField.setHidden(true);
		ListGridField passAwayField = new ListGridField(LgbField.PASSAWAY.getEname(),LgbField.PASSAWAY.getCname(),25);
		passAwayField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				if((Boolean) value){
					return "已故";
				}
				return "";
			}
		});
		passAwayField.setCanFilter(false);
		
		ListGridField paixuField = new ListGridField(LgbField.PAIXU.getEname(),LgbField.PAIXU.getCname());
	    
		ListGridField shequIdField = new ListGridField(LgbField.SHEQU_ID.getEname());
		shequIdField.setHidden(true);
		shequIdField.setCanFilter(false);
		
		grid.addSelectionChangedHandler(getSelectionChangeHandler());
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),xmField,xbField,srField,sfzField,minzuField,jgField,csdField,jyField,rdsjField,rwsjField,ltxsjField,
        		xzgdwField,dwxzField,xzjbField,sszbField,xsdyField,gblxField,jkzkField,hyzkField,jjzkField,pogwField,hjszdField,swsjField,bzField,passAwayField,
        		auiService.getCreatedAtField(false),ygzdwField,yzwField,paixuField,shequIdField,jbmsField);
	    layout.addMember(tsp);
	    layout.addMember(grid);
	    return layout;
	}
	
	class Mygvfunction implements GroupValueFunction{

		@Override
		public Object getGroupValue(Object value, ListGridRecord record,
				ListGridField field, String fieldName, ListGrid grid) {
			if(fieldName.equals(LgbField.XB.getEname())){
				if("男".equals(value)){
					return 1;
				}else if("女".equals(value)){
					return 0;
				}else{
					return -1;
				}
			}
			
			return value;
		}
		
	}
	
	class Mygtfunction implements GroupTitleRenderer{

		@Override
		public String getGroupTitle(Object groupValue, GroupNode groupNode,
				ListGridField field, String fieldName, ListGrid grid) {
			if(fieldName.equals(LgbField.PASSAWAY.getEname())){
				if((Boolean)groupValue){
					return "已故";
				}else{
					return "健在";
				}
			}
			return groupValue.toString();
		}
		
	}
	
	public String getRelationModelId(){
		return relationModelId;
	}
	
	@Override
	public void fetchGridData(){
	    Criteria c = new Criteria();
	    c.addCriteria(LgbField.DEPARTMENT_IDS.getEname(),"," + relationModelId + ",");
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
	    grid.filterData(c);
	}


	private ToolStrip initTsp() {
		ToolStrip gridToolStrip = new ToolStrip();
        ToolStripButton formulaButton = new ToolStripButton("构造方程式", "crystal/oo/sc_insertformula.png");
        formulaButton.setAutoFit(true);
        formulaButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                grid.addFormulaField();
            }
        });

        ToolStripButton summaryBuilder = new ToolStripButton("构建摘要字段", "crystal/16/apps/tooloptions.png");
        summaryBuilder.setAutoFit(true);
        summaryBuilder.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                grid.addSummaryField();
            }
        });

        
        preferenceSelectItem.setTitle("");
        final ListGrid pickListProperties = new ListGrid();
        
        pickListProperties.setEmptyMessage("没有视图");
        preferenceSelectItem.setPickListProperties(pickListProperties);
        preferenceSelectItem.setOptionDataSource(cpds);
        preferenceSelectItem.setOptionCriteria(new Criteria(SmartParameterName.FETCH_OWNER, "true"));
        preferenceSelectItem.setDefaultToFirstOption(true);
       
        //apply the selected preference from the SelectItem
        preferenceSelectItem.addChangedHandler(new ChangedHandler() {
            @Override
            public void onChanged(ChangedEvent event) {
                Record r = preferenceSelectItem.getSelectedRecord();
                grid.setViewState(r.getAttribute("viewState"));
//                appHandler.setLastPreference(r.getAttributeAsInt("id"));
            }
        });


        ToolStripButton savePreference = new ToolStripButton("保存视图", "silk/database_gear.png");
        savePreference.setAutoFit(true);
        savePreference.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                SC.askforValue("请输入视图名称 :", new ValueCallback() {
                    @Override
                    public void execute(String value) {
                        if (value != null && !value.equals("")) {
                            String viewState = grid.getViewState();
                            PreferenceRecord record = new PreferenceRecord(0, value, viewState,LgbDataSource.className,true);
                            cpds.addData(record);
                            preferenceSelectItem.setValue(value);
                        }
                    }
                });
            }
        });
        
        ToolStripButton deletePreference  = new ToolStripButton("删除当前视图");
        deletePreference.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Record r = preferenceSelectItem.getSelectedRecord();
				cpds.removeData(r);
			}});
        gridToolStrip.setWidth100();
        gridToolStrip.addSpacer(20);
        gridToolStrip.addFill();
        gridToolStrip.addButton(formulaButton);
        gridToolStrip.addButton(summaryBuilder);
        gridToolStrip.addSeparator();
        gridToolStrip.addButton(savePreference);
        gridToolStrip.addSeparator();
        gridToolStrip.addFormItem(preferenceSelectItem);
        gridToolStrip.addButton(deletePreference);
        return gridToolStrip;
	}
	

    /**
     * Record class for capturing grid preference
     */
    class PreferenceRecord extends ListGridRecord {
        PreferenceRecord(int pk, String name, String viewState,String modelName,boolean lastRequest) {
            setPk(pk);
            setName(name);
            setViewState(viewState);
            setModelName(modelName);
            setLastRequest(lastRequest);
        }
        
        public void setLastRequest(boolean lastRequest){
        	setAttribute("lastRequest", lastRequest);
        }
        
        public boolean getLastRequest(){
        	return getAttributeAsBoolean("lastRequest");
        }

        public void setModelName(String modelName) {
			setAttribute("modelName", modelName);
		}
        
        public String getModelName(){
        	return getAttribute("modelName");
        }

		public int getPk() {
            return getAttributeAsInt("pk");
        }

        public void setPk(int pk) {
            setAttribute("pk", pk);
        }

        public String getName() {
            return getAttribute("name");
        }

        public void setName(String name) {
            setAttribute("name", name);
        }

        public String getViewState() {
            return getAttribute("viewState");
        }

        public void setViewState(String viewState) {
            setAttribute("viewState", viewState);
        }
    }
    
	@Override
	public void setUnInitialized() {
		if(contentlayout != null){
			removeMember(contentlayout);
			contentlayout = null;
			relationModelId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
		}
		initialized = false;
	}

	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		if(!initialized){
			addMember(initContent());
			initialized = true;
		}
		switch (va) {
		case REMOVE:
			grid.removeSelectedData();
			break;
		case CONTAINER_CLICKED:
			if(relationModelId == null || !relationModelId.equals(auService.getStringIdPara(paras, 0))){
				relationModelId = auService.getStringIdPara(paras, 0);
				fetchGridData();
			}
			enableButtons();
			break;
		default:
			disableButtons();
			break;
		}
		lgbSearchLayout.hide();
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.LGB;
	}


	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		switch (btname) {
		case EDIT:
			return ViewNameEnum.LGB_EDIT;
		case ADD:
			return ViewNameEnum.LGB_EDIT;
		default:
			break;
		}
		return null;
	}


	@Override
	public String[] getParas() {
		return new String[]{relationModelId,selectedRecordId};
	}
	
	public ListGrid asGrid(){
		return grid;
	}
	
	@Override
	public Btname[] getStripStatus() {
		if(grid == null)return simpleBts0;
		ListGridRecord[] rs = grid.getSelection();
		if(rs.length == 0){
			return simpleBts0;
		}else if(rs.length == 1){
			return simpleBts1;
		}else{
			return simpleBts1;
		}
	}


	@Override
	protected void initGrid() {
	}

	@Override
	protected void leaveRelation() {
	}

	@Override
	public void fetchGroups() {
		
	}

	@Override
	public void fetchMine() {
		
	}

	@Override
	public void fetchOthers() {
		
	}

	@Override
	public void fetchAll() {
		
	}

	@Override
	public String getModelName() {
		return LgbDataSource.className;
	}

	@Override
	public String getRelationModelName() {
		return null;
	}

	@Override
	public String getMasterSideProperty() {
		return null;
	}


	@Override
	public boolean isMaster() {
		return false;
	}

	@Override
	public String getRelationHint() {
		return null;
	}

}
