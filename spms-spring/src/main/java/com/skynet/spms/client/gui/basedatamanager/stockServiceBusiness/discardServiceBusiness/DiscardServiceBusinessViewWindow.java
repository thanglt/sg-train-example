package com.skynet.spms.client.gui.basedatamanager.stockServiceBusiness.discardServiceBusiness;

import com.skynet.spms.client.tools.ShowWindowTools;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class DiscardServiceBusinessViewWindow extends Window {

	private DetailViewer detailView;

	public DiscardServiceBusinessViewWindow(final Rectangle rect
			,DiscardServiceBusinessListgrid discardServiceBusinessListgrid) {

		final Window objWindow = this;
		setWidth(360);
		setTitle("查看备件报废管理");
		setShowMinimizeButton(false);
		setIsModal(true);
		setHeight(310);
		setShowModalMask(true);
		centerInPage();
		addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClientEvent event) {
				ShowWindowTools.showCloseWindow(rect, objWindow, -1);
			}
		});

		detailView = new DetailViewer();
		detailView.setDataSource(discardServiceBusinessListgrid.getDataSource());
		ListGridRecord[] record = discardServiceBusinessListgrid.getSelection();

		detailView.setData(record);
		addItem(detailView);
	}
}