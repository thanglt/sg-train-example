package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.datasource.StepCareerDataSource;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGridField;

@Singleton
public class StepCareerGrid extends LgbRelationGridImpl implements ILgbRelationGrid{
	
	@Inject
	private StepCareerDataSource scds;
	
	@Override
	protected void initGrid() {
		grid.setShowRowNumbers(true);
		grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    
	    grid.setAutoFetchData(false);

	    grid.setDataSource(scds);

	    ListGridField bzField = new ListGridField("bz","备注");
	    

	    ListGridField startField = new ListGridField("start","开始日期");
	    startField.setType(ListGridFieldType.DATE);
	    startField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
	    ListGridField endField = new ListGridField("end","结束日期");
	    endField.setType(ListGridFieldType.DATE);
	    endField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    ListGridField zysjField = new ListGridField("zysj","主要事迹");
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),startField,endField,zysjField,bzField,auiService.getCreatedAtField(false));
	}


	@Override
	public void fetchData(String lgbId) {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, lgbId);
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, LgbField.STEP_CAREERS.getEname());
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME, LgbDataSource.className);
	    c.addCriteria(SmartParameterName.IS_MASTER, "0");
	    grid.filterData(c);
	}


	@Override
	public ViewNameEnum getNextView() {
		return ViewNameEnum.STEP_CAREER_EDIT;
	}


	@Override
	public String getLbname() {
		return "退休后经历：";
	}
}
