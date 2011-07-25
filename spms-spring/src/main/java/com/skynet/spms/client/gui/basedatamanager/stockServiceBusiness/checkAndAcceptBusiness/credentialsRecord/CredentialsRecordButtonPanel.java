/**
 * 
 */
package com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.checkAndAcceptBusiness.credentialsRecord;

import com.skynet.spms.client.gui.base.BaseButtonToolStript;
import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

	/**
	 * @author Administrator
	 *
	 */
public class CredentialsRecordButtonPanel extends BaseButtonToolStript {
	
	private CredentialsRecordListgrid credentialsRecordListgrid;

	public CredentialsRecordButtonPanel(
			final CredentialsRecordListgrid credentialsRecordListgrid) {
		super("stockServiceBusiness.checkAndAcceptBusiness.credentialsRecord");
		this.credentialsRecordListgrid = credentialsRecordListgrid;
	}
	
	protected void showWindow(String buttonName, final ToolStripButton objButton) {
		Window objWindow = null;
		Rectangle rect = objButton.getPageRect();
		
		if ("add".equalsIgnoreCase(buttonName)) {
			//objWindow = new CheckAndAcceptSheetDetailWindow("新建领料验收单", rect, checkAndAcceptSheetListgrid, false);
		}
		ShowWindowTools.showWondow(rect, objWindow, -1);
	}
}
