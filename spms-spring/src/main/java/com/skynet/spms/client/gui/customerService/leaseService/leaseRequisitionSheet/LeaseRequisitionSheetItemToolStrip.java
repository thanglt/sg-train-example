package com.skynet.spms.client.gui.customerService.leaseService.leaseRequisitionSheet;

import com.skynet.spms.client.gui.base.BaseButtonToolStript;
import com.skynet.spms.client.gui.base.ValidateUtil;
import com.skynet.spms.client.gui.customerService.leaseService.leaseRequisitionSheet.add.LeaseRequisitionSheetAddWindow;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class LeaseRequisitionSheetItemToolStrip extends BaseButtonToolStript {

	private LeaseRequisitionSheetListGrid leaseRequisitionSheetListGrid;
	private LeaseRequisitionSheetItemListGrid leaseRequisitionSheetItemListGrid;

	public LeaseRequisitionSheetItemToolStrip(
			final LeaseRequisitionSheetListGrid leaseRequisitionSheetListGrid,
			final LeaseRequisitionSheetItemListGrid leaseRequisitionSheetItemListGrid) {
		super("customerService.leaseService.leaseRequisitionSheet",
				new String[] { "ADD", "DELETE", "MODIFY", "REFRESH" });
		this.setWidth100();
		this.setHeight(40);
		this.setLayoutLeftMargin(5);
		this.setLayoutRightMargin(5);
		this.setMargin(5);
		this.leaseRequisitionSheetItemListGrid = leaseRequisitionSheetItemListGrid;
		this.leaseRequisitionSheetListGrid = leaseRequisitionSheetListGrid;
	}

	protected void showWindow(String buttonName, ToolStripButton button) {

		if (buttonName.equals("ADD")) {
			if (ValidateUtil.isRecordSelected(leaseRequisitionSheetListGrid)) {
				LeaseRequisitionSheetAddWindow addWin = new LeaseRequisitionSheetAddWindow();
				ShowWindowTools.showWondow(button.getPageRect(), addWin, 200);
			}
		} else if (buttonName.equals("DELETE")) {
			if (ValidateUtil
					.isRecordSelected(leaseRequisitionSheetItemListGrid)) {
				leaseRequisitionSheetItemListGrid
						.removeData(leaseRequisitionSheetItemListGrid
								.getSelectedRecord());
			}
		} else if (buttonName.equals("REFRESH")) {
			if (ValidateUtil.isRecordSelected(leaseRequisitionSheetListGrid)) {

			}
		}
	}

}
