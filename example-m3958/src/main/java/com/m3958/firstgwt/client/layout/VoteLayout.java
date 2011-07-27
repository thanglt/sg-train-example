package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanRefresh;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.VoteDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.VoteField;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;


@Singleton
public class VoteLayout extends MyTreeGridLayout implements Iview,ICanRefresh{
	
	@Inject
	private VoteDataSource vds;
	
	private String siteId;

	public Btname[] btsNoAdd1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH,Btname.VOTE_HIT};
	public Btname[] simpleBts1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH,Btname.VOTE_HIT};
	@Override
	protected void initTreeGrid() {
		treeGrid = new TreeGrid();
		treeGrid.setDataSource(vds);
		treeGrid.setAutoFetchData(false);
		treeGrid.setTitleField(CommonField.NAME.getEname());
		treeGrid.setShowRoot(false);
		treeGrid.setCanAcceptDroppedRecords(true);
		
	    
        TreeGridField parentField = new TreeGridField(CommonField.PARENT_ID.getEname(),CommonField.PARENT_ID.getCname());
        parentField.setType(ListGridFieldType.INTEGER);
        parentField.setHidden(true);
        
        TreeGridField nameField = new TreeGridField(CommonField.NAME.getEname(),CommonField.NAME.getCname());
        
        TreeGridField startField = new TreeGridField(VoteField.START_DATE.getEname(),VoteField.START_DATE.getCname());
        startField.setType(ListGridFieldType.DATE);
        
        TreeGridField endField = new TreeGridField(VoteField.END_DATE.getEname(),VoteField.END_DATE.getCname());
        startField.setType(ListGridFieldType.DATE);

        
        TreeGridField minSelectField = new TreeGridField(VoteField.MIN_SELECT.getEname(),VoteField.MIN_SELECT.getCname());
        minSelectField.setWidth(20);
        
        TreeGridField maxSelectField = new TreeGridField(VoteField.MAX_SELECT.getEname(),VoteField.MAX_SELECT.getCname());
        maxSelectField.setWidth(20);
        
        TreeGridField positionField = new TreeGridField(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
        positionField.setWidth(20);

        TreeGridField hiddenField = new TreeGridField(CommonField.HIDDEN.getEname(),CommonField.HIDDEN.getCname());
        hiddenField.setWidth(20);
        
        treeGrid.setFields(nameField,auiService.getIdField(false),auiService.getCreatedAtField(false),parentField,auiService.getVersionField(),startField,endField,minSelectField,maxSelectField,positionField,hiddenField);
        treeGrid.addSelectionChangedHandler(getSelectionChangeHandler());
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
			break;
		default:
			if(siteId == null || (!bservice.getSiteId().equals(siteId) && !firstCall)){
				siteId = bservice.getSiteId();
				firstCall = false;
				selectedRecordId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
				fetchChildren();
			}
			break;
		}
	}
	
	@Override
	protected void fetchChildren(){
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_CHILDREN.toString());
	    if(!SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(bservice.getSiteId())){
	    	c.addCriteria(CommonField.SITE_ID.getEname(), bservice.getSiteId());
	    }
	    c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
	    treeGrid.filterData(c);
	}
	
	@Override
	public Btname[] getStripStatus() {
		if(treeGrid == null)return simpleBts0;
		ListGridRecord[] rs = treeGrid.getSelection();
		if(rs.length == 0){
			return simpleBts0;
		}else if(rs.length == 1){
			return simpleBts1;
		}else{
			return simpleBts2;
		}
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.VOTE;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		if(btname == Btname.VOTE_HIT){
			return ViewNameEnum.VOTEHIT;
		}else{
			return ViewNameEnum.VOTE_EDIT;
		}
		
	}

	@Override
	public String getModelName(){
		return VoteDataSource.className;
	}


	@Override
	public ViewNameEnum getRightSideView() {
		return null;
	}
	




}
