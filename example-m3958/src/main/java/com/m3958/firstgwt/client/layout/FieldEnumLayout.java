package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.FieldEnumDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.FieldEnumField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;


@Singleton
public class FieldEnumLayout extends MySimpleGridLayout implements Iview{
	
	@Inject
	private FieldEnumDataSource ds;
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setShowFilterEditor(true);
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setFilterOnKeypress(true);
	    grid.setDataSource(ds);
	    grid.setAutoFetchData(false);
	    grid.setWidth100();
	    grid.setHeight100();

	    ListGridField positionField = new ListGridField(FieldEnumField.POSITION.getEname(),FieldEnumField.POSITION.getCname());
	    ListGridField fieldTypeField = new ListGridField(FieldEnumField.FIELD_TYPE.getEname(),FieldEnumField.FIELD_TYPE.getCname());
	    ListGridField fieldValueField = new ListGridField(FieldEnumField.FIELD_VALUE.getEname(),FieldEnumField.FIELD_VALUE.getCname());
	    grid.setFields(auiService.getIdField(true),fieldTypeField,fieldValueField,positionField,auiService.getVersionField(),auiService.getCreatedAtField(false));
	    grid.addSelectionChangedHandler(getSelectionChangedHandler());
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.FIELD_ENUM;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.FIELD_ENUM_EIDT;
	}
	
	@Override
	public void fetchGridData() {
		Criteria c = new Criteria();
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}
}
