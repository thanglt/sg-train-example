package com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.base.partEntityBusiness.partLifetimeInformation;

import com.skynet.spms.client.gui.base.BaseButtonToolStript;
import com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.discardServiceBusiness.DiscardServiceBusinessDetailWindow;
import com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.discardServiceBusiness.DiscardServiceBusinessListgrid;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PartLifeTimeButtonPanel extends BaseButtonToolStript {
	private PartLifeTimeListgrid partLifetimeInformationListgrid;

	public PartLifeTimeButtonPanel(
			final PartLifeTimeListgrid partLifetimeInformationListgrid) {
		super("stockServiceBusiness.partsInventory.partLifeTime");
		this.partLifetimeInformationListgrid = partLifetimeInformationListgrid;
	}

	protected void showWindow(String buttonName, ToolStripButton objButton) {
		Window objWindow = null;
		Rectangle rect = objButton.getPageRect();
		
		if ("add".equalsIgnoreCase(buttonName)) {
			objWindow = new PartLifeTimeDetailWindow("新增备件时寿信息", false, rect, partLifetimeInformationListgrid, "icons/add.gif", false);
		} else if ("modify".equalsIgnoreCase(buttonName)) {
			if (partLifetimeInformationListgrid.getSelection().length == 1) {
				objWindow = new PartLifeTimeDetailWindow("修改备件时寿信息", false, rect, partLifetimeInformationListgrid, "icons/add.gif", true);
			} else {
				SC.say("请选择一条记录进行修改！");
			}
		} else if ("delete".equalsIgnoreCase(buttonName)) {
			if (partLifetimeInformationListgrid.getSelection().length != 0) {
				boolean isDel = com.google.gwt.user.client.Window
						.confirm("是否要删除选中的记录！");
				if (isDel) {
					partLifetimeInformationListgrid.removeSelectedData();
					return;
				}
			} else {
				SC.say("请选择一条记录进行删除！");
			}
		} else if ("DISCARD".equalsIgnoreCase(buttonName)) {
			if (partLifetimeInformationListgrid.getSelection().length == 1) {
				objWindow = new DiscardServiceBusinessDetailWindow("修改备件报废管理", false, rect, partLifetimeInformationListgrid, "showwindow/stock_modify_01.png", true,false);
			} else {
				SC.say("请选择一条记录进行修改！");
			}
		} 
		ShowWindowTools.showWondow(rect, objWindow, -1);
	}
}