package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.VoteDataSource;
import com.m3958.firstgwt.client.datasource.VoteHitDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.types.VoteField;
import com.m3958.firstgwt.client.types.VoteHitField;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class VoteHitLayout extends HasOMRelationGridLayout implements Iview{
	
	@Inject
	private VoteHitDataSource vhds;
	
	@Override
	protected void initGrid() {
		grid = new ListGrid();
	    grid.setWidth100();
	    grid.setHeight100();
	    grid.setShowRowNumbers(true);
	    grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);

	    grid.setDataSource(vhds);
	    grid.setAutoFetchData(false);

        ListGridField nameField = new ListGridField(CommonField.NAME.getEname(),CommonField.NAME.getCname());
        
        ListGridField refererField = new ListGridField(VoteHitField.REFERER.getEname(),VoteHitField.REFERER.getCname());
        
        ListGridField ipField = new ListGridField(VoteHitField.VOTE_IP.getEname(),VoteHitField.VOTE_IP.getCname());
        
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),auiService.getCreatedAtField(false),nameField,refererField,ipField);
	    grid.addSelectionChangedHandler(getSelectionChangeHandler());
	}
	

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.VOTEHIT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
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
	public void fetchAll() {
		
	}

	@Override
	public String getModelName() {
		return VoteHitDataSource.className;
	}

	@Override
	public String getRelationModelName() {
		return VoteDataSource.className;
	}

	@Override
	public String getMasterSideProperty() {
		return VoteField.VOTEHITS.getEname();
	}


	@Override
	public boolean isMaster() {
		return false;
	}

	@Override
	public String getRelationHint() {
		return VoteHitLayout.class.getName();
	}


}
