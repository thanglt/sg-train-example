package com.skynet.spms.client.gui.supplierSupport.repairInspectClaimContractTemplate.add;

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
		
		ContractBaseForm baseform=new ContractBaseForm();
		baseTab.setPane(baseform);

		Tab addressTab = new Tab("地址信息");
		addressTab.setPane(new BaseAddressForm(BaseAddressForm.Type.modify));
		
		Tab provisionTab = new Tab("合同条款");
		provisionTab.setPane(new ContractProvisionPanel(baseform.form,TagEnum.RepairInspectClaimContractTemplate));

		Tab attachmentTab = new Tab("附件信息");
		attachmentTab.setPane(ContractBaseForm.attachmentCanvas);

		Tab inspectOutlayRecordTab = new Tab("检修费用");
		inspectOutlayRecordTab.setPane(new InspectOutlayRecordForm());
		inspectOutlayRecordTab.setDisabled(true);
		
		Tab repairQuoteRecordTab = new Tab("修理报价");
		repairQuoteRecordTab.setPane(new RepairQuoteRecordForm());
		repairQuoteRecordTab.setDisabled(true);

		setTabs(baseTab,addressTab,provisionTab,attachmentTab,inspectOutlayRecordTab,repairQuoteRecordTab);

	}

}
