package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.MenuItemDataSource;
import com.m3958.firstgwt.client.datasource.MenuLevelDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.MenuItemField;
import com.m3958.firstgwt.client.types.MenuLevelField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;


@Singleton
public class MenuItemLayout extends HasMMRelationGridLayout implements Iview{
	
	@Inject
	private MenuItemDataSource mids;

	

	@Override
	protected void initGrid() {
		grid = new ListGrid();
		grid.setDataSource(mids);
	    grid.setShowFilterEditor(true);
	    grid.setShowRowNumbers(true);
	    grid.setSelectionType(SelectionStyle.MULTIPLE);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setAutoFetchData(false);
	    grid.setCanDragRecordsOut(true);
	    
	    ListGridField titleField = new ListGridField(MenuItemField.TITLE.getEname(),MenuItemField.TITLE.getCname());
	    ListGridField uniqueNameField = new ListGridField(MenuItemField.UNIQUE_NAME.getEname(),MenuItemField.UNIQUE_NAME.getCname());
	    ListGridField menuItemCatField = new ListGridField(MenuItemField.MENUITEMCAT.getEname(),MenuItemField.MENUITEMCAT.getCname());
	    
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),titleField,uniqueNameField,menuItemCatField,auiService.getCreatedAtField(false),auiService.getPositionField());
	    grid.addSelectionChangedHandler(getSelectionChangeHandler());
	}
	


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.MENUITEM;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		switch (btname) {
		case EDIT:
			return ViewNameEnum.MENUITEM_EDIT;
		case ADD:
			return ViewNameEnum.MENUITEM_EDIT;
		default:
			break;
		}
		return null;
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
	public String getModelName() {
		return MenuItemDataSource.className;
	}



	@Override
	public String getRelationModelName() {
		return MenuLevelDataSource.className;
	}



	@Override
	public String getMasterSideProperty() {
		return MenuLevelField.MENUITEMS.getEname();
	}

	@Override
	public boolean isMaster() {
		return false;
	}

	@Override
	public String getRelationHint() {
		return MenuItemLayout.class.getName();
	}

}
