package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.PageMistakeDataSource;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;

@Singleton
public class PageMistakeEditLayout extends HasOMRelationEditLayout implements Iview{
	
	@Inject
	private PageMistakeDataSource pmds;
	

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setNumCols(2);
		modelForm.setIsGroup(true);
		modelForm.setGroupTitle(constants.cwFormTitle());
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(pmds);
	}
	

	@Override
	protected String getModelName() {
		return PageMistakeDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.PAGE_MISTAKE_EDIT;
	}

	@Override
	protected String getRelationPropertyName() {
		return null;
	}



}
