package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.HtmlCssDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.grid.ListGrid;

@Singleton
public class HtmlCssLayout extends MySimpleGridLayout implements Iview{
	
	@Inject
	private HtmlCssDataSource hcds;
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
		grid.setDataSource(hcds);
		grid.setAutoFetchData(false);
		grid.setCanAcceptDroppedRecords(true);
		grid.setShowFilterEditor(true);
        grid.addSelectionChangedHandler(getSelectionChangedHandler());
	}
	
	@Override
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.HTMLCSS;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.HTMLCSS_EDIT;
	}

}
