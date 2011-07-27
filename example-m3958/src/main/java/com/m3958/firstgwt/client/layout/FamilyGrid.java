package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.FamilyDataSource;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
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
public class FamilyGrid extends LgbRelationGridImpl implements ILgbRelationGrid{
	
	@Inject
	private FamilyDataSource fds;

	@Override
	protected void initGrid() {
		grid.setShowRowNumbers(true);
		grid.setAutoFetchTextMatchStyle(TextMatchStyle.SUBSTRING);
	    SortSpecifier ssf[] = {new SortSpecifier(CommonField.CREATED_AT.getEname(),SortDirection.DESCENDING)};
	    grid.setInitialSort(ssf);
	    grid.setDataSource(fds);
	    grid.setAutoFetchData(false);
	    
	    ListGridField bzField = new ListGridField("bz","备注");
	    bzField.setHidden(true);
	    
	    ListGridField xmField = new ListGridField("xm","姓名");
	    ListGridField gxField = new ListGridField("gx","关系");
	    ListGridField xbField = new ListGridField("xb","性别");
	    
	    xbField.setValueMap("男","女","未知");
	    ListGridField srField = new ListGridField("birthday","生日");
	    srField.setType(ListGridFieldType.DATE);
	    srField.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
	    srField.setHidden(true);
	    
	    ListGridField dizhiField = new ListGridField("dizhi","地址");
	    ListGridField shoujiField = new ListGridField("shouji","手机");
	    ListGridField dianhuaField = new ListGridField("dianhua","电话");
	    ListGridField zzmmField = new ListGridField("zzmm","政治面貌");
	    zzmmField.setHidden(true);
	    ListGridField jkzkField = new ListGridField("jkzk","健康状况");
	    jkzkField.setHidden(true);
	    grid.setFields(auiService.getIdField(true),auiService.getVersionField(),xmField,gxField,shoujiField,dianhuaField,dizhiField,srField,xbField,zzmmField,jkzkField,bzField,auiService.getCreatedAtField(false));
	}

	@Override
	public void fetchData(String lgbId) {
	    Criteria c = new Criteria();
	    c.addCriteria(SmartParameterName.RELATION_MODEL_ID, lgbId);
	    c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
	    c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, LgbField.FAMILIES.getEname());
	    c.addCriteria(SmartParameterName.RELATION_MODEL_NAME, LgbDataSource.className);
	    c.addCriteria(SmartParameterName.IS_MASTER, "0");
	    grid.filterData(c);
	}
	
	@Override
	public ViewNameEnum getNextView() {
		return ViewNameEnum.FAMILY_EDIT;
	}


	@Override
	public String getLbname() {
		return "家庭成员：";
	}

}
