package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.GlobalConstants;
import com.m3958.firstgwt.client.datasource.JrxmlFileDataSource;
import com.m3958.firstgwt.client.types.JrxmlFileField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class JrxmlFileEditLayout extends MyTreeEditLayout implements Iview{
	
	@Inject
	private JrxmlFileDataSource jfds;

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();

	    HiddenItem parentIdItem = new HiddenItem(JrxmlFileField.PARENTID.getEname());
	
	    TextItem nameItem = new TextItem(JrxmlFileField.NAME.getEname(),JrxmlFileField.NAME.getCname());
	    nameItem.setRequired(true);
	    
	    SelectItem modelNameItem = auiService.createSelectItem(JrxmlFileField.MODEL_NAME.getEname(),JrxmlFileField.MODEL_NAME.getCname(),GlobalConstants.LimitFieldName.LMC.getName());
	    modelNameItem.setRequired(true);
	    modelNameItem.setWidth(300);
	    modelNameItem.setColSpan(4);
	    
   
	    TextAreaItem jrxmlItem = new TextAreaItem(JrxmlFileField.JRXML.getEname(),JrxmlFileField.JRXML.getCname());
	    jrxmlItem.setRequired(true);
	    jrxmlItem.setWidth(450);
	    jrxmlItem.setHeight(250);
	    jrxmlItem.setColSpan(4);
    

	    
		modelForm.setWidth100();
		modelForm.setHeight100();
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setDataSource(jfds);
	    modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),nameItem,modelNameItem,auiService.getCreatedAtHiddenItem(),jrxmlItem,parentIdItem);
	}


	@Override
	protected String getModelName() {
		return JrxmlFileDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}



	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.JRXML_EDIT;
	}




}
