package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.PageMistakeDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.PageMistakeField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;

@Singleton
public class PageMistakeLayout extends HasOMRelationGridLayout implements Iview{
	
	@Inject
	private PageMistakeDataSource pmds;
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setWidth100();
	    grid.setHeight100();
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);

	    grid.setDataSource(pmds);
	    grid.setAutoFetchData(false);

	    grid.addSelectionChangedHandler(getSelectionChangeHandler());
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.PAGE_MISTAKE;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.PAGE_MISTAKE_EDIT;
	}

	@Override
	public void fetchGroups() {
	}

	@Override
	public void fetchMine() {
	}

	@Override
	public void fetchOthers() {
	}

	@Override
	public void fetchAll() {
		
	}

	@Override
	public String getModelName() {
		return PageMistakeDataSource.className;
	}

	@Override
	public String getRelationModelName() {
		return PageMistakeDataSource.className;
	}

	@Override
	public String getMasterSideProperty() {
		return PageMistakeField.WEBSITE.getEname();
	}


	@Override
	public boolean isMaster() {
		return true;
	}

	@Override
	public String getRelationHint() {
		return PageMistakeLayout.class.getName();
	}


}
