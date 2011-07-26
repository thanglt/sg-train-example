package com.skynet.spms.client.gui.supplierSupport.repairInspectClaimContractTemplate.update;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 更新窗体
 * 
 * @author tqc
 * 
 */
public class RepairInsClaimContractTemplateUpdateWin extends Window {
	

	public RepairInsClaimContractTemplateUpdateWin() {
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
		setTitle("修改送修合同");
		VLayout container = new VLayout();
		container.setWidth100();
		container.setHeight100();
		container.setAlign(Alignment.CENTER);
		ContractTabSet tabSet=new ContractTabSet();
		container.addMember(tabSet);
		addItem(container);
	}
	
	

}
