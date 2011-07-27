package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.datasource.RewardDataSource;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.RewardField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGridField;


@Singleton
public class RewardGrid extends LgbRelationGridImpl implements ILgbRelationGrid{
	
	@Inject
	private RewardDataSource rds;
	
	@Override
	protected void initGrid() {
		grid.setShowRowNumbers(true);
		grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setAutoFetchData(false);
  
	    grid.setDataSource(rds);

	    ListGridField bzField = new ListGridField(RewardField.BZ.getEname(),RewardField.BZ.getCname());
	    
	    
	    ListGridField jlqcField = new ListGridField(RewardField.JLQK.getEname(),RewardField.JLQK.getCname());
	    ListGridField cfqcField = new ListGridField(RewardField.CFQK.getEname(),RewardField.CFQK.getCname());

	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),jlqcField,cfqcField,bzField,auiService.getCreatedAtField(false));
	    
	}

	@Override
	public void fetchData(String lgbId) {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, lgbId);
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, LgbField.REWARDS.getEname());
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME, LgbDataSource.className);
	    c.addCriteria(SmartParameterName.IS_MASTER, "0");
	    grid.filterData(c);
	}
	
	@Override
	public ViewNameEnum getNextView() {
		return ViewNameEnum.REWARD_EDIT;
	}


	@Override
	public String getLbname() {
		return "奖惩记录：";
	}

}
