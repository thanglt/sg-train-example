package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.FtlDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.FtlField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class FtlLayout extends MySimpleGridLayout implements Iview{
	
	@Inject
	private FtlDataSource fds;
	
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setShowFilterEditor(true);
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setFilterOnKeypress(true);
	    grid.setDataSource(fds);
	    grid.setAutoFetchData(false);
	    
	    ListGridField updatedAtField = new ListGridField(FtlField.UPDATEDAT.getEname(),FtlField.UPDATEDAT.getCname());
	    updatedAtField.setHidden(true);
	    updatedAtField.setCanFilter(false);
	    
	    ListGridField nameField = new ListGridField(FtlField.NAME.getEname(),FtlField.NAME.getCname());
	    
	    ListGridField descriptionField = new ListGridField(FtlField.DESCRIPTION.getEname(),FtlField.DESCRIPTION.getCname());
	    
//	    ListGridField ftlField = new ListGridField(FtlField.FTL.getEname(),FtlField.FTL.getCname());
	    
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),auiService.getCreatedAtField(false),updatedAtField,nameField,descriptionField);
	    grid.addSelectionChangedHandler(getSelectionChangedHandler());

	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.FTL;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.FTL_EDIT;
	}

	@Override
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}


}
