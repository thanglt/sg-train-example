package com.skynet.spms.client.gui.partcatalog.technicalCatalog.appliationData;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.skynet.spms.client.constants.ConstantsUtil;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
public class ApplicationDataPanel2 extends VLayout {
	
	private DynamicForm form;
	
	public ApplicationDataPanel2() {
    	
		/*DataSourceTool dataSourceTool = new DataSourceTool();
		dataSourceTool.onCreateDataSource("partCatalog.technical","applicationData_dataSource", new PostDataSourceInit() {
			@Override
			public void doPostOper(DataSource dataSource, DataInfo dataInfo) {
				initPanel(dataSource,dataInfo);
			}
		});*/
		initPanel();
	}
		
	//public void initPanel(DataSource dataSource,DataInfo dataInfo){
	public void initPanel(){
		form = new DynamicForm();
		//form.setDataSource(dataSource);
		
		form.setHeight(50);
		form.setWidth100();
		form.setPadding(5);
		form.setNumCols(8);
		form.setAlign(Alignment.LEFT);
		
		LinkedHashMap<String, String> boolValueMap = new LinkedHashMap<String, String>();
	    boolValueMap.put("true",ConstantsUtil.commonConstants.choiceYes());
	    boolValueMap.put("false",ConstantsUtil.commonConstants.choiceNo());
		
	    List<FormItem> itemList = new ArrayList<FormItem>();
		
		//主键ID
		HiddenItem hdnId = new HiddenItem("id");
		itemList.add(hdnId);
		
		//单机装机数量
		final TextItem tiTotalQuantityPerAircraftEngine = new TextItem("totalQuantityPerAircraftEngine","单机装机数量");
		tiTotalQuantityPerAircraftEngine.setWidth(120);
		itemList.add(tiTotalQuantityPerAircraftEngine);
	    
		//单位
		//SelectItem sltUnitOfMeasureCode = (SelectItem)dataInfo.getFieldInfoByName("m_UnitOfMeasureCode").generFormField(); 
		SelectItem sltUnitOfMeasureCode = new SelectItem("m_UnitOfMeasureCode","单位");
		sltUnitOfMeasureCode.setWidth(80);
		itemList.add(sltUnitOfMeasureCode);
		
		//快速转发
		RadioGroupItem rgiQuickEngineChangeIndicator = new RadioGroupItem("quickEngineChange","快速转发");
		rgiQuickEngineChangeIndicator.setValueMap(boolValueMap);
		rgiQuickEngineChangeIndicator.setVertical(false);
		rgiQuickEngineChangeIndicator.setColSpan(3);
		rgiQuickEngineChangeIndicator.setWidth(120);
	    itemList.add(rgiQuickEngineChangeIndicator);
		
	    //ETOPS
	    RadioGroupItem rgiETOPSFlightIndicator = new RadioGroupItem("m_ETOPSFlightIndicator","ETOPS");
	    rgiETOPSFlightIndicator.setValueMap(boolValueMap);
	    rgiETOPSFlightIndicator.setVertical(false);
	    rgiETOPSFlightIndicator.setColSpan(3);
	    rgiETOPSFlightIndicator.setWidth(120);
	    itemList.add(rgiETOPSFlightIndicator);
	    
	    //发动机维护代码
	    //SelectItem sltEngineLevelOfMaintenanceCode = (SelectItem)dataInfo.getFieldInfoByName("m_EngineLevelOfMaintenanceCode").generFormField(); 
	    SelectItem sltEngineLevelOfMaintenanceCode = new SelectItem("m_EngineLevelOfMaintenanceCode","发动机维护代码");
	    sltEngineLevelOfMaintenanceCode.setWidth(120);
		itemList.add(sltEngineLevelOfMaintenanceCode);
		
		//维修代码
		//SelectItem sltMaintenanceOverhaulRepairCode = (SelectItem)dataInfo.getFieldInfoByName("m_MaintenanceOverhaulRepairCode").generFormField(); 
	    SelectItem sltMaintenanceOverhaulRepairCode = new SelectItem("m_MaintenanceOverhaulRepairCode","维修代码");
		sltMaintenanceOverhaulRepairCode.setWidth(120);
		itemList.add(sltMaintenanceOverhaulRepairCode);
		
		//出口控制分类号码
		//SelectItem sltExportControlClassificationCode = (SelectItem)dataInfo.getFieldInfoByName("m_ExportControlClassificationCode").generFormField(); 
	    SelectItem sltExportControlClassificationCode = new SelectItem("m_ExportControlClassificationCode","出口控制分类号码");
		sltExportControlClassificationCode.setWidth(120);
		itemList.add(sltExportControlClassificationCode);
		
		
		 //航线维护备注说明
	    TextAreaItem textMiscellaneousText = new TextAreaItem("miscellaneousText","航线维护备注说明");
	    textMiscellaneousText.setWidth(300);  
	    textMiscellaneousText.setRowSpan(3);
	    itemList.add(textMiscellaneousText);
       	
	    FormItem[] items = new FormItem[itemList.size()];
        itemList.toArray(items); 
		form.setFields(items);
		form.setColWidths(120, 120,40,80, 300, "*"); 
        this.addMember(form);
	    
		
	    //按钮条
     	HLayout buttonPanel = new HLayout(10);
     	buttonPanel.setHeight(30);
         
 		IButton saveButton = new IButton(ConstantsUtil.buttonConstants.saveButton());
 		saveButton.setIcon("icons/save.png");
 		saveButton.addClickHandler(new ClickHandler() {	
 			@Override
 			public void onClick(ClickEvent event) {
 				form.saveData();	
 			}
 		});
		
 		IButton cancelButton = new IButton(ConstantsUtil.buttonConstants.cancelButton());
 		cancelButton.setIcon("icons/remove.png");
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				form.reset();
				
			}
		});
		
		IButton helpButton = new IButton();
	    helpButton.setIcon("book_help.png");
	    helpButton.setTitle(ConstantsUtil.buttonConstants.helpButton());
	    helpButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
			}
		});
	    buttonPanel.addMember(saveButton);
	    buttonPanel.addMember(cancelButton);
	    buttonPanel.addMember(helpButton);
		this.addMember(buttonPanel);
		
	}
	
	public void fetchData(String applicationDataId){
		Criteria criteria = new Criteria("id",applicationDataId);
		form.fetchData(criteria,new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Record selectedRecord = response.getData()[0];
				form.getItem("id").setValue(selectedRecord.getAttribute("id"));
				form.getItem("totalQuantityPerAircraftEngine").setValue(selectedRecord.getAttribute("totalQuantityPerAircraftEngine"));
				form.getItem("m_UnitOfMeasureCode").setValue(selectedRecord.getAttribute("m_UnitOfMeasureCode"));
				form.getItem("quickEngineChange").setValue(selectedRecord.getAttribute("quickEngineChange"));
				form.getItem("m_ETOPSFlightIndicator").setValue(selectedRecord.getAttribute("m_ETOPSFlightIndicator"));
				form.getItem("m_EngineLevelOfMaintenanceCode").setValue(selectedRecord.getAttribute("m_EngineLevelOfMaintenanceCode"));
				form.getItem("m_MaintenanceOverhaulRepairCode").setValue(selectedRecord.getAttribute("m_MaintenanceOverhaulRepairCode"));
				form.getItem("m_ExportControlClassificationCode").setValue(selectedRecord.getAttribute("m_ExportControlClassificationCode"));
				form.getItem("miscellaneousText").setValue(selectedRecord.getAttribute("miscellaneousText"));
				
			}
		});
	}
}
