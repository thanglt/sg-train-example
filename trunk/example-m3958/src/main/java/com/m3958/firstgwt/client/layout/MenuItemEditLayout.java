package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.MenuItemDataSource;
import com.m3958.firstgwt.client.types.MenuItemCategory;
import com.m3958.firstgwt.client.types.MenuItemField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class MenuItemEditLayout extends HasMMRelationEditLayout implements Iview{

	@Inject
	private MenuItemDataSource mids;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
	    modelForm.setNumCols(4);

	    modelForm.setWidth100();
	    modelForm.setDataSource(mids);

		TextItem titleItem = new TextItem(MenuItemField.TITLE.getEname(),MenuItemField.TITLE.getCname());
	    titleItem.setRequired(true);
	    
	    TextItem uniqueNameItem = new TextItem(MenuItemField.UNIQUE_NAME.getEname(),MenuItemField.UNIQUE_NAME.getCname());
	    uniqueNameItem.setRequired(true);

	    SelectItem catItem = new SelectItem(MenuItemField.MENUITEMCAT.getEname(),MenuItemField.MENUITEMCAT.getCname());
	    catItem.setRequired(true);
	    
	    MenuItemCategory[] vv = MenuItemCategory.values();
	    String[] values = new String[vv.length];
	    
	    for(int i=0;i<vv.length;i++){
	    	values[i] = vv[i].toString();
	    }
	    catItem.setValueMap(values);
	    
	    modelForm.setItems(titleItem,uniqueNameItem,catItem,auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),auiService.getCreatedAtHiddenItem(),auiService.getPositionItem());
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.MENUITEM_EDIT;
	}

	@Override
	protected String getModelName() {
		return MenuItemDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}



}
