package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.FieldEnumDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.FieldEnumField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class FieldEnumEditLayout extends MySimpleEditLayout implements Iview{
	
	@Inject
	private FieldEnumDataSource ds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		
	    SelectItem fieldTypeItem = new SelectItem(FieldEnumField.FIELD_TYPE.getEname(),FieldEnumField.FIELD_TYPE.getCname());
	    fieldTypeItem.setRequired(true);
	    TextItem fieldValueItem = new TextItem(FieldEnumField.FIELD_VALUE.getEname(),FieldEnumField.FIELD_VALUE.getCname());
	    fieldValueItem.setRequired(true);
	    fieldTypeItem.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 40));
	    TextItem position = new TextItem(FieldEnumField.POSITION.getEname(),FieldEnumField.POSITION.getCname());
	    
		modelForm.setWidth100();
		modelForm.setHeight(500);
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setDataSource(ds);
	    modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),fieldTypeItem,fieldValueItem,position,auiService.getCreatedAtHiddenItem());
	}
	

	@Override
	public String[] getParas() {
		return null;
	}

	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.FIELD_ENUM_EIDT;
	}


	@Override
	protected String getModelName() {
		return FieldEnumDataSource.className;
	}

}
