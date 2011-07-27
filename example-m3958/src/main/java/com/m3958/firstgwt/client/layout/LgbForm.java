package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.GlobalConstants;
import com.m3958.firstgwt.client.datasource.LgbDataSource;
import com.m3958.firstgwt.client.datasource.ShequDataSource;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.service.AppUtilService;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.BooleanItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class LgbForm extends DynamicForm{
	
	private String departmentId;
	
	private ComboBoxItem shequItem;
	
	
	@Inject
	public LgbForm(AppUtilService auService,AppUiService auiService,LgbDataSource lds,ShequDataSource sds){
		setWidth100();
		setHeight100();
	    setNumCols(6);
	    setDataSource(lds);
	    HiddenItem attachmentIdsItem = new HiddenItem(SmartParameterName.ATTACHMENTIDS);

	    TextItem xmItem = new TextItem(LgbField.XM.getEname(), LgbField.XM.getCname());
	    xmItem.setRequired(true);
	    xmItem.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 20));
	    SelectItem xbItem = new SelectItem(LgbField.XB.getEname(), LgbField.XB.getCname());
	    xbItem.setValueMap(new String[]{"男","女","未知"});
	    xbItem.setRequired(true);
	    DateItem srItem = new DateItem(LgbField.SR.getEname(), LgbField.SR.getCname());
	    srItem.setUseTextField(true);
	    srItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    TextItem sfzItem = new TextItem(LgbField.SFZ.getEname(), LgbField.SFZ.getCname());
	    SelectItem minzuItem = auiService.createSelectItem(LgbField.MINZU.getEname(), LgbField.MINZU.getCname(),GlobalConstants.LimitFieldName.MINZU.getName());
	    TextItem jgItem = new TextItem(LgbField.JG.getEname(), LgbField.JG.getCname());
	    TextItem csdItem = new TextItem(LgbField.CSD.getEname(), LgbField.CSD.getCname());
	    SelectItem jyItem = auiService.createSelectItem(LgbField.JY.getEname(), LgbField.JY.getCname(), GlobalConstants.LimitFieldName.JIAOYU.getName());
	    DateItem rdsjItem = new DateItem(LgbField.RDSJ.getEname(), LgbField.RDSJ.getCname());
	    rdsjItem.setUseTextField(true);
	    rdsjItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    DateItem rwsjItem = new DateItem(LgbField.RWSJ.getEname(), LgbField.RWSJ.getCname());
	    rwsjItem.setUseTextField(true);
	    rwsjItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    DateItem ltxsjItem = new DateItem(LgbField.LTXSJ.getEname(), LgbField.LTXSJ.getCname());
	    ltxsjItem.setUseTextField(true);
	    ltxsjItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    TextItem ygzdwItem = new TextItem(LgbField.YGZDW.getEname(), LgbField.YGZDW.getCname());
	    TextItem yzwItem = new TextItem(LgbField.YZW.getEname(),LgbField.YZW.getCname());
	    TextItem xzgdwItem = new TextItem(LgbField.XZGDW.getEname(), LgbField.XZGDW.getCname());
	    SelectItem dwxzItem = auiService.createSelectItem(LgbField.DWXZ.getEname(), LgbField.DWXZ.getCname(),GlobalConstants.LimitFieldName.DWXZ.getName());
	    SelectItem xzjbItem = auiService.createSelectItem(LgbField.XZJB.getEname(), LgbField.XZJB.getCname(),GlobalConstants.LimitFieldName.XZJB.getName());
	    SelectItem xsdyItem = auiService.createSelectItem(LgbField.XSDY.getEname(), LgbField.XSDY.getCname(),GlobalConstants.LimitFieldName.XSDY.getName());
	    SelectItem gblxItem = auiService.createSelectItem(LgbField.GBLX.getEname(), LgbField.GBLX.getCname(),GlobalConstants.LimitFieldName.GBLX.getName());
	    SelectItem jkzkItem = auiService.createSelectItem(LgbField.JKZK.getEname(), "",GlobalConstants.LimitFieldName.JKZK.getName());
		jkzkItem.setShowTitle(false);
	    SelectItem hyzkItem = auiService.createSelectItem(LgbField.HYZK.getEname(), LgbField.HYZK.getCname(),GlobalConstants.LimitFieldName.HYZK.getName());
	    SelectItem jjzkItem = auiService.createSelectItem(LgbField.JJZK.getEname(), LgbField.JJZK.getCname(),GlobalConstants.LimitFieldName.JJZK.getName());
	    SelectItem sszbItem = auiService.createSelectItem(LgbField.SSZB.getEname(), LgbField.SSZB.getCname(),GlobalConstants.LimitFieldName.SSZB.getName());
		TextItem pogwItem = new TextItem(LgbField.POGZ.getEname(), LgbField.POGZ.getCname());
		TextItem hjszdItem = new TextItem(LgbField.HJSZD.getEname(), LgbField.HJSZD.getCname());
		IntegerItem paixuItem = new IntegerItem( );
		paixuItem.setName(LgbField.PAIXU.getEname());
		paixuItem.setTitle(LgbField.PAIXU.getCname());
		DateTimeItem swsjItem = new DateTimeItem(LgbField.SWSJ.getEname(), LgbField.SWSJ.getCname());
		swsjItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
		TextItem bzItem = new TextItem(LgbField.BZ.getEname(), LgbField.BZ.getCname());
		bzItem.setColSpan(4);
		bzItem.setWidth(500);
		TextItem jbmsItem = new TextItem(LgbField.JBMS.getEname(), LgbField.JBMS.getCname());
		//不包括title的占位。
		jbmsItem.setColSpan(4);
		jbmsItem.setWidth(500);
		BooleanItem passAwayItem = new BooleanItem();
		passAwayItem.setName(LgbField.PASSAWAY.getEname());
		passAwayItem.setTitle(LgbField.PASSAWAY.getCname());
		HiddenItem departmentIdsItem = new HiddenItem("departmentIds");
		shequItem = new ComboBoxItem(LgbField.SHEQU_ID.getEname(),"所属社区");
		shequItem.setColSpan(6);
		shequItem.setWidth(500);
		shequItem.setOptionDataSource(sds);
		shequItem.setValueField("id");
		shequItem.setDisplayField("name");
	    setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),xmItem,xbItem,srItem,sfzItem,
	    		minzuItem,jgItem,csdItem,jyItem,hjszdItem,rwsjItem,rdsjItem,ltxsjItem,
	    		ygzdwItem,yzwItem,xzgdwItem,dwxzItem,xzjbItem,xsdyItem,
	    		gblxItem,hyzkItem,jjzkItem,sszbItem,pogwItem,
	    		swsjItem,departmentIdsItem,auiService.getCreatedAtHiddenItem(),shequItem,
	    		jbmsItem,jkzkItem,bzItem,paixuItem,passAwayItem,attachmentIdsItem);
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
//		setValue(LgbField.DEPARTMENT_ID.getEname(), departmentId);
		shequItem.setPickListCriteria(getShequCriteria());
		shequItem.fetchData();
	}

	private Criteria getShequCriteria(){
    	Criteria c = new Criteria();
    	c.addCriteria(SmartParameterName.RELATION_MODEL_ID, departmentId);
    	c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, "department");
    	c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
    	return c;
	}
}
