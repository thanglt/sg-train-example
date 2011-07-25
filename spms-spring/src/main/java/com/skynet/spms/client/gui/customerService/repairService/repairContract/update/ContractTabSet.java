package com.skynet.spms.client.gui.customerService.repairService.repairContract.update;

import com.skynet.spms.client.gui.contractManagement.tag.TagEnum;
import com.skynet.spms.client.gui.customerService.commonui.BaseAddressForm;
import com.skynet.spms.client.gui.customerService.commonui.BaseAddressModel;
import com.skynet.spms.client.gui.customerService.commonui.ContractProvisionPanel;
import com.skynet.spms.client.gui.customerService.repairService.repairContract.ContractModelLocator;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class ContractTabSet extends TabSet {

	ContractModelLocator model = ContractModelLocator.getInstance();

	public ContractTabSet() {
		_init();
	}
	@SuppressWarnings("static-access")
	public void _init() {
		Tab baseTab = new Tab("合同基本信息");
		
		ContractBaseForm  baseForm=new ContractBaseForm();
		baseTab.setPane(baseForm);

		BaseAddressModel.getInstance().addr_sheetId = model.repairContractListGrid
				.getSelectedRecord().getAttribute("id");
		Tab addressTab = new Tab("地址信息");
		addressTab.setPane(new BaseAddressForm(BaseAddressForm.Type.modify));

		Tab provisionTab = new Tab("合同条款");
		provisionTab.setPane(new ContractProvisionPanel(baseForm.form,TagEnum.RepairInspectClaimContractTemplate));

		Tab attachmentTab = new Tab("附件信息");
		attachmentTab.setPane(new Attachment());

		Tab inspectOutLayRecordTab = new Tab("检验费用");
		InspectOutlayRecordForm inspectOutlayRecordForm=new InspectOutlayRecordForm();
		inspectOutLayRecordTab.setPane(inspectOutlayRecordForm);

		Tab repairQuoteReocrdTab = new Tab("修理记录");
		RepairQuoteRecordForm repairQuoteRecordForm=new RepairQuoteRecordForm();
		repairQuoteReocrdTab.setPane(repairQuoteRecordForm);
		
		//Account the amount.
		ContractAmountOper contractAmountOper=new ContractAmountOper();
		contractAmountOper.setInspectOutLayGrid(inspectOutlayRecordForm.itemDetailGrid);
		contractAmountOper.setRepairQuoteGrid(repairQuoteRecordForm.itemDetailGrid);
		inspectOutlayRecordForm.setContractAmountOper(contractAmountOper);
		repairQuoteRecordForm.setContractAmountOper(contractAmountOper);
		
		
		setTabs(baseTab, addressTab, provisionTab, attachmentTab,
				inspectOutLayRecordTab, repairQuoteReocrdTab);

	}

}
