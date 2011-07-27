package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.GlobalConstants;
import com.m3958.firstgwt.client.datasource.HouseDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.HouseField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class HouseEditLayout extends HasMMRelationEditLayout implements Iview{
	
	@Inject
	private HouseDataSource hds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setWidth100();
		modelForm.setHeight(350);
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());

	    modelForm.setDataSource(hds);
	    
		TextAreaItem bzItem = new TextAreaItem(HouseField.BZ.getEname(),HouseField.BZ.getCname());
		bzItem.setHeight(50);

		SelectItem zfxzItem = auiService.createSelectItem(HouseField.ZFXZ.getEname(),HouseField.ZFXZ.getCname(),GlobalConstants.LimitFieldName.ZFXZ.getName());
	    
	    TextItem areaItem = new TextItem(HouseField.AREA.getEname(),HouseField.AREA.getCname());
	    
	    SelectItem structureItem = auiService.createSelectItem(HouseField.STRUCTURE.getEname(),HouseField.STRUCTURE.getCname(),GlobalConstants.LimitFieldName.ZFJG.getName());
	    
	    modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),zfxzItem,areaItem,structureItem,bzItem,auiService.getCreatedAtHiddenItem());
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
		return ViewNameEnum.HOUSE_EDIT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}

	@Override
	protected String getModelName() {
		return HouseDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
		
	}


}
