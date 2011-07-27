package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.ShequDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.ShequField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class ShequEditLayout extends HasOMRelationEditLayout implements Iview{
	
	@Inject
	private ShequDataSource sds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		
		TextItem nameItem = new TextItem(ShequField.NAME.getEname(), ShequField.NAME.getCname());
		nameItem.setRequired(true);
		nameItem.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 40));
		
		TextItem dizhiItem = new TextItem(ShequField.DIZHI.getEname(), ShequField.DIZHI.getCname());
		TextItem dianhuaItem = new TextItem(ShequField.DIANHUA.getEname(), ShequField.DIANHUA.getCname());
		TextItem sqfzrItem = new TextItem(ShequField.SQFZR.getEname(), ShequField.SQFZR.getCname());
		TextItem shoujiItem = new TextItem(ShequField.SHOUJI.getEname(), ShequField.SHOUJI.getCname());
		TextAreaItem bzItem = new TextAreaItem(ShequField.BZ.getEname(), ShequField.BZ.getCname());
	
		modelForm.setDataSource(sds);
	    modelForm.setNumCols(2);
	    modelForm.setWidth100();
	    modelForm.setHeight(100);
	    modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),nameItem,dizhiItem,dianhuaItem,dianhuaItem,sqfzrItem,shoujiItem,bzItem,auiService.getCreatedAtHiddenItem());
	}


	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.SHEQU_EDIT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}


	@Override
	public String[] getParas() {
		return null;
	}

	@Override
	protected String getModelName() {
		return ShequDataSource.className;
	}


	@Override
	protected void initOtherWidget() {
		
	}


	@Override
	protected String getRelationPropertyName() {
		return null;
	}



}
