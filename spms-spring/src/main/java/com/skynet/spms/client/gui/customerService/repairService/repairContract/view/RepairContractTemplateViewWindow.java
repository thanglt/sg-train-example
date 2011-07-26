package com.skynet.spms.client.gui.customerService.repairService.repairContract.view;

import com.skynet.spms.client.gui.customerService.repairService.repairContract.ContractModelLocator;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 送修合同查看窗体
 * 
 * @author tqc
 * 
 */
public class RepairContractTemplateViewWindow extends Window {
	
	public ContractModelLocator model = ContractModelLocator.getInstance();
	
	public RepairContractTemplateViewWindow() {
		_init();
	}

	private void _init() {
		setWidth100();
		setHeight100();
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();
		setAlign(Alignment.CENTER);
		setTitle("查看客户送修合同");
		VLayout container = new VLayout();
		container.setWidth100();
		container.setHeight100();
		container.setAlign(Alignment.CENTER);
		ContractTabSet tabSet=new ContractTabSet();
		container.addMember(tabSet);
		addItem(container);
	}
	
	

}
