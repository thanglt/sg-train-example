package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanRefresh;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.CommentDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommentField;
import com.m3958.firstgwt.client.types.CommonField;
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
public class CommentLayout extends MyTreeGridLayout implements Iview,ICanRefresh{
	
	@Inject
	private CommentDataSource cds;
	
	private String siteId;

	
	@Override
	protected void initTreeGrid() {
		treeGrid = new TreeGrid();
		treeGrid.setDataSource(cds);
		treeGrid.setAutoFetchData(false);
		treeGrid.setTitleField(CommentField.MESSAGE.getEname());
		treeGrid.setShowRoot(false);
		treeGrid.setCanAcceptDroppedRecords(true);
		SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
		treeGrid.setSort(ssf);
	    
        TreeGridField parentField = new TreeGridField(CommonField.PARENT_ID.getEname(),CommonField.PARENT_ID.getCname());
        parentField.setType(ListGridFieldType.INTEGER);
        parentField.setHidden(true);
        
        TreeGridField titleField = new TreeGridField(CommentField.TITLE.getEname(),CommentField.TITLE.getCname());
        
        TreeGridField messageField = new TreeGridField(CommentField.MESSAGE.getEname(),CommentField.MESSAGE.getCname());
        
        TreeGridField emailField = new TreeGridField(CommentField.EMAIL.getEname(),CommentField.EMAIL.getCname());
        TreeGridField ipField = new TreeGridField(CommonField.IP.getEname(),CommonField.IP.getCname());
        TreeGridField nickNameField = new TreeGridField(CommentField.NICKNAME.getEname(),CommentField.NICKNAME.getCname());
        TreeGridField followNumField = new TreeGridField(CommentField.FOLLOWNUM.getEname(),CommentField.FOLLOWNUM.getCname());
        
        treeGrid.setFields(titleField,messageField,auiService.getIdField(true),auiService.getCreatedAtField(true),parentField,auiService.getVersionField(),emailField,nickNameField,followNumField,ipField,auiService.getAuditField());
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
	    	c.addCriteria(SmartParameterName.SORTBY,"-" + CommonField.CREATED_AT.getEname());
	    	c.addCriteria(CommonField.SITE_ID.getEname(), bservice.getSiteId());
	    }
	    c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
	    treeGrid.filterData(c);
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.COMMENT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.COMMENT_EDIT;
	}

	@Override
	public String getModelName(){
		return CommentDataSource.className;
	}


	@Override
	public ViewNameEnum getRightSideView() {
		return null;
	}
}
