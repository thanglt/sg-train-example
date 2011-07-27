package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.AssetFolderDataSource;
import com.m3958.firstgwt.client.types.AssetFolderField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;

@Singleton
public class AssetFolderEditLayout extends MyTreeEditLayout implements Iview{
	
	@Inject
	private AssetFolderDataSource fds;

	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setNumCols(2);
		modelForm.setIsGroup(true);
		modelForm.setGroupTitle(constants.cwFormTitle());
		modelForm.setAlign(Alignment.LEFT);
		modelForm.setDataSource(fds);
	    
	    TextItem nameItem = new TextItem(AssetFolderField.NAME.getEname(),AssetFolderField.NAME.getCname());
	    nameItem.setRequired(true);
	    HiddenItem parentIdItem = new HiddenItem(SmartParameterName.PARENTID);
	    LengthRangeValidator lrv = new LengthRangeValidator();
	    lrv.setMin(2);
	    lrv.setMax(40);
	    nameItem.setValidators(lrv);
	    modelForm.setFields(auiService.getIdDisableItem(false),nameItem,auiService.getVersionHiddenItem(),auiService.getCreatedAtHiddenItem(),parentIdItem,auiService.getPositionItem(),auiService.getPerpageItem(false));
		
	}

	@Override
	protected String getModelName() {
		return AssetFolderDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.ASSET_FOLDER_EDIT;
	}
}
