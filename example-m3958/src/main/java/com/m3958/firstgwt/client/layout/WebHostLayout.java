package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.WebHostDataSource;
import com.m3958.firstgwt.client.datasource.WebSiteDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.WebHostField;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class WebHostLayout extends HasOMRelationGridLayout implements Iview{
	
	@Inject
	private WebHostDataSource whds;
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setWidth100();
	    grid.setHeight100();
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);

	    grid.setDataSource(whds);
	    grid.setAutoFetchData(false);

        ListGridField nameField = new ListGridField(WebHostField.NAME.getEname(),WebHostField.NAME.getCname());
        nameField.setType(ListGridFieldType.LINK);
        nameField.setRequired(true);
        
        ListGridField auditField = new ListGridField(CommonField.AUDIT.getEname(),CommonField.AUDIT.getCname());
        
        ListGridField styleField = new ListGridField(WebHostField.THEME.getEname(),WebHostField.THEME.getCname());
        
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),auiService.getCreatedAtField(false),nameField,styleField,auditField);
	    grid.addSelectionChangedHandler(getSelectionChangeHandler());
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.WEBHOST;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.WEBHOST_EDIT;
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
		return WebHostDataSource.className;
	}

	@Override
	public String getRelationModelName() {
		return WebSiteDataSource.className;
	}

	@Override
	public String getMasterSideProperty() {
		return WebHostField.WEBSITE.getEname();
	}


	@Override
	public boolean isMaster() {
		return true;
	}

	@Override
	public String getRelationHint() {
		return WebHostLayout.class.getName();
	}


}
