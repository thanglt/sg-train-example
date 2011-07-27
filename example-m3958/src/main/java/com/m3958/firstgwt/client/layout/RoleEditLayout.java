package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.RoleDataSource;
import com.m3958.firstgwt.client.types.RoleField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class RoleEditLayout extends MySimpleEditLayout implements Iview{
	
	@Inject
	private RoleDataSource rds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
	    modelForm.setWidth100();
	    modelForm.setHeight(60);
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setDataSource(rds);
	    TextItem orname = new TextItem(RoleField.ORNAME.getEname(),RoleField.ORNAME.getCname());
	    orname.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 40));
	    TextItem cname = new TextItem(RoleField.CNAME.getEname(),RoleField.CNAME.getCname());
	    cname.setRequired(true);
	    cname.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 40));
	    HiddenItem id = new HiddenItem("id");
	    HiddenItem version = new HiddenItem("version");
	    modelForm.setFields(orname,cname,id,version);
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.ROLE_EDIT;
	}

	@Override
	protected String getModelName() {
		return RoleDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}


}
