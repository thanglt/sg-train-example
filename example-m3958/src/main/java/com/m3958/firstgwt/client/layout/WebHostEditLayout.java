package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.DiskFileDataSource;
import com.m3958.firstgwt.client.datasource.WebHostDataSource;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.DiskFileField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.WebHostField;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class WebHostEditLayout extends HasOMRelationEditLayout implements Iview{
	
	@Inject
	private WebHostDataSource whds;
	
	@Inject
	private DiskFileDataSource dfds;

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setNumCols(2);
		modelForm.setIsGroup(true);
		modelForm.setGroupTitle(constants.cwFormTitle());
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(whds);
	    
	    TextItem nameItem = new TextItem(WebHostField.NAME.getEname(),WebHostField.NAME.getCname());
	    nameItem.setRequired(true);
	    
	    SelectItem styleItem = new SelectItem(WebHostField.THEME.getEname(),WebHostField.THEME.getCname());

	    styleItem.setOptionDataSource(dfds);
	    styleItem.setValueField(DiskFileField.FILE_NAME.getEname());
	    styleItem.setAnimatePickList(true);
	    styleItem.setTextMatchStyle(TextMatchStyle.EXACT);
	    styleItem.setPickListCriteria(getPickerCriteria());
	    
	    styleItem.setRequired(true);
	    CheckboxItem auditItem = new CheckboxItem(CommonField.AUDIT.getEname(), CommonField.AUDIT.getCname());
	    
	    modelForm.setFields(nameItem,styleItem,auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),auiService.getCreatedAtHiddenItem(),auditItem);
	}
	
	@Override
	public void changeDisplay(ViewAction va,String... paras) {
		super.changeDisplay(va, paras);
		SelectItem si = (SelectItem)modelForm.getItem(WebHostField.THEME.getEname());
		si.setPickListCriteria(getPickerCriteria());
		si.fetchData();
	}
	
	private Criteria getPickerCriteria(){
	    Criteria c = new Criteria();
	    c.addCriteria(DiskFileField.SITE_ID.getEname(),relationModelId);
	    c.addCriteria(DiskFileField.DIR_PATH.getEname(),"/");
	    c.addCriteria(CommonField.PURPOSE.getEname(),"theme");
	    return c;
	}
	

	@Override
	protected String getModelName() {
		return WebHostDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.WEBHOST_EDIT;
	}

	@Override
	protected String getRelationPropertyName() {
		return null;
	}



}
