package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanConfirmSome;
import com.m3958.firstgwt.client.ICanShare;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.SectionDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
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
public class SectionLayout extends MyTreeGridLayout implements Iview,ICanShare,ICanConfirmSome{
	
	
	@Inject
	private SectionDataSource sds;
	
	private String siteId;
	
	public Btname[] btStatus0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] btStatus1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH,Btname.CONFIRM,Btname.SHARE_TO_USER,Btname.SHARE_TO_GROUP};
	public Btname[] btStatus2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH};


	@Override
	protected void initTreeGrid() {
		treeGrid = new TreeGrid();
		treeGrid.setAutoFetchData(false);
		treeGrid.setTitleField(CommonField.NAME.getEname());
		treeGrid.setShowRoot(false);
		treeGrid.setCanAcceptDroppedRecords(true);
		treeGrid.setDataSource(sds);
		
		SortSpecifier ssf[] = {new SortSpecifier(CommonField.POSITION.getEname(),SortDirection.ASCENDING)};
		treeGrid.setSort(ssf);
		
        TreeGridField parentField = new TreeGridField(CommonField.PARENT_ID.getEname(),CommonField.PARENT_ID.getCname());
        parentField.setType(ListGridFieldType.INTEGER);
        parentField.setHidden(true);
        
        TreeGridField nameField = new TreeGridField(CommonField.NAME.getEname(),CommonField.NAME.getCname());
        
        TreeGridField hiddenField = new TreeGridField(CommonField.HIDDEN.getEname(),CommonField.HIDDEN.getCname());
        hiddenField.setWidth(20);

        treeGrid.setFields(nameField,auiService.getIdField(false),auiService.getCreatedAtField(false),parentField,auiService.getVersionField(),auiService.getPositionField(),hiddenField);
        treeGrid.addRecordClickHandler(getRecordClickHandler());
        treeGrid.addFolderDropHandler(getFdh());
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
		switch (va) {
		case REMOVE:
			treeGrid.removeSelectedData();
			selectedRecordId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
			break;
		default:
			if(siteId == null || (!bservice.getSiteId().equals(siteId) && !firstCall)){
				firstCall = false;
				siteId = bservice.getSiteId();
				fetchChildren();
				selectedRecordId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
			}
			break;
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
	public String[] getParas() {
		return new String[]{selectedRecordId};
	}
		
	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.SECTION;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.SECTION_EDIT;
	}

	@Override
	public String getModelName() {
		return SectionDataSource.className;
	}

	@Override
	public String getOid() {
		return selectedRecordId;
	}

	@Override
	public String getOname() {
		if(treeGrid.getSelectedRecord() != null){
			return treeGrid.getSelectedRecord().getAttributeAsString(CommonField.NAME.getEname());
		}else{
			return "";
		}
	}
	
	@Override
	protected void fetchChildren(){
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_CHILDREN.toString());
	    if(!SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(bservice.getSiteId())){
	    	c.addCriteria(CommonField.SITE_ID.getEname(), bservice.getSiteId());
	    	c.addCriteria(SmartParameterName.SORTBY,CommonField.POSITION.getEname());
		    c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		    treeGrid.filterData(c);
	    }
	}

	@Override
	public Record[] getConfirmedThing() {
		return treeGrid.getSelection();
	}

	@Override
	public ViewNameEnum getRightSideView() {
		return ViewNameEnum.ARTICLE;
	}
}
