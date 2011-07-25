package com.skynet.spms.client.gui.partcatalog.technicalCatalog.appliationData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.skynet.spms.client.constants.ButtonConstants;
import com.skynet.spms.client.constants.ConstantsUtil;
import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.gui.base.BaseWindow;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ApplicationDataAddWindow extends BaseWindow {

	public ApplicationDataAddWindow(String windowTitle, boolean isMax,
			Rectangle srcRect, ListGrid listGrid, String iconUrl) {
		super(windowTitle, isMax,srcRect, listGrid, iconUrl);
	}

	@Override
	protected Canvas getLeftLayout(final Rectangle srcRect, ListGrid listGrid) {
		ButtonConstants buttonConstants = GWT.create(ButtonConstants.class);
		
		ApplicationDataListGrid applicationDataListGrid=(ApplicationDataListGrid)listGrid;
		addCloseClickHandler(new CloseClickHandler() {
			public void onCloseClick(CloseClientEvent event) {
				ShowWindowTools.showCloseWindow(srcRect, ApplicationDataAddWindow.this, -1);
			}
		});
		
		final DynamicForm form = new DynamicForm();
	    form.setDataSource(applicationDataListGrid.getDataSource());
		form.setHeight100();
		form.setWidth100();
		form.setPadding(5);
		form.setMargin(10);
		form.setNumCols(4);  
		form.setLayoutAlign(VerticalAlignment.BOTTOM);
		form.enableHiliting(false);
		
		
		final List<FormItem> itemList = new ArrayList<FormItem>();
		//把所选中的indexInfo中的id传过来

		HiddenItem hdnPartIndexId = new HiddenItem("partIndexId");
		hdnPartIndexId.setValue(applicationDataListGrid.getPartIndexId());
		itemList.add(hdnPartIndexId);	

		TextItem tiRecommendedQuantity = new TextItem("recommendedQuantity");
		itemList.add(tiRecommendedQuantity);
		
		 RadioGroupItem rgiQuickEngineChangeIndicator = new RadioGroupItem("quickEngineChange"); 
		 LinkedHashMap<String, String> boolValueMap = new LinkedHashMap<String, String>();
		 boolValueMap.put("true",ConstantsUtil.commonConstants.choiceYes());
		 boolValueMap.put("false",ConstantsUtil.commonConstants.choiceNo());
		 rgiQuickEngineChangeIndicator.setValueMap(boolValueMap);
		 itemList.add(rgiQuickEngineChangeIndicator);
		 
		 DataInfo dataInfo = applicationDataListGrid.getDataInfo();
		 SelectItem sltETOPS = (SelectItem)dataInfo.getFieldInfoByName("m_ETOPSFlightIndicator").generFormField();
		 itemList.add(sltETOPS);
		 
		 TextAreaItem taiMiscellaneousText = new TextAreaItem("miscellaneousText");
		 taiMiscellaneousText.setWidth(300);  
		 taiMiscellaneousText.setRowSpan(3); 
		 itemList.add(taiMiscellaneousText);
		 
		 SelectItem siEngineLevelOfMaintenanceCode = (SelectItem)dataInfo.getFieldInfoByName("m_EngineLevelOfMaintenanceCode").generFormField();  
		 itemList.add(siEngineLevelOfMaintenanceCode);
		 
		 SelectItem siMaintenanceOverhaulRepairCode = (SelectItem)dataInfo.getFieldInfoByName("m_MaintenanceOverhaulRepairCode").generFormField();  
		 itemList.add(siMaintenanceOverhaulRepairCode);
		 
		 SelectItem siExportControlClsCode = (SelectItem)dataInfo.getFieldInfoByName("m_ExportControlClassificationCode").generFormField();  
		 itemList.add(siExportControlClsCode);
		 
        form.setColWidths(120, 300, 150, 300);  
        FormItem[] items = new FormItem[itemList.size()];
        itemList.toArray(items);
        form.setFields(items);
		
		this.addMember(form);
        
		HLayout buttonPanel = new HLayout(10);
		IButton saveButton = new IButton();
		saveButton.setIcon("icons/save.png");
	    saveButton.setTitle(buttonConstants.saveButton());
	    saveButton.setAlign(Alignment.CENTER);
	    
	    IButton cancelButton = new IButton(buttonConstants.cancelButton());
	    cancelButton.setIcon("icons/remove.png");
	    
	    IButton helpButton = new IButton();
	    helpButton.setIcon("icons/book_help.png");
	    helpButton.setTitle(ConstantsUtil.buttonConstants.helpButton());
	    helpButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
			}
		});
	    buttonPanel.addMember(saveButton);
	    buttonPanel.addMember(cancelButton);
	    buttonPanel.addMember(helpButton);
		
	    saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				form.saveData();
				ShowWindowTools.showCloseWindow(srcRect, ApplicationDataAddWindow.this, -1);
			}
		});

	    HLayout tileGrid = new HLayout();
		tileGrid.setWidth100();
		tileGrid.setHeight("90%");
		//tileGrid.setTileWidth(150);
		//tileGrid.setTileHeight(150);
		tileGrid.setBorder("0px solid #9C9C9C");
		tileGrid.addChild(form);
		
		HLayout buttonGrid = new HLayout();
		buttonGrid.setWidth100();
		buttonGrid.setHeight("10%");
		//buttonGrid.setTileWidth(100);
		//buttonGrid.setTileHeight(100);	
		buttonGrid.setBorder("0px solid #9C9C9C");
		saveButton.setTop("25%");
		buttonGrid.addChild(saveButton);

		
		VLayout vLayout = new VLayout();
		vLayout.addMember(tileGrid); 
		vLayout.addMember(buttonGrid);
		return vLayout;
	}
}
