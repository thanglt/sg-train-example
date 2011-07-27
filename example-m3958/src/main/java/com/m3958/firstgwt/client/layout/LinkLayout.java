package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanRefresh;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.LinkDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.LinkField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;


@Singleton
public class LinkLayout extends MyTreeGridLayout implements Iview,ICanRefresh{
	
	@Inject
	private LinkDataSource lds;
	
	private String siteId;

	
	@Override
	protected void initTreeGrid() {
		treeGrid = new TreeGrid();
		treeGrid.setDataSource(lds);
		treeGrid.setAutoFetchData(false);
		treeGrid.setTitleField(CommonField.NAME.getEname());
		treeGrid.setShowRoot(false);
		treeGrid.setCanAcceptDroppedRecords(true);
		SortSpecifier ssf[] = {new SortSpecifier(CommonField.POSITION.getEname(),SortDirection.ASCENDING)};
		treeGrid.setSort(ssf);
	    
        TreeGridField parentField = new TreeGridField(CommonField.PARENT_ID.getEname(),CommonField.PARENT_ID.getCname());
        parentField.setType(ListGridFieldType.INTEGER);
        parentField.setHidden(true);
        
        TreeGridField nameField = new TreeGridField(CommonField.NAME.getEname(),CommonField.NAME.getCname());
        
        TreeGridField linkToField = new TreeGridField(LinkField.LINK_TO.getEname(),LinkField.LINK_TO.getCname());
        TreeGridField lurlField = new TreeGridField(LinkField.LURL.getEname(),LinkField.LURL.getCname());
        TreeGridField tplNameField = new TreeGridField(LinkField.TPL_NAME.getEname(),LinkField.TPL_NAME.getCname());
        TreeGridField modelIdField = new TreeGridField(LinkField.MODEL_ID.getEname(),LinkField.MODEL_ID.getCname());
        modelIdField.setWidth(50);
        
        TreeGridField positionField = new TreeGridField(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
        positionField.setWidth(20);

        TreeGridField hiddenField = new TreeGridField(CommonField.HIDDEN.getEname(),CommonField.HIDDEN.getCname());
        hiddenField.setWidth(20);
        
        treeGrid.setFields(nameField,auiService.getIdField(true),auiService.getCreatedAtField(false),parentField,auiService.getVersionField(),linkToField,lurlField,tplNameField,modelIdField,positionField,hiddenField);
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
				firstCall = false;
				siteId = bservice.getSiteId();
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
	    	c.addCriteria(SmartParameterName.SORTBY,CommonField.POSITION.getEname());
	    	c.addCriteria(CommonField.SITE_ID.getEname(), bservice.getSiteId());
	    }
	    c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
	    treeGrid.filterData(c);
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.LINK;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.LINK_EDIT;
	}

	@Override
	public String getModelName(){
		return LinkDataSource.className;
	}


	@Override
	public ViewNameEnum getRightSideView() {
		return null;
	}
	




}
