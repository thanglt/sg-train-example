package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanConfirmSome;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.AssetFolderDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.AssetFolderField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

@Singleton
public class AssetFolderLayout extends MyTreeGridLayout implements Iview,ICanConfirmSome{
	
	@Inject
	private AssetFolderDataSource fds;
	
	private String siteId;
	
	public Btname[] btStatus0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] btStatus1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH,Btname.CONFIRM};
	public Btname[] btStatus2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};

	@Override
	protected void initTreeGrid() {
		treeGrid = new TreeGrid();
		treeGrid.setDataSource(fds);
		treeGrid.setAutoFetchData(false);
		treeGrid.setTitleField(AssetFolderField.NAME.getEname());
		treeGrid.setShowRoot(false);
		treeGrid.setCanAcceptDroppedRecords(true);
		treeGrid.addFolderDropHandler(getFdh());
		
		SortSpecifier ssf[] = {new SortSpecifier(CommonField.POSITION.getEname(),SortDirection.ASCENDING)};
		treeGrid.setSort(ssf);
        
        TreeGridField parentField = new TreeGridField(CommonField.PARENT_ID.getEname(),CommonField.PARENT_ID.getCname());
        parentField.setType(ListGridFieldType.INTEGER);
        parentField.setHidden(true);
        
        TreeGridField positionField = new TreeGridField(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
        
        TreeGridField nameField = new TreeGridField(AssetFolderField.NAME.getEname(),AssetFolderField.NAME.getCname());
        
        treeGrid.setFields(nameField,auiService.getIdField(true),auiService.getCreatedAtField(false),parentField,auiService.getVersionField(),positionField);
        treeGrid.addRecordClickHandler(getRecordClickHandler());
	}
	
	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(!initialized){
			initTreeGrid();
			addMember(treeGrid);
			fetchChildren();
			initialized = true;
			firstCall = true;
		}
		if(siteId == null || (!bservice.getSiteId().equals(siteId) && !firstCall)){
			siteId = bservice.getSiteId();
			firstCall = false;
			fetchChildren();
		}
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
	protected void fetchChildren(){
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_CHILDREN.toString());
	    if(!SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(bservice.getSiteId())){
	    	c.addCriteria(CommonField.SITE_ID.getEname(), bservice.getSiteId());
	    	c.addCriteria(SmartParameterName.SORTBY,CommonField.POSITION.getEname());
	    }
	    c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
	    treeGrid.filterData(c);
	}
		
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.ASSET_FOLDER;
	}


	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.ASSET_FOLDER_EDIT;
	}

	@Override
	protected String getModelName() {
		return AssetFolderDataSource.className;
	}

	@Override
	public Record[] getConfirmedThing() {
		return treeGrid.getSelection();
	}

	@Override
	public ViewNameEnum getRightSideView() {
		return ViewNameEnum.ASSET;
	}

}
