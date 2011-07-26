package com.skynet.spms.client.gui.customerService.leaseService.leaseInstruct;

import com.skynet.spms.client.gui.base.BaseButtonToolStript;
import com.skynet.spms.client.gui.base.ValidateUtil;
import com.skynet.spms.client.gui.customerService.leaseService.business.LeaseRequisitionSheetBusiness;
import com.skynet.spms.client.gui.customerService.leaseService.leaseInstruct.modify.RequisitonLeaseInstructUpdateWindow;
import com.skynet.spms.client.gui.customerService.leaseService.leaseInstruct.view.RequisitonLeaseInstructViewWindow;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class RequisitionLeaseInstructToolStrip extends BaseButtonToolStript {
	private LeaseRequisitionSheetBusiness bus = new LeaseRequisitionSheetBusiness();
	private RequisitionLeaseInstructListGrid requisitionLeaseInstructListGrid;

	public RequisitionLeaseInstructToolStrip(
			RequisitionLeaseInstructListGrid requisitionLeaseInstructListGrid) {
		super("customerService.leaseService.LeaseInstruct");
		this.requisitionLeaseInstructListGrid = requisitionLeaseInstructListGrid;
	}

	@Override
	protected void showWindow(String buttonName, ToolStripButton button) {
		// ADD,DELETE,MODIFY,VIEW,PRINT,PUBLISH,CANCELPUBLISH,MESSAGE,LOG,EXPORT,SENIORSEARCH
		if (buttonName.equals("MODIFY")) {
			if (ValidateUtil.isRecordSelected(requisitionLeaseInstructListGrid)) {
				RequisitonLeaseInstructUpdateWindow addWin = new RequisitonLeaseInstructUpdateWindow(
						"修改申请供应商租赁指令", true, button.getPageRect(), null, "");
				ShowWindowTools.showWindow(button.getPageRect(), addWin, 500);
			}
		} else if (buttonName.equals("PUBLISH")) {
			bus.publishSheet(requisitionLeaseInstructListGrid);
			// 取消发布
		} else if (buttonName.equals("CANCELPUBLISH")) {
			bus.cancelPublishSheet(requisitionLeaseInstructListGrid);
		} else if (buttonName.equals("DELETE")) {
			bus.deleteSheet(requisitionLeaseInstructListGrid);

		} else if (buttonName.equals("VIEW")) {
			if (ValidateUtil.isRecordSelected(requisitionLeaseInstructListGrid)) {
				RequisitonLeaseInstructViewWindow view = new RequisitonLeaseInstructViewWindow(
						"查看供应商申请租赁指令", true, button.getPageRect(), null, "");
				ShowWindowTools.showWindow(button.getPageRect(), view, 200);
			}
		}
	}
}
