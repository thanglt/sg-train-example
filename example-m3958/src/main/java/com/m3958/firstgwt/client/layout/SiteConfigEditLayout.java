package com.m3958.firstgwt.client.layout;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.SiteConfigDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.ConfigKey;
import com.m3958.firstgwt.client.types.SiteConfigField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class SiteConfigEditLayout extends MySimpleEditLayout implements Iview{
	
	@Inject
	private SiteConfigDataSource scds;
    
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		SelectItem keyItem = new SelectItem(SiteConfigField.CONFIG_KEY.getEname(),SiteConfigField.CONFIG_KEY.getCname());
		keyItem.setValueMap(ConfigKey.getLinkedHashMap());
	    keyItem.setRequired(true);
	    TextItem valueItem = new TextItem(SiteConfigField.CONFIG_VALUE.getEname(),SiteConfigField.CONFIG_VALUE.getCname());
	    valueItem.setRequired(true);
	    TextItem descriptionItem = new TextItem(SiteConfigField.DESCRIPTION.getEname(),SiteConfigField.DESCRIPTION.getCname());
	    
	    CheckboxItem forClientItem = new CheckboxItem(SiteConfigField.CLIENT_SIDE.getEname(), SiteConfigField.CLIENT_SIDE.getCname());
	    
		modelForm.setWidth100();
		modelForm.setHeight(300);
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setDataSource(scds);
	    modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),keyItem,valueItem,descriptionItem,forClientItem,auiService.getCreatedAtHiddenItem());
	}
	
	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.SITECONFIG_EDIT;
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
		return SiteConfigDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
		
	}


}
