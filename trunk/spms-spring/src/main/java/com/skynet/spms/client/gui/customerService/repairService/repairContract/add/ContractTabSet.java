package com.skynet.spms.client.gui.customerService.repairService.repairContract.add;

import com.skynet.spms.client.gui.contractManagement.tag.TagEnum;
import com.skynet.spms.client.gui.customerService.commonui.BaseAddressForm;
import com.skynet.spms.client.gui.customerService.commonui.ContractProvisionPanel;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class ContractTabSet extends TabSet {

	public ContractTabSet() {
		_init();
	}

	@SuppressWarnings("static-access")
	public void _init() {
		Tab baseTab = new Tab("合同基本信息");
		
		ContractBaseForm baseForm=new ContractBaseForm();
		baseTab.setPane(baseForm);

		Tab addressTab = new Tab("地址信息");
		addressTab.setPane(new BaseAddressForm(BaseAddressForm.Type.modify));

		Tab provisionTab = new Tab("合同条款");
		provisionTab.setPane(new ContractProvisionPanel(baseForm.form, TagEnum.RepairInspectClaimContractTemplate));

		Tab attachmentTab = new Tab("附件信息");
		attachmentTab.setPane(ContractBaseForm.attachmentCanvas);

		setTabs(baseTab, addressTab, provisionTab, attachmentTab);

	}

}
