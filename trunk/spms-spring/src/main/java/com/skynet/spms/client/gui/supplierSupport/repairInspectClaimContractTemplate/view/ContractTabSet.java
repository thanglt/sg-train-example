package com.skynet.spms.client.gui.supplierSupport.repairInspectClaimContractTemplate.view;

import com.skynet.spms.client.gui.contractManagement.tag.TagEnum;
import com.skynet.spms.client.gui.customerService.commonui.AttachmentCanvas;
import com.skynet.spms.client.gui.customerService.commonui.BaseAddressForm;
import com.skynet.spms.client.gui.customerService.commonui.BaseAddressModel;
import com.skynet.spms.client.gui.customerService.commonui.ContractProvisionPanel;
import com.skynet.spms.client.gui.supplierSupport.repairInspectClaimContractTemplate.model.ModelLocator;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class ContractTabSet extends TabSet {

	private ModelLocator model = ModelLocator.getInstance();

	public ContractTabSet() {
		_init();
	}

	@SuppressWarnings("static-access")
	public void _init() {
		Tab baseTab = new Tab("合同基本信息");
		ContractBaseForm baseForm=new ContractBaseForm();
		baseTab.setPane(baseForm);

		BaseAddressModel.getInstance().addr_sheetId = model.viewDetailContractID;
		Tab addressTab = new Tab("地址信息");
		addressTab.setPane(new BaseAddressForm(BaseAddressForm.Type.view));

		Tab provisionTab = new Tab("合同条款");
		provisionTab.setPane(new ContractProvisionPanel(baseForm.form,TagEnum.RepairInspectClaimContractTemplate));

		Tab attachmentTab = new Tab("附件信息");
		attachmentTab.setPane(new AttachmentCanvas(model.viewDetailContractID));

		Tab inspectOutlayRecordTab = new Tab("检修费用");
		inspectOutlayRecordTab.setPane(new InspectOutlayRecordForm());

		Tab repairQuoteRecordTab = new Tab("修理报价");
		repairQuoteRecordTab.setPane(new RepairQuoteRecordForm());

		setTabs(baseTab, addressTab, provisionTab, attachmentTab,
				inspectOutlayRecordTab, repairQuoteRecordTab);

	}

}
