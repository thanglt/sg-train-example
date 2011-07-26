package com.skynet.spms.client.gui.customerplatform.salesRequisitionSheet;

import com.skynet.spms.client.gui.base.BaseButtonToolStript;
import com.skynet.spms.client.gui.customerService.common.DSKey;
import com.skynet.spms.client.gui.customerService.salesService.SalesRequisitionSheet.business.SalesRequisitionSheetBusiness;
import com.skynet.spms.client.gui.customerplatform.DoQuotationPanel.view.SalesInquiryViewWindow;
import com.skynet.spms.client.gui.customerplatform.salesRequisitionSheet.add.SalesRequisitionAddWindow;
import com.skynet.spms.client.gui.customerplatform.salesRequisitionSheet.model.SalesRequisitionModel;
import com.skynet.spms.client.gui.customerplatform.salesRequisitionSheet.modify.SalesRequisitionModifyWindow;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.skynet.spms.client.gui.customerplatform.common.ModuleKey;
import com.skynet.spms.client.gui.customerplatform.salesRequisitionSheet.view.SalesRequisitionViewWindow;
public class SalesRequisitionSheetToolStrip extends BaseButtonToolStript {

	private SalesRequisitionModel model = SalesRequisitionModel.getInstance();

	public SalesRequisitionSheetToolStrip() {
		super(ModuleKey.C_ORDERTRACK);
		this.setWidth100();
		this.setHeight(30);
		this.setLayoutLeftMargin(5);
		this.setLayoutRightMargin(5);
	}

	@Override
	protected void showWindow(String buttonName, ToolStripButton button) {
		SalesRequisitionSheetBusiness bus = new SalesRequisitionSheetBusiness();
		if ("ADD".equals(buttonName)) {
			SalesRequisitionAddWindow win = new SalesRequisitionAddWindow(
					"新建订单", true, button.getPageRect(), null, "");
			ShowWindowTools.showWindow(button.getPageRect(), win, 200);

		} else if (("MODIFY").equals(buttonName)) {

			if (bus.canModifiedSheet(model.listGrid)) {
				SalesRequisitionModifyWindow win = new SalesRequisitionModifyWindow(
						"修改订单", true, button.getPageRect(), null, "");
				ShowWindowTools.showWindow(button.getPageRect(), win, 200);
			}

		} else if ("DELETE".equals(buttonName)) {
			bus.deleteSheet(model.listGrid);
		} else if ("PUBLISH".equals(buttonName)) {
			bus.publishSheet(model.listGrid);
		} else if ("CANCELPUBLISH".equals(buttonName)) {
			bus.cancelPublishSheet(model.listGrid);
		} else if ("VIEW".equals(buttonName)) {
			// 先给主键赋值
			model.primaryKey = model.listGrid.getSelectedRecord().getAttribute(
					"id");
			SalesRequisitionViewWindow win = new SalesRequisitionViewWindow("订单详细",
					true,null, null, "");
			win.show();
			//ShowWindowTools.showWindow(button.getPageRect(), win, 200);
		}

	}

}
