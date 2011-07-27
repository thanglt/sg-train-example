package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.StepCareerDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.StepCareerField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;

@Singleton
public class StepCareerEditLayout extends HasMMRelationEditLayout implements Iview {
	
	@Inject
	private StepCareerDataSource scds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setWidth100();
		modelForm.setHeight(350);
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());

		modelForm.setDataSource(scds);
		
		DateItem startItem = new DateItem(StepCareerField.START.getEname(),StepCareerField.START.getCname());
		startItem.setUseTextField(true);
		startItem.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		DateItem endItem = new DateItem(StepCareerField.END.getEname(),StepCareerField.END.getCname());
		endItem.setUseTextField(true);
		endItem.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);

		
		TextAreaItem zysjItem = new TextAreaItem(StepCareerField.ZYSJ.getEname(),StepCareerField.ZYSJ.getCname());
		zysjItem.setHeight(50);
		TextAreaItem bzItem = new TextAreaItem(StepCareerField.BZ.getEname(),StepCareerField.BZ.getCname());
		bzItem.setHeight(50);
		
		modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),startItem,endItem,zysjItem,bzItem,auiService.getCreatedAtHiddenItem());
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
		return ViewNameEnum.STEP_CAREER_EDIT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}

	@Override
	protected String getModelName() {
		return StepCareerDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
		
	}



}
