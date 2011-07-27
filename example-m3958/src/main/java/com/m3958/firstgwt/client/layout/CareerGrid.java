package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.CareerDataSource;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.types.CareerField;
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
public class CareerGrid extends LgbRelationGridImpl implements ILgbRelationGrid{
	
	@Inject
	private CareerDataSource cds;

	@Override
	protected void initGrid() {
		grid.setShowRowNumbers(true);
		grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CareerField.START.getEname(),SortDirection.ASCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setDataSource(cds);
	    grid.setAutoFetchData(false);

	    ListGridField startField = new ListGridField(CareerField.START.getEname(),CareerField.START.getCname());
	    startField.setType(ListGridFieldType.DATE);
	    startField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
	    ListGridField endField = new ListGridField(CareerField.END.getEname(),CareerField.END.getCname());
	    endField.setType(ListGridFieldType.DATE);
	    endField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
	    ListGridField danweiField = new ListGridField(CareerField.DANWEI.getEname(),CareerField.DANWEI.getCname());
	    ListGridField zhiwuField = new ListGridField(CareerField.ZHIWU.getEname(),CareerField.ZHIWU.getCname());
	    ListGridField bzField = new ListGridField(CareerField.BZ.getEname(),CareerField.BZ.getCname());

	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),startField,endField,danweiField,zhiwuField,bzField,auiService.getCreatedAtField(false));
	}


	@Override
	public void fetchData(String lgbId) {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, lgbId);
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, LgbField.CAREERS.getEname());
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME, LgbDataSource.className);
	    c.addCriteria(SmartParameterName.IS_MASTER, "0");
	    grid.filterData(c);
	}
	
	@Override
	public ViewNameEnum getNextView() {
		return ViewNameEnum.CAREER_EDIT;
	}


	@Override
	public String getLbname() {
		return "工作经历：";
	}
	
}
