package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanShare;
import com.m3958.firstgwt.client.datasource.DepartmentDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.DepartmentField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;


@Singleton
public class DepartmentLayout extends MyTreeGridLayout implements Iview,ICanShare{
	
	@Inject
	private DepartmentDataSource dds;
	
	public Btname[] btStatus0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] btStatus1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.SHARE_TO_USER,Btname.SHARE_TO_GROUP,Btname.REFRESH,Btname.CONFIRM};
	public Btname[] btStatus2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};
	
	@Override
	protected void initTreeGrid() {
		treeGrid = new TreeGrid();
		treeGrid.setDataSource(dds);
		treeGrid.setAutoFetchData(false);
		treeGrid.setTitleField(DepartmentField.NAME.getEname());
		treeGrid.setShowRoot(false);
		treeGrid.setSelectionType(SelectionStyle.SINGLE);
        
        TreeGridField parentField = new TreeGridField(CommonField.PARENT_ID.getEname(),CommonField.PARENT_ID.getEname());
        parentField.setType(ListGridFieldType.INTEGER);
        parentField.setHidden(true);
        
        TreeGridField cnameField = new TreeGridField(DepartmentField.NAME.getEname(),DepartmentField.NAME.getCname());
        
        TreeGridField bzField = new TreeGridField(DepartmentField.BZ.getEname(),DepartmentField.BZ.getCname());
        bzField.setHidden(true);
        TreeGridField shequContainerField = new TreeGridField(DepartmentField.SHEQU_CONTAINER.getEname(),DepartmentField.SHEQU_CONTAINER.getCname());
        shequContainerField.setHidden(true);

        treeGrid.setFields(cnameField,auiService.getIdField(true),auiService.getVersionField(),parentField,shequContainerField,auiService.getCreatedAtField(false),bzField);
        
        treeGrid.addRecordClickHandler(getRecordClickHandler());
	}
	
	@Override
	public Btname[] getStripStatus() {
		if(treeGrid == null)return btStatus0;
		ListGridRecord[] rs = treeGrid.getSelection();
		if(rs.length == 0){
			return btStatus1;
		}else if(rs.length == 1){
			return btStatus1;
		}else{
			return btStatus2;
		}
	}
	
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.DEPARTMENT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		switch (btname) {
		case EDIT:
			return ViewNameEnum.DEPARTMENT_EDIT;
		case ADD:
			return ViewNameEnum.DEPARTMENT_EDIT;
		default:
			break;
		}
		return null;
	}

	@Override
	public String getModelName() {
		return DepartmentDataSource.className;
	}

	@Override
	public ViewNameEnum getRightSideView() {
		return ViewNameEnum.LGB;
	}

	@Override
	public String getOid() {
		return selectedRecordId;
	}

	@Override
	public String getOname() {
		if(treeGrid.getSelectedRecord() != null){
			return treeGrid.getSelectedRecord().getAttributeAsString(DepartmentField.NAME.getEname());
		}else{
			return "";
		}
	}


}
