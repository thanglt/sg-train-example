package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanConfirmSome;
import com.m3958.firstgwt.client.ViewAction;
import com.m3958.firstgwt.client.datasource.XinJianCatDataSource;
import com.m3958.firstgwt.client.datasource.XinJianDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.XinJianCatField;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

@Singleton
public class XinJianLayout extends HasMMRelationGridLayout implements Iview,ICanConfirmSome{
	
	@Inject
	private XinJianDataSource xjds;
	
	
	public Btname[] btStatus0 = new Btname[]{Btname.ADD,Btname.REFRESH};
	public Btname[] btStatus1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.LEAVE_FOLDER,Btname.REFRESH,Btname.CONFIRM};
	public Btname[] btStatus2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.LEAVE_FOLDER,Btname.REFRESH};
	
	public Btname[] btStatus00 = new Btname[]{Btname.ADD,Btname.REFRESH,Btname.SHOW_ALL};
	public Btname[] btStatus11 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.REFRESH,Btname.CONFIRM,Btname.SHOW_ALL,Btname.CONFIRM};
	public Btname[] btStatus22 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH,Btname.SHOW_ALL};


	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setFilterOnKeypress(true);
	    grid.setDataSource(xjds);
	    grid.setAutoFetchData(false);
	    grid.setCanDragRecordsOut(true);

	    grid.addSelectionChangedHandler(getSelectionChangeHandler());

	}
	
	@Override
	public void changeDisplay(ViewAction va, String... paras) {
		if(!initialized){
			initGrid();
			addMember(grid);
			initialized = true;
			firstCall = true;
		}
		
		switch (va) {
		case REMOVE:
			grid.removeSelectedData();
			break;
		case CONTAINER_CLICKED:
			String id = auService.getStringIdPara(paras, 0);
			if(id == null || !id.equals(relationModelId)){
				relationModelId = id;
				fetchGridData();
			}
			break;
		case LEAVE_FOLDER:
			leaveRelation();
			break;
		case SHOW_ALL:
			fetchAll();
			relationModelId = SmartConstants.NONE_EXIST_MODEL_ID_STR;
			break;
		default:
			break;
		}
	}
	
	
	@Override
	public Btname[] getStripStatus() {
		if(aservice.isSuperman()){
			if(grid == null)return btStatus00;
			ListGridRecord[] rs = grid.getSelection();
			if(rs.length == 0){
				return btStatus00;
			}else if(rs.length == 1){
				return btStatus11;
			}else{
				return btStatus22;
			}
		}else{
			if(grid == null)return btStatus0;
			ListGridRecord[] rs = grid.getSelection();
			if(rs.length == 0){
				return btStatus0;
			}else if(rs.length == 1){
				return btStatus1;
			}else{
				return btStatus2;
			}
		}

	}
	
	@Override
	public void fetchGridData(){
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME,getMasterSideProperty());
		c.addCriteria(SmartParameterName.RELATION_MODEL_NAME,getRelationModelName());
		c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.MANY_TO_MANY.toString());
		c.addCriteria(SmartParameterName.RELATION_MODEL_ID, relationModelId);
		c.addCriteria(SmartParameterName.IS_MASTER,isMaster());
		c.addCriteria(CommonField.SITE_ID.getEname(),bservice.getSiteId());
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		grid.filterData(c);
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.XINJIAN;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.XINJIAN_EDIT;
	}


	@Override
	public String getModelName() {
		return XinJianDataSource.className;
	}

	@Override
	public String getRelationModelName() {
		return XinJianCatDataSource.className;
	}

	@Override
	public String getMasterSideProperty() {
		return XinJianCatField.XINJIANS.getEname();
	}


	@Override
	public boolean isMaster() {
		return false;
	}

	@Override
	public String getRelationHint() {
		return XinJianLayout.class.getName();
	}

	@Override
	public Record[] getConfirmedThing() {
		return grid.getSelection();
	}

}
