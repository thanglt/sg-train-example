package com.skynet.spms.client.gui.customerplatform.exchangeRequisitionSheet;

import com.skynet.spms.client.gui.base.BaseBusiness;
import com.skynet.spms.client.gui.base.ValidateUtil;
import com.skynet.spms.client.gui.customerService.i18n.I18n;
import com.skynet.spms.client.gui.customerService.repairService.repairContract.add.RepairContractTemplateAddWin;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.skynet.spms.client.tools.UserTools;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * 处理送修申请业务
 * 
 * @author tqc
 * 
 */
public class ExchangeRequisitionSheetBusiness extends BaseBusiness {

	private I18n ui = new I18n();

	public SheetModelLocator model = SheetModelLocator.getInstance();

	public ExchangeRequisitionSheetBusiness() {

	}

	/**
	 * 刷新送修申请表格
	 */
	public void refeshRQGrird() {
		Criteria c = new Criteria();
		c.setAttribute("userName", UserTools.getCurrentUserName());
		c.setAttribute("_r", String.valueOf(Math.random()));
		model.repairRequisitionListGrid.fetchData(c);
	}
	
	/***
	 * 关闭窗体
	 */
	public void closeWindow(){
		model.parentWindow.destroy();
	}


}
