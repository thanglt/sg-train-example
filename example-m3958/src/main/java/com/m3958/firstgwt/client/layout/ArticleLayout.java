package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.ICanConfirmSome;
import com.m3958.firstgwt.client.datasource.ArticleDataSource;
import com.m3958.firstgwt.client.datasource.SectionDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.ArticleField;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SectionField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

@Singleton
public class ArticleLayout extends HasMMRelationGridLayout implements Iview,ICanConfirmSome{
	
	@Inject
	private ArticleDataSource ads;

	
	public Btname[] btStatus0 = new Btname[]{Btname.ADD,Btname.REFRESH,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	public Btname[] btStatus1 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.EDIT,Btname.LEAVE_FOLDER,Btname.REFRESH,Btname.CONFIRM,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	public Btname[] btStatus2 = new Btname[]{Btname.ADD,Btname.REMOVE,Btname.LEAVE_FOLDER,Btname.REFRESH,Btname.MINE,Btname.OTHERS,Btname.GROUPS};
	
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
	    grid.setShowFilterEditor(true);
	    grid.setDataSource(ads);
	    grid.setAutoFetchData(false);
	    grid.setCanDragRecordsOut(true);
	    grid.setEmptyMessage("请点击右边的目录来显示目录下的文章！");
	    
	    grid.setDataPageSize(50);
	    
	    ListGridField titleField = new ListGridField(ArticleField.TITLE.getEname(),ArticleField.TITLE.getCname());
	    ListGridField flagField = new ListGridField(ArticleField.FLAG.getEname(),ArticleField.FLAG.getCname());
	    ListGridField publishedAtField = new ListGridField(ArticleField.PUBLISHED_AT.getEname(),ArticleField.PUBLISHED_AT.getCname());
	    ListGridField auditField = auiService.getAuditField();
	    auditField.setCanEdit(true);
	    
	    grid.setFields(titleField,auiService.getCreatedAtField(false),auiService.getIdField(false),auiService.getVersionField(),auditField,publishedAtField,flagField);
	    grid.addSelectionChangedHandler(getSelectionChangeHandler());

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
	public void fetchMine(){
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.FETCH_OWNER,true);
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		c.addCriteria(CommonField.SITE_ID.getEname(),bservice.getSiteId());
		grid.filterData(c);
	}
	
	@Override
	public void fetchOthers(){
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.NOT_CREATOR_HAS_PERMISSION.toString());
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		c.addCriteria(CommonField.SITE_ID.getEname(),bservice.getSiteId());
		grid.filterData(c);
	}
	
	@Override
	public void fetchGroups(){
		Criteria c = new Criteria();
		c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.NOT_CREATOR_HAS_GPERMISSION.toString());
		c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
		c.addCriteria(CommonField.SITE_ID.getEname(),bservice.getSiteId());
		grid.filterData(c);
	}
	
	@Override
	protected void fetchAll(){
		if(aservice.isSuperman()){
			Criteria c = new Criteria();
			c.addCriteria(CommonField.TIMEZONE.getEname(),bservice.getTimeZone());
			c.addCriteria(CommonField.SITE_ID.getEname(),bservice.getSiteId());
			grid.filterData(c);
		}
	}


	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.ARTICLE;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return ViewNameEnum.ARTICLE_EDIT;
	}


	@Override
	public String getModelName() {
		return ArticleDataSource.className;
	}

	@Override
	public String getRelationModelName() {
		return SectionDataSource.className;
	}

	@Override
	public String getMasterSideProperty() {
		return SectionField.ARTICLES.getEname();
	}


	@Override
	public boolean isMaster() {
		return false;
	}

	@Override
	public String getRelationHint() {
		return ArticleLayout.class.getName();
	}

	@Override
	public Record[] getConfirmedThing() {
		return grid.getSelection();
	}

}
