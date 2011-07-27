package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.RewardDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.RewardField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class RewardEditLayout extends HasMMRelationEditLayout implements Iview{
	
	@Inject
	private RewardDataSource rds;

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setWidth100();
		modelForm.setHeight(350);
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());

	    modelForm.setDataSource(rds);
		TextAreaItem bzItem = new TextAreaItem(RewardField.BZ.getEname(),RewardField.BZ.getCname());
		bzItem.setHeight(50);

	    TextItem jlqcItem = new TextItem(RewardField.JLQK.getEname(),RewardField.JLQK.getCname());
	    TextItem cfqcItem = new TextItem(RewardField.CFQK.getEname(),RewardField.CFQK.getCname());
	    
	    
	    modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),jlqcItem,cfqcItem,bzItem,auiService.getCreatedAtHiddenItem());
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
		return ViewNameEnum.REWARD_EDIT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}



	@Override
	protected String getModelName() {
		return RewardDataSource.className;
	}


	@Override
	protected void initOtherWidget() {
		
	}


}
