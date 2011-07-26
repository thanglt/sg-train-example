package com.skynet.spms.client.gui.customerService.repairService.sheet.view;

import com.skynet.spms.client.gui.customerService.commonui.BaseAddressForm;
import com.skynet.spms.client.gui.customerService.commonui.BaseAddressModel;
import com.skynet.spms.client.gui.customerService.repairService.sheet.SheetModelLocator;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class TabSetPanelView extends TabSet {
	
	private SheetModelLocator model = SheetModelLocator.getInstance();

	public TabSetPanelView() {
		_init();
	}

	public void _init() {
		Tab baseTab = new Tab("申请单基本信息");
		baseTab.setPane(new SheetViewForm());
		
		Tab partRqDataTab = new Tab("部件拆换信息");
		partRqDataTab.setPane(new PartRepairDisassembleDataViewForm());
		
		BaseAddressModel.getInstance().addr_sheetId=model.repairRequisitionListGrid.getSelectedRecord().getAttribute("id");
		Tab addressTab = new Tab("地址信息");
		addressTab.setPane(new BaseAddressForm(BaseAddressForm.Type.view));
		
		setTabs(baseTab,partRqDataTab,addressTab);
	}

}
