package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanRefresh;
import com.m3958.firstgwt.client.IHasRole;
import com.m3958.firstgwt.client.datasource.GroupDataSource;
import com.m3958.firstgwt.client.datasource.UserDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.GroupField;
import com.m3958.firstgwt.client.types.UserField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

@Singleton
public class UserLayout extends HasMMRelationGridLayout implements Iview,IHasRole,ICanRefresh{
	
	@Inject
	private UserDataSource uds;
	
	public Btname[] btStatus0 = new Btname[]{Btname.ADD,Btname.REFRESH,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	public Btname[] btStatus1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.LEAVE_FOLDER,Btname.REFRESH,Btname.ROLE};
	public Btname[] btStatus2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	
	public Btname[] btStatus00 = new Btname[]{Btname.ADD,Btname.REFRESH,Btname.SHOW_ALL,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	public Btname[] btStatus11 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.SHOW_ALL,Btname.LEAVE_FOLDER,Btname.REFRESH,Btname.ROLE};
	public Btname[] btStatus22 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.REFRESH,Btname.SHOW_ALL};
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
		grid.setDataSource(uds);
	    grid.setShowRowNumbers(true);
	    grid.setShowFilterEditor(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setAutoFetchData(false);
	    grid.setCanDragRecordsOut(true);
	    
	    ListGridField loginNameField = new ListGridField(UserField.LOGIN_NAME.getEname(),UserField.LOGIN_NAME.getCname());
	    ListGridField emailField = new ListGridField(UserField.EMAIL.getEname(),UserField.EMAIL.getCname());
	    ListGridField openIdField = new ListGridField(UserField.OPENID.getEname(),UserField.OPENID.getCname());
	    ListGridField mobileField = new ListGridField(UserField.MOBILE.getEname(),UserField.MOBILE.getCname());
	    ListGridField birthDayField = new ListGridField(UserField.BIRTHDAY.getEname(),UserField.BIRTHDAY.getCname());
	    ListGridField passwordField = new ListGridField(UserField.PASSWORD.getEname(),UserField.PASSWORD.getCname());
	    ListGridField fcUserField = new ListGridField(UserField.FCUSER.getEname(),UserField.FCUSER.getCname());
	    
	    ListGridField menuLevelField = new ListGridField("menuLevel");
	    menuLevelField.setHidden(true);
	    menuLevelField.setCanFilter(false);
	    
	    passwordField.setHidden(true);
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),loginNameField,emailField,menuLevelField,fcUserField,openIdField,mobileField,birthDayField,auiService.getCreatedAtField(false),passwordField);
	    
	    grid.addSelectionChangedHandler(getSelectionChangeHandler());

	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.USER;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.USER_EDIT;
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
	
	
	public String getOid(){
		return selectedRecordId;
	}
	
	public String getModelName(){
		return UserDataSource.className;
	}
	
	public String getOname(){
		if(grid.getSelectedRecord() != null){
			return grid.getSelectedRecord().getAttributeAsString(UserField.LOGIN_NAME.getEname());
		}else{
			return "";
		}
	}

	@Override
	public void fetchGroups() {
	}

	@Override
	public void fetchMine() {
	}

	@Override
	public void fetchOthers() {
	}


	@Override
	public void refreshContent() {
		
	}


	@Override
	public String getRelationModelName() {
		return GroupDataSource.className;
	}


	@Override
	public String getMasterSideProperty() {
		return GroupField.USERS.getEname();
	}


	@Override
	public boolean isMaster() {
		return false;
	}


	@Override
	public String getRelationHint() {
		return UserLayout.class.getName();
	}


}
