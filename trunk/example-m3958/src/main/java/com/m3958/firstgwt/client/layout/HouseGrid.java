package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.HouseDataSource;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.HouseField;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGridField;


@Singleton
public class HouseGrid extends LgbRelationGridImpl implements ILgbRelationGrid{
	
	@Inject
	private HouseDataSource hds;

	
	
	@Override
	protected void initGrid() {
		grid.setShowRowNumbers(true);
		grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    
	    grid.setDataSource(hds);
	    grid.setAutoFetchData(false);


	    ListGridField bzField = new ListGridField(HouseField.BZ.getEname(),HouseField.BZ.getCname());
	    ListGridField zfxzField = new ListGridField(HouseField.ZFXZ.getEname(),HouseField.ZFXZ.getCname());
	    
	    ListGridField areaField = new ListGridField(HouseField.AREA.getEname(),HouseField.AREA.getCname());
	    ListGridField structureField = new ListGridField(HouseField.STRUCTURE.getEname(),HouseField.STRUCTURE.getCname());

	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),zfxzField,areaField,structureField,bzField,auiService.getCreatedAtField(false));
	}

	@Override
	public void fetchData(String lgbId) {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, lgbId);
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, LgbField.HOUSES.getEname());
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME, LgbDataSource.className);
	    c.addCriteria(SmartParameterName.IS_MASTER, "0");
	    grid.filterData(c);
	}
	
	@Override
	public ViewNameEnum getNextView() {
		return ViewNameEnum.HOUSE_EDIT;
	}


	@Override
	public String getLbname() {
		return "住房情况：";
	}
	
}
