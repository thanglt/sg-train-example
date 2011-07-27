package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.JrxmlFileDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.JrxmlFileField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

@Singleton
public  class JrxmlFileLayout extends MyTreeGridLayout implements Iview{
	
	@Inject
	private JrxmlFileDataSource jfds;

	
	@Override
	protected void initTreeGrid() {
		treeGrid = new TreeGrid();
	    treeGrid.setShowFilterEditor(true);
	    treeGrid.setShowRowNumbers(false);
	    treeGrid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    treeGrid.setInitialSort(ssf);
	    treeGrid.setFilterOnKeypress(true);
	    
	    treeGrid.setDataSource(jfds);
	    treeGrid.setAutoFetchData(false);
    
	    TreeGridField parentIdField = new TreeGridField(SmartParameterName.PARENTID);
	    parentIdField.setHidden(true);
	    
	    TreeGridField nameField = new TreeGridField(JrxmlFileField.NAME.getEname(),JrxmlFileField.NAME.getCname());
	    
	    TreeGridField jrxmlField = new TreeGridField(JrxmlFileField.JRXML.getEname(),JrxmlFileField.JRXML.getCname());
	    jrxmlField.setHidden(true);
	    TreeGridField modelNameField = new TreeGridField(JrxmlFileField.MODEL_NAME.getEname(),JrxmlFileField.MODEL_NAME.getCname());
    
	    treeGrid.setFields(auiService.getIdField(true),jrxmlField,modelNameField,nameField,parentIdField,auiService.getCreatedAtField(false),auiService.getVersionField());
	    treeGrid.addSelectionChangedHandler(getSelectionChangeHandler());

	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.JRXML;
	}


	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.JRXML_EDIT;
	}


	@Override
	protected String getModelName() {
		return null;
	}


	@Override
	public ViewNameEnum getRightSideView() {
		return null;
	}


}
