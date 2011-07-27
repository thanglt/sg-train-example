package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.datasource.HgllDatasource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;

public class HgllLayout extends MySimpleGridLayout {
	
	@Inject
	private HgllDatasource hds;


	@Override
	protected void initGrid() {
		grid = new ListGrid();
		grid.setDataSource(hds);
	    grid.setShowFilterEditor(true);
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setFilterOnKeypress(true);
	    grid.setAutoFetchData(false);
	    grid.setShowDetailFields(false);

		grid.addSelectionChangedHandler(getSelectionChangedHandler());
	}

	
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.HGLL;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.HGLL_EDIT;
	}
	
	@Override
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}

}
