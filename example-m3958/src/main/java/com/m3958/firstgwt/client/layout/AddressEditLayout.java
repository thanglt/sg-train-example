package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.AddressDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.AddressField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class AddressEditLayout extends HasMMRelationEditLayout implements Iview{
	
	@Inject
	private AddressDataSource ads;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setWidth100();
		modelForm.setHeight(350);
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());

	    modelForm.setDataSource(ads);
	    
		TextItem dizhiItem = new TextItem(AddressField.DIZHI.getEname(),AddressField.DIZHI.getCname());
		dizhiItem.setRequired(true);
		dizhiItem.setValidators(FormValidatorUtils.getLengthRangeValidator(4, 60));
		TextItem dianhuaItem = new TextItem(AddressField.DIANHUA.getEname(),AddressField.DIANHUA.getCname());
		TextItem shoujiItem = new TextItem(AddressField.SHOUJI.getEname(),AddressField.SHOUJI.getCname());
		TextAreaItem bzItem = new TextAreaItem(AddressField.BZ.getEname(),AddressField.BZ.getCname());
		bzItem.setColSpan(4);
		CheckboxItem mainAddressItem = new CheckboxItem(AddressField.MAIN_ADDRESS.getEname(),AddressField.MAIN_ADDRESS.getCname());
		bzItem.setHeight(50);
		
		modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),dizhiItem,dianhuaItem,shoujiItem,auiService.getCreatedAtHiddenItem(),mainAddressItem,bzItem);
	}


	@Override
	public String[] getParas() {
		return null;
	}

	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	public ViewNameEnum getViewName() {
		return ViewNameEnum.ADDRESS_EDIT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}


	@Override
	protected String getModelName() {
		return AddressDataSource.className;
	}


	@Override
	protected void initOtherWidget() {
		
	}

}
