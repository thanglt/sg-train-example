package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.CareerDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CareerField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;


@Singleton
public class CareerEditLayout extends HasMMRelationEditLayout implements Iview{
	
	@Inject
	private CareerDataSource cds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setWidth100();
		modelForm.setHeight(350);
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
		
		modelForm.setDataSource(cds);
		
		DateItem startItem = new DateItem(CareerField.START.getEname(),CareerField.START.getCname());
		startItem.setUseTextField(true);
		startItem.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		DateItem endItem = new DateItem(CareerField.END.getEname(),CareerField.END.getCname());
		endItem.setUseTextField(true);
		endItem.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
		
		TextItem danweiItem = new TextItem(CareerField.DANWEI.getEname(),CareerField.DANWEI.getCname());
		TextItem zhiwuItem = new TextItem(CareerField.ZHIWU.getEname(),CareerField.ZHIWU.getCname());
		TextAreaItem bzItem = new TextAreaItem(CareerField.BZ.getEname(),CareerField.BZ.getCname());
		bzItem.setHeight(50);
		
		modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),startItem,endItem,danweiItem,zhiwuItem,auiService.getCreatedAtHiddenItem(),bzItem);

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
		return ViewNameEnum.CAREER_EDIT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}


	@Override
	protected String getModelName() {
		return CareerDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
		
	}

}
