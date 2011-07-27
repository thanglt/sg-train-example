package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.HtmlCssDataSource;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;

@Singleton
public class HtmlCssEditLayout extends MySimpleEditLayout implements Iview{
	
	@Inject
	private HtmlCssDataSource hcds;
	

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setNumCols(2);
		modelForm.setIsGroup(true);
		modelForm.setGroupTitle(constants.cwFormTitle());
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(hcds);
	}
	

	@Override
	protected String getModelName() {
		return HtmlCssDataSource.className;
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.HTMLCSS_EDIT;
	}
}
