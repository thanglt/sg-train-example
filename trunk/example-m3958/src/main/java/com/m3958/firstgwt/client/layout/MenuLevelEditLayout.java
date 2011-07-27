package com.m3958.firstgwt.client.layout;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.MenuLevelDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.MenuLevelField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.Layout;

@Singleton
public class MenuLevelEditLayout extends MySimpleEditLayout implements Iview{
	
	@Inject
	private MenuLevelDataSource mlds;

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setNumCols(2);
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(mlds);
	    
	    TextItem nameItem = new TextItem(MenuLevelField.NAME.getEname(),MenuLevelField.NAME.getCname());
	    nameItem.setRequired(true);
	    
	    final HiddenItem id = new HiddenItem("id");
	    final HiddenItem version = new HiddenItem("version");
	    
	    modelForm.setFields(nameItem,id,version);
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.MENULEVEL_EDIT;
	}


	@Override
	public Layout asLayout() {
		return this;
	}


	@Override
	public Btname[] getStripStatus() {
		return null;
	}


	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}

	@Override
	protected String getModelName() {
		return MenuLevelDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}



}
