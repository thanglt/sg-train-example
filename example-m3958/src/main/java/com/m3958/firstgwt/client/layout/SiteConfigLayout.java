package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.SiteConfigDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SiteConfigField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class SiteConfigLayout extends MySimpleGridLayout implements Iview{
	
	@Inject
	private SiteConfigDataSource scds;

	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setShowFilterEditor(true);
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setFilterOnKeypress(true);
	    grid.setDataSource(scds);
	    grid.setAutoFetchData(false);
	    
	    ListGridField keyField = new ListGridField(SiteConfigField.CONFIG_KEY.getEname(),SiteConfigField.CONFIG_KEY.getCname());
	    
	    ListGridField valueField = new ListGridField(SiteConfigField.CONFIG_VALUE.getEname(),SiteConfigField.CONFIG_VALUE.getCname());
	    
	    ListGridField forClientField = new ListGridField(SiteConfigField.CLIENT_SIDE.getEname(),SiteConfigField.CLIENT_SIDE.getCname());
	    forClientField.setType(ListGridFieldType.BOOLEAN);
	    
	    ListGridField descriptionField = new ListGridField(SiteConfigField.DESCRIPTION.getEname(),SiteConfigField.DESCRIPTION.getCname());
	    
	    grid.setFields(forClientField,auiService.getIdField(true),keyField,valueField,descriptionField,auiService.getCreatedAtField(false));
	    grid.addSelectionChangedHandler(getSelectionChangedHandler());
	}


	public ListGrid getUserListGrid() {
		return grid;
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.SITE_CONFIG;
	}


	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.SITECONFIG_EDIT;
	}




	@Override
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}



}
