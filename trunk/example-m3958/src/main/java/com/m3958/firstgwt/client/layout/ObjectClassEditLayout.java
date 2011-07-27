package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.ObjectClassNameDataSource;
import com.m3958.firstgwt.client.types.ObjectClassNameField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class ObjectClassEditLayout extends MySimpleEditLayout implements Iview {
	
	@Inject
	private ObjectClassNameDataSource ocds;
	
	@Override
	protected void initModelForm() {
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    modelForm.setDataSource(ocds);
	    HiddenItem idItem = new HiddenItem("id");
	    HiddenItem versionItem = new HiddenItem("version");
	    
	    TextItem cnameItem = new TextItem(ObjectClassNameField.CNAME.getEname(),ObjectClassNameField.CNAME.getEname());
	    
	    TextItem enameItem = new TextItem(ObjectClassNameField.ENAME.getEname(),ObjectClassNameField.ENAME.getEname());
	    
	    TextItem daoItem = new TextItem(ObjectClassNameField.DAO_NAME.getEname(),ObjectClassNameField.DAO_NAME.getEname());
	    
	    TextItem checkItem = new TextItem(ObjectClassNameField.CHECKER_NAME.getEname(),ObjectClassNameField.CHECKER_NAME.getEname());

	    modelForm.setFields(idItem,versionItem,cnameItem,enameItem,daoItem,checkItem);
	    
	}

	@Override
	protected String getModelName() {
		return ObjectClassNameDataSource.className;
	}

	@Override
	protected void initOtherWidget() {
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.OBJECT_CLASS_EDIT;
	}

}
