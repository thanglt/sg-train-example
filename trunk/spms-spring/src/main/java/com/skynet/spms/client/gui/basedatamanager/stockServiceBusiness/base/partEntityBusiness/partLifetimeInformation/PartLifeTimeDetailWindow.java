package com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.base.partEntityBusiness.partLifetimeInformation;

import com.skynet.spms.client.gui.base.BtnsHLayout;
import com.skynet.spms.client.gui.base.SetWindow;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;

public class PartLifeTimeDetailWindow extends Window {

	/**
	 * @param windowTitle
	 * @param isMax
	 * @param rect
	 * @param listGrid
	 * @param iconUrl
	 */
	public PartLifeTimeDetailWindow(String windowTitle, 
											boolean isMax,
											final Rectangle rect, 
											final ListGrid listGrid, 
											String iconUrl, 
											Boolean updateFlg) {

		final Window objWindow = this;
		setWidth(400);
		setHeight(310);
		
		final DynamicForm mainForm = new DynamicForm();
		mainForm.setWidth(200);
		mainForm.setHeight(300);
		mainForm.setDataSource(listGrid.getDataSource());
		if (updateFlg == true)
		{
			final Record record = listGrid.getSelectedRecord();
			mainForm.editRecord(record);
		}

		// 件号
		final TextItem txtPartNumber = new TextItem("partNumber");
		// 备件分类
		final TextItem txtSparePartClassCode = new TextItem("sparePartClassCode");
		// 检测周期
		final TextItem txtTimeControlTaskCycle = new TextItem("timeControlTaskCycle");
		// 检测代码
		final TextItem txtPeriodicCheckCode = new TextItem("periodicCheckCode");
		// 恢复方式
		final TextItem txtRenewalProcedureCode = new TextItem("renewalProcedureCode");
		// 注意代码
		final TextItem txtPayAttentionCode = new TextItem("payAttentionCode");
		// 制造商
		final TextItem txtManufacturerCode = new TextItem("manufacturerCode");
		// ATA
		final TextItem txtAta = new TextItem("ata");
		// 重要性
		final TextItem txtEssentialityCode = new TextItem("essentialityCode");
		
		final IButton saveButton = new IButton();
		saveButton.setTitle("保存");
		saveButton.setWidth(65);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainForm.saveData();
				mainForm.clearValues();
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

		mainForm.setFields(txtPartNumber
						,txtSparePartClassCode
						,txtTimeControlTaskCycle
						,txtPeriodicCheckCode
						,txtRenewalProcedureCode
						,txtPayAttentionCode
						,txtManufacturerCode
						,txtAta
						,txtEssentialityCode
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