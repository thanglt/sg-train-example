package com.m3958.firstgwt.client.widgets;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.MenuItemField;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;


public class MainMenuListGrid extends ListGrid{
	
	@Inject
	public MainMenuListGrid(){
		setShowHeader(false);
		ListGridField titleField = new ListGridField(MenuItemField.TITLE.getEname(), MenuItemField.TITLE.getCname());
		ListGridField catField = new ListGridField(MenuItemField.MENUITEMCAT.getEname(), MenuItemField.MENUITEMCAT.getCname());
		catField.setHidden(true);
		ListGridField tokenField = new ListGridField(MenuItemField.UNIQUE_NAME.getEname(), MenuItemField.UNIQUE_NAME.getCname());
		tokenField.setHidden(true);
		setFields(titleField,catField,tokenField);
		setSelectionType(SelectionStyle.SINGLE);
	}
}
