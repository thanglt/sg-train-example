package com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.inStockRoomBusiness.inStockRecord;

import com.skynet.spms.client.gui.base.BtnsHLayout;
import com.skynet.spms.client.gui.base.CustomDateItem;
import com.skynet.spms.client.gui.base.SetWindow;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;

public class InStockRecordDetailWindow extends Window {

	public InStockRecordDetailWindow(String windowTitle, 
									boolean isMax,
									final Rectangle rect,
									ListGrid listGrid,
									String iconUrl,
									Boolean updateFlg) {
			final Window objWindow = this;
			setWidth(600);
			setHeight(500);
			
			final DynamicForm mainForm = new DynamicForm();
			mainForm.setWidth(400);
			mainForm.setNumCols(4);
			mainForm.setColWidths(120, 100, 80, 100);
			mainForm.setDataSource(listGrid.getDataSource());
					
		// 检验单号
		final TextItem CheckAndAcceptSheetNumber = new TextItem("checkAndAcceptSheetNumber");
		// 检验日期
		final CustomDateItem InspectionDate = new CustomDateItem("inspectionDate");
		// 检验员
		final TextItem Inspector = new TextItem("inspector");
		// 合同编号
		final TextItem ContratNumber = new TextItem("contratNumber");
		// 件号
		final TextItem PartNumber = new TextItem("partNumber");
		// 序号/批号
		final TextItem PartSerialNumber = new TextItem("partSerialNumber");
		// 数量
		final TextItem Quantity = new TextItem("quantity");
		// 供货单位
		final TextItem SupplyUnit = new TextItem("supplyUnit");
		// 状态
		final TextItem State = new TextItem("state");
		// 寿命件
		final TextItem IsLefttimePart = new TextItem("isLefttimePart");
		// 寿命期
		final TextItem UsefulLifePeriod = new TextItem("usefulLifePeriod");
		// 备件状况
		final TextItem PartStatusCode = new TextItem("partStatusCode");

		final IButton saveButton = new IButton();
		saveButton.setTitle("保存");
		saveButton.setWidth(65);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainForm.saveData();
				ShowWindowTools.showCloseWindow(rect, objWindow, -1);
			}
		});

		final IButton cancelButton = new IButton();
		cancelButton.setTitle("返回");
		cancelButton.setWidth(65);
		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ShowWindowTools.showCloseWindow(rect, objWindow, -1);
			}
		});

		mainForm.setFields(CheckAndAcceptSheetNumber
						, InspectionDate
						, Inspector
						, ContratNumber
						, PartNumber
						, PartSerialNumber
						, Quantity
						, SupplyUnit
						, State
						, IsLefttimePart
						, UsefulLifePeriod
						, PartStatusCode
					);

		final BtnsHLayout buttonLayout = new BtnsHLayout();
		buttonLayout.addMember(saveButton);
		buttonLayout.addMember(cancelButton);

		VLayout layout = new VLayout();
		layout.setMargin(5);
		layout.addMember(mainForm);
		layout.addMember(buttonLayout);

		SetWindow.SetWindowLayout(windowTitle
				,false
				,iconUrl
				,rect
				,objWindow
				,layout);
	}
}