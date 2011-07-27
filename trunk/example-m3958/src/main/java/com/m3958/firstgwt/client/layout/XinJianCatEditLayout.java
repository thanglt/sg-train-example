package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.XinJianCatDataSource;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SectionField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.XinJianCatField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;

@Singleton
public class XinJianCatEditLayout extends MyTreeEditLayout implements Iview{
	
	@Inject
	private XinJianCatDataSource xjcds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setNumCols(1);
		modelForm.setIsGroup(true);
		modelForm.setGroupTitle(constants.cwFormTitle());
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(xjcds);
	    
	    TextItem nameItem = new TextItem(CommonField.NAME.getEname(),CommonField.NAME.getCname());
	    nameItem.setTitleOrientation(TitleOrientation.TOP);
	    nameItem.setRequired(true);
	    HiddenItem parentIdItem = new HiddenItem(SmartParameterName.PARENTID);
	    
	    CheckboxItem xjcatContainerItem = new CheckboxItem(XinJianCatField.XJCAT_CONTAINER.getEname(),XinJianCatField.XJCAT_CONTAINER.getCname());

	    
	    LengthRangeValidator lrv = new LengthRangeValidator();
	    lrv.setMin(2);
	    lrv.setMax(40);
	    nameItem.setValidators(lrv);
	    modelForm.setFields(auiService.getIdDisableItem(true),nameItem,parentIdItem,auiService.getVersionHiddenItem(),auiService.getCreatedAtHiddenItem(),getTplNameItem(true),getPositionItem(true),auiService.getPerpageItem(true),xjcatContainerItem);
	}
	
	@Override
	protected void editModel(Record r){
		editing = true;
		modelForm.editRecord(r);
		button.setTitle(constants.cwFormSaveButtonLabel());
	}
	
	@Override
	protected void newModel(){
		editing = false;
		modelForm.editNewRecord();
		button.setTitle(constants.cwFormCreateButtonLabel());
	}
	
	

	@Override
	protected String getModelName() {
		return XinJianCatDataSource.className;
	}


	@Override
	protected void initOtherWidget() {
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.XJ_CAT_EDIT;
	}

}
