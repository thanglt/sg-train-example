package com.m3958.firstgwt.client.layout;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.FamilyDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.ViewNameEnum;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

@Singleton
public class FamilyEditLayout extends HasMMRelationEditLayout implements Iview{
	
	@Inject
	private FamilyDataSource fds;
	
	@Override
	protected void initModelForm() {
		modelForm = new DynamicForm();
		modelForm.setWidth100();
		modelForm.setHeight(350);
	    modelForm.setNumCols(4);
	    modelForm.setIsGroup(true);
	    modelForm.setGroupTitle(constants.cwFormTitle());
	    
	    modelForm.setDataSource(fds);
	    
		TextAreaItem bzItem = new TextAreaItem("bz","备注");
		bzItem.setHeight(50);
	    TextItem xmItem = new TextItem("xm","姓名");
	    xmItem.setRequired(true);
	    xmItem.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 40));
	    TextItem gxItem = new TextItem("gx","关系");
	    
	    SelectItem xbItem = new SelectItem("xb","性别");
	    xbItem.setRequired(true);
	    xbItem.setValueMap("男","女","未知");
	    
	    DateItem srItem = new DateItem("birthday","生日");
	    srItem.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATE);
	    srItem.setUseTextField(true);
	    srItem.setUseMask(true);
	    
	    TextItem dizhiItem = new TextItem("dizhi","地址");
	    TextItem shoujiItem = new TextItem("shouji","手机");
	    TextItem dianhuaItem = new TextItem("dianhua","电话");
	    TextItem zzmmItem = new TextItem("zzmm","政治面貌");
	    TextItem jkzkItem = new TextItem("jkzk","健康状况");
	    
	    modelForm.setFields(auiService.getIdHiddenItem(),auiService.getVersionHiddenItem(),xmItem,gxItem,xbItem,srItem,dizhiItem,dianhuaItem,shoujiItem,zzmmItem,jkzkItem,bzItem,auiService.getCreatedAtHiddenItem());
	}


	@Override
	public String[] getParas() {
		return null;
	}

	@Override
	public Btname[] getStripStatus() {
		return null;
	}

	@Override
	public ViewNameEnum getViewName() {
		return ViewNameEnum.FAMILY_EDIT;
	}

	@Override
	public ViewNameEnum nextViewName(Btname btname) {
		return null;
	}


	@Override
	protected String getModelName() {
		return FamilyDataSource.className;
	}


	@Override
	protected void initOtherWidget() {
		
	}


}
