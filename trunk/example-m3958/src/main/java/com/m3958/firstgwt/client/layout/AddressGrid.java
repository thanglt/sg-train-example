package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.AddressDataSource;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.types.AddressField;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.widgets.grid.ListGridField;


@Singleton
public class AddressGrid extends LgbRelationGridImpl implements ILgbRelationGrid{

	@Inject
	private AddressDataSource ads;

	
	@Override
	protected void initGrid() {
		grid.setWidth100();
		grid.setHeight100();
		grid.setShowRowNumbers(true);
		grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setAutoFetchData(false);
	    
	    grid.setDataSource(ads);

	    ListGridField bzField = new ListGridField(AddressField.BZ.getEname(),AddressField.BZ.getCname());

	    ListGridField dizhiField = new ListGridField(AddressField.DIZHI.getEname(),AddressField.DIZHI.getCname());
	    ListGridField dianhuaField = new ListGridField(AddressField.DIANHUA.getEname(),AddressField.DIANHUA.getCname());
	    ListGridField shoujiField = new ListGridField(AddressField.SHOUJI.getEname(),AddressField.SHOUJI.getCname());
	    ListGridField mainAddressField = new ListGridField(AddressField.MAIN_ADDRESS.getEname(),AddressField.MAIN_ADDRESS.getCname());
	    mainAddressField.setType(ListGridFieldType.BOOLEAN);
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),dizhiField,dianhuaField,shoujiField,bzField,mainAddressField,auiService.getCreatedAtField(false));
	}

	@Override
	public void fetchData(String lgbId) {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, lgbId);
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, LgbField.ADDRESSES.getEname());
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME, LgbDataSource.className);
	    c.addCriteria(SmartParameterName.IS_MASTER, "0");
	    grid.filterData(c);
	}
	
	@Override
	public ViewNameEnum getNextView() {
		return ViewNameEnum.ADDRESS_EDIT;
	}

	@Override
	public String getLbname() {
		return "联系方式：";
	}
}
