package com.skynet.spms.client.gui.supplierSupport.leaseService.leaseContract;

import com.skynet.spms.client.gui.base.BaseButtonToolStript;
import com.skynet.spms.client.gui.base.Utils;
import com.skynet.spms.client.gui.base.ValidateUtil;
import com.skynet.spms.client.gui.base.WorkFlowBusinessType;
import com.skynet.spms.client.gui.base.WorkFlowSheetType;
import com.skynet.spms.client.gui.supplierSupport.common.DSKey;
import com.skynet.spms.client.gui.supplierSupport.leaseService.business.SSLeaseSheetBusiness;
import com.skynet.spms.client.gui.supplierSupport.leaseService.leaseContract.add.SSLeaseContractAddWindow;
import com.skynet.spms.client.gui.supplierSupport.leaseService.leaseContract.update.SSLeaseContractUpdateWindow;
import com.skynet.spms.client.gui.supplierSupport.leaseService.leaseContract.view.SSLeaseContractViewWindow;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class SSLeaseContractToolStrip extends BaseButtonToolStript {
	private SSLeaseContractListGrid ssLeaseContractListGrid;
	private SSLeaseSheetBusiness business = new SSLeaseSheetBusiness();

	public SSLeaseContractToolStrip(
			SSLeaseContractListGrid ssLeaseContractListGrid) {
		super(DSKey.S_LEASECONTRACT);
		this.setWidth100();
		this.setHeight(30);
		this.setLayoutLeftMargin(5);
		this.setLayoutRightMargin(5);
		this.ssLeaseContractListGrid = ssLeaseContractListGrid;
	}

	@Override
	protected void showWindow(String buttonName, ToolStripButton button) {
		// ADD,DELETE,MODIFY,PUBLISH,CANCELPUBLISH,ADDSALESQUOTATION,MESSAGE,LOG,PRINT,EXPORT,SENIORSEARCH
		if (buttonName.equals("ADD")) {
			// SSLeaseContractAddWindow addWin = new SSLeaseContractAddWindow();
			// ShowWindowTools.showWondow(button.getPageRect(), addWin, 500);
		} else if (buttonName.equals("PUBLISH")) {
			business.publishSheet(ssLeaseContractListGrid);
		} else if (buttonName.equals("CANCELPUBLISH")) {
			business.cancelPublishSheet(ssLeaseContractListGrid);
		} else if (buttonName.equals("DELETE")) {
			business.deleteSheet(ssLeaseContractListGrid);
		} else if (buttonName.equals("MODIFY")) {
			if (ValidateUtil.isRecordSelected(ssLeaseContractListGrid)) {
				SSLeaseContractUpdateWindow modify = new SSLeaseContractUpdateWindow();
				ShowWindowTools.showWindow(button.getPageRect(), modify, 200);
			}
		} else if (buttonName.equals("COMMITEXAMINE")) {
			// buiness.approvalContractFinally(leaseContractListGrid);
			business.doApproval(ssLeaseContractListGrid,
					WorkFlowBusinessType.SSLEASECONTRACT,
					WorkFlowSheetType.SSLEASECONTRACT, "id", "contractNumber",
					"extendedValueTotalAmount",
					"m_BussinessPublishStatusEntity.m_PublishStatus",
					"m_Priority");
		} else if (buttonName.equals("VIEW")) {
			if (ValidateUtil.isRecordSelected(ssLeaseContractListGrid)) {
				SSLeaseContractViewWindow view = new SSLeaseContractViewWindow();
				ShowWindowTools.showWindow(button.getPageRect(), view, 500);
			}
		}
	}
}
