package com.skynet.spms.client.gui.supplierSupport.repairInspectClaimContractTemplate.update;

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

		BaseAddressModel.getInstance().addr_sheetId = model.contractID;
		Tab addressTab = new Tab("地址信息");
		addressTab.setPane(new BaseAddressForm(BaseAddressForm.Type.modify));

		Tab provisionTab = new Tab("合同条款");
		provisionTab.setPane(new ContractProvisionPanel(baseForm.form,TagEnum.RepairInspectClaimContractTemplate));

		Tab attachmentTab = new Tab("附件信息");
		attachmentTab.setPane(new AttachmentCanvas(model.contractID));

		Tab inspectOutlayRecordTab = new Tab("检修费用");
		InspectOutlayRecordForm inspectOutlayRecordForm=new InspectOutlayRecordForm();
		inspectOutlayRecordTab.setPane(inspectOutlayRecordForm);

		Tab repairQuoteRecordTab = new Tab("修理报价");
		RepairQuoteRecordForm repairQuoteRecordForm=new RepairQuoteRecordForm();
		repairQuoteRecordTab.setPane(repairQuoteRecordForm);
		
		//Account the amount.
		ContractAmountOper contractAmountOper=new ContractAmountOper();
		contractAmountOper.setInspectOutLayGrid(inspectOutlayRecordForm.itemDetailGrid);
		contractAmountOper.setRepairQuoteGrid(repairQuoteRecordForm.itemDetailGrid);
		inspectOutlayRecordForm.setContractAmountOper(contractAmountOper);
		repairQuoteRecordForm.setContractAmountOper(contractAmountOper);
		

		setTabs(baseTab, addressTab, provisionTab, attachmentTab,
				inspectOutlayRecordTab, repairQuoteRecordTab);

	}

}
