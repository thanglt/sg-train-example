package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.ObjectClassNameDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ObjectClassNameField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class ObjectClassLayout extends MySimpleGridLayout implements Iview{
	
	@Inject
	private ObjectClassNameDataSource ocds;


	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setDataSource(ocds);
	    grid.setAutoFetchData(false);
	    
	    ListGridField enameField = new ListGridField(ObjectClassNameField.ENAME.getEname(),ObjectClassNameField.ENAME.getCname());
	    ListGridField cnameField = new ListGridField(ObjectClassNameField.CNAME.getEname(),ObjectClassNameField.CNAME.getCname());
	    
	    ListGridField daoNameField = new ListGridField(ObjectClassNameField.DAO_NAME.getEname(),ObjectClassNameField.DAO_NAME.getCname());
	    ListGridField checkNameField = new ListGridField(ObjectClassNameField.CHECKER_NAME.getEname(),ObjectClassNameField.CHECKER_NAME.getCname());
	    
	    grid.setFields(auiService.getIdField(true),enameField,cnameField,daoNameField,checkNameField,auiService.getCreatedAtField(false));
	    grid.addSelectionChangedHandler(getSelectionChangedHandler());
	}



	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.OBJECT_CLASS;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.OBJECT_CLASS_EDIT;
	}



	@Override
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}




}
