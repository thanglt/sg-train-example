package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanShare;
import com.m3958.firstgwt.client.IHasRole;
import com.m3958.firstgwt.client.datasource.GroupDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

@Singleton
public class GroupLayout extends MyTreeGridLayout implements Iview,IHasRole,ICanShare{
	
	@Inject
	private GroupDataSource gds;

	public Btname[] btStatus0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] btStatus1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.ROLE,Btname.SHARE_TO_USER,Btname.SHARE_TO_GROUP,Btname.REFRESH};
	public Btname[] btStatus2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};

	@Override
	protected void initTreeGrid() {
		treeGrid = new TreeGrid();
		treeGrid.setDataSource(gds);
		treeGrid.setAutoFetchData(false);
		treeGrid.setTitleField(CommonField.NAME.getEname());
		treeGrid.setShowRoot(false);
		treeGrid.setCanAcceptDroppedRecords(true);
		treeGrid.addFolderDropHandler(getFdh());

        TreeGridField parentField = new TreeGridField(CommonField.PARENT_ID.getEname(),CommonField.PARENT_ID.getCname());
        parentField.setType(ListGridFieldType.INTEGER);
        parentField.setHidden(true);
        TreeGridField nameField = new TreeGridField(CommonField.NAME.getEname(),CommonField.NAME.getCname());

        treeGrid.setFields(nameField,auiService.getIdField(true),auiService.getCreatedAtField(false),parentField,auiService.getVersionField());
        treeGrid.addRecordClickHandler(getRecordClickHandler());
	}
	
	@Override
	public Btname[] getStripStatus() {
		if(treeGrid == null)return btStatus0;
		ListGridRecord[] rs = treeGrid.getSelection();
		if(rs.length == 0){
			return btStatus0;
		}else if(rs.length == 1){
			return btStatus1;
		}else{
			return btStatus2;
		}
	}
		
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.GROUP;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		switch (btname) {
		case ROLE:
			
			break;

		default:
			break;
		}
		return ViewNameEnum.GROUP_EDIT;
	}
	
	public String getOid(){
		return selectedRecordId;
	}
	
	
	public String getOname(){
		if(treeGrid.getSelectedRecord() != null){
			return treeGrid.getSelectedRecord().getAttributeAsString(CommonField.NAME.getEname());
		}else{
			return "";
		}
	}

	@Override
	public String getModelName() {
		return GroupDataSource.className;
	}

	@Override
	public ViewNameEnum getRightSideView() {
		return ViewNameEnum.USER;
	}

}
