package com.m3958.firstgwt.client.layout;

import java.util.LinkedHashMap;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.GlobalConstants;
import com.m3958.firstgwt.client.datasource.ShequDataSource;
import com.m3958.firstgwt.client.service.AppUiService;
import com.m3958.firstgwt.client.types.BooleanSearchValue;
import com.m3958.firstgwt.client.types.DateTimeRangeSuffix;
import com.m3958.firstgwt.client.types.HeadTailIncludeType;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.utils.FormValidatorUtils;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

@Singleton
public class LgbSearchLayout extends VLayout{

	private final DynamicForm searchForm = new DynamicForm();
	private final IButton searchButton = new IButton("开始查询");
	private final IButton clearSearchButton = new IButton("清除条件");
	
	private String departmentId;
	

	private LgbLayout lgbLayout;
	
	
	private AppUiService auiService;
	
	
	private ShequDataSource shequDs;
	
//	private LgbSavedQueryFormLayout sqf;
	
	@Inject
	public LgbSearchLayout(AppUiService auiService,ShequDataSource shequDs){
		this.auiService = auiService;
		this.shequDs = shequDs;
		initSearchLayout();
	}
	
	
	private void initSearchLayout() {
		setHeight(490);
		VLayout vl = new VLayout(5);
		vl.addMember(initSearchFormLayout());
		vl.addMember(initSearchBtLayout());
		HLayout space = new HLayout();
		space.setHeight(15);
		vl.addMember(space);
		addMember(vl);
	}
	
	
	private Canvas initSearchFormLayout() {
		HLayout layout = new HLayout();
		searchForm.setWidth100();
		searchForm.setHeight100();
		searchForm.setNumCols(6);
		searchForm.setIsGroup(true);
		searchForm.setGroupTitle("多条件查询");
	    
	    TextItem xmItem = new TextItem(LgbField.XM.getEname(), LgbField.XM.getCname());

	    xmItem.setValidators(FormValidatorUtils.getLengthRangeValidator(2, 20));
	    
	    SelectItem xbItem = new SelectItem(LgbField.XB.getEname(), LgbField.XB.getCname());
	    xbItem.setValueMap(new String[]{"男","女","未知"});
	    
    
	    
	    TextItem srStartItem = new TextItem("sr_start","年龄从：");
	    TextItem srEndItem = new TextItem("sr_end","到：");
	    SelectItem srConfigItem = new SelectItem("sr_htit","配置");
	    srConfigItem.setValueMap(HeadTailIncludeType.getValueMap());
	    srConfigItem.setDefaultToFirstOption(true);
   
	    TextItem sfzItem = new TextItem(LgbField.SFZ.getEname(), LgbField.SFZ.getCname());
	    SelectItem minzuItem = auiService.createSelectItem(LgbField.MINZU.getEname(), LgbField.MINZU.getCname(),GlobalConstants.LimitFieldName.MINZU.getName());
	    
	    TextItem jgItem = new TextItem(LgbField.JG.getEname(), LgbField.JG.getCname());
	    TextItem csdItem = new TextItem(LgbField.CSD.getEname(), LgbField.CSD.getCname());
	    
	    SelectItem jyItem = auiService.createSelectItem(LgbField.JY.getEname(), LgbField.JY.getCname(),GlobalConstants.LimitFieldName.JIAOYU.getName());
	    
	    DateItem rdsjStartItem = new DateItem("rdsj_start", "入党时间从");
	    rdsjStartItem.setUseTextField(true);
	    rdsjStartItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    DateItem rdsjEndItem = new DateItem("rdsj_end", "到");
	    rdsjEndItem.setUseTextField(true);
	    rdsjEndItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    SelectItem rdsjConfigItem = new SelectItem("rdsj_htit","配置");
	    rdsjConfigItem.setValueMap(HeadTailIncludeType.getValueMap());
	    rdsjConfigItem.setDefaultToFirstOption(true);


	    
	    DateItem rwsjStartItem = new DateItem("rwsj_start", "入伍时间从");
	    rwsjStartItem.setUseTextField(true);
	    rwsjStartItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    
	    DateItem rwsjEndItem = new DateItem("rwsj_end", "到");
	    rwsjEndItem.setUseTextField(true);
	    rwsjEndItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    SelectItem rwsjConfigItem = new SelectItem(LgbField.RWSJ.getEname() + DateTimeRangeSuffix.INCLUDE,"配置");
	    rwsjConfigItem.setValueMap(HeadTailIncludeType.getValueMap());
	    rwsjConfigItem.setDefaultToFirstOption(true);


	    
	    DateItem ltxsjStartItem = new DateItem("ltxsj_start", "离退休时间从");
	    ltxsjStartItem.setUseTextField(true);
	    ltxsjStartItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    
	    DateItem ltxsjEndItem = new DateItem("ltxsj_end", "到");
	    ltxsjEndItem.setUseTextField(true);
	    ltxsjEndItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
	    
	    
	    SelectItem ltxsjConfigItem = new SelectItem("ltxsj_htit","配置");
	    ltxsjConfigItem.setValueMap(HeadTailIncludeType.getValueMap());
	    ltxsjConfigItem.setDefaultToFirstOption(true);


	    
	    TextItem ygzdwItem = new TextItem(LgbField.YGZDW.getEname(), LgbField.YGZDW.getCname());
	    TextItem yzwItem = new TextItem(LgbField.YZW.getEname(),LgbField.YZW.getCname());
	    TextItem xzgdwItem = new TextItem(LgbField.XZGDW.getEname(), LgbField.XZGDW.getCname());
	    SelectItem dwxzItem = auiService.createSelectItem(LgbField.DWXZ.getEname(), LgbField.DWXZ.getCname(),GlobalConstants.LimitFieldName.DWXZ.getName());
	    
	    SelectItem xzjbItem = auiService.createSelectItem(LgbField.XZJB.getEname(), LgbField.XZJB.getCname(),GlobalConstants.LimitFieldName.XZJB.getName());
	    
	    SelectItem xsdyItem = auiService.createSelectItem(LgbField.XSDY.getEname(), LgbField.XSDY.getCname(),GlobalConstants.LimitFieldName.XSDY.getName());
		
	    SelectItem gblxItem = auiService.createSelectItem(LgbField.GBLX.getEname(), LgbField.GBLX.getCname(),GlobalConstants.LimitFieldName.GBLX.getName());
		
	    TextItem jbmsItem = new TextItem(LgbField.JBMS.getEname(),LgbField.JBMS.getCname());
	    
	    SelectItem jkzkItem = auiService.createSelectItem(LgbField.JKZK.getEname(), LgbField.JKZK.getCname(),GlobalConstants.LimitFieldName.JKZK.getName());
		
	    SelectItem hyzkItem = auiService.createSelectItem(LgbField.HYZK.getEname(), LgbField.HYZK.getCname(),GlobalConstants.LimitFieldName.HYZK.getName());
		
	    SelectItem jjzkItem = auiService.createSelectItem(LgbField.JJZK.getEname(), LgbField.JJZK.getCname(),GlobalConstants.LimitFieldName.JJZK.getName());

	    SelectItem sszbItem = auiService.createSelectItem(LgbField.SSZB.getEname(), LgbField.SSZB.getCname(),GlobalConstants.LimitFieldName.SSZB.getName());
		
		TextItem pogwItem = new TextItem(LgbField.POGZ.getEname(), LgbField.POGZ.getCname());
		TextItem hjszdItem = new TextItem(LgbField.HJSZD.getEname(), LgbField.HJSZD.getCname());
		
		DateTimeItem swsjStartItem = new DateTimeItem("swsj_start", "死亡时间从");
		swsjStartItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
		
		DateTimeItem swsjEndItem = new DateTimeItem("swsj_end", "到");
		swsjEndItem.setDisplayFormat(DateDisplayFormat.TOJAPANSHORTDATE);
		
	    SelectItem swsjConfigItem = new SelectItem("swsj_htit","配置");
	    swsjConfigItem.setValueMap(HeadTailIncludeType.getValueMap());
	    swsjConfigItem.setDefaultToFirstOption(true);


		
		RadioGroupItem passAwayItem = new RadioGroupItem();
		passAwayItem.setName("passAway_radio");
		passAwayItem.setVertical(false);
		passAwayItem.setTitle("");
		LinkedHashMap<String, String> pamap = new LinkedHashMap<String, String>();
		pamap.put(BooleanSearchValue.YES.toString(), "已故");
		pamap.put(BooleanSearchValue.NO.toString(), "健在");
		pamap.put(BooleanSearchValue.ALL.toString(), "全部");
		passAwayItem.setValueMap(pamap);
		passAwayItem.setValue("全部");
		
		RadioGroupItem isDangyuanItem = new RadioGroupItem();
		isDangyuanItem.setVertical(false);
		isDangyuanItem.setName("rdsj_radio");
		isDangyuanItem.setTitle("");
		
		LinkedHashMap<String, String> dymap = new LinkedHashMap<String, String>();
		pamap.put(BooleanSearchValue.YES.toString(), "党员");
		pamap.put(BooleanSearchValue.NO.toString(), "非党员");
		pamap.put(BooleanSearchValue.ALL.toString(), "全部");
		
		isDangyuanItem.setValueMap(dymap);
		isDangyuanItem.setValue("全部");
		
		
		ComboBoxItem shequItem = new ComboBoxItem(LgbField.SHEQU_ID.getEname(),"所属社区");
		shequItem.setColSpan(6);
		shequItem.setWidth(500);
		
		shequItem.setOptionDataSource(shequDs);
		shequItem.setValueField("id");
		shequItem.setDisplayField("name");
		shequItem.setPickListCriteria(getShequCriteria());

		
	    
		searchForm.setFields(xmItem,jgItem,hjszdItem,sfzItem,yzwItem,xzgdwItem,ygzdwItem,csdItem,pogwItem,jbmsItem,
	    		xbItem,jyItem,minzuItem,dwxzItem,xzjbItem,xsdyItem,gblxItem,hyzkItem,jjzkItem,sszbItem,jkzkItem,
	    		srStartItem,srEndItem,srConfigItem,
	    		rwsjStartItem,rwsjEndItem,rwsjConfigItem,rdsjStartItem,rdsjEndItem,rdsjConfigItem,swsjStartItem,swsjEndItem,swsjConfigItem,ltxsjStartItem,ltxsjEndItem,ltxsjConfigItem,
	    		passAwayItem,isDangyuanItem,shequItem);
		
		searchForm.addKeyPressHandler(new KeyPressHandler(){
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if("Entry".equals(event.getKeyName())){
					searchButton.focus();
				}
				
			}});
	    
	    layout.addMember(searchForm);
	    return layout;
	}
	
	private Criteria getShequCriteria(){
    	Criteria c = new Criteria();
    	c.addCriteria(SmartParameterName.RELATION_MODEL_ID, departmentId);
    	c.addCriteria(SmartParameterName.RELATION_PROPERTY_NAME, "department");
    	c.addCriteria(SmartParameterName.SUB_OPERATION_TYPE, SmartSubOperationName.FETCH_ONE_TO_MANY.toString());
    	return c;
	}
	

	private Canvas initSearchBtLayout() {
	    HLayout layout = new HLayout();
	    layout.setHeight(30);
	    layout.setMembersMargin(5);
	    searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Criteria c = searchForm.getValuesAsCriteria();
				String rid = lgbLayout.getRelationModelId();
				if(rid == null || SmartConstants.NONE_EXIST_MODEL_ID_STR.equals(rid) || rid.isEmpty()){
					SC.warn("请先选择左边的目录！");
					return;
				}
				c.addCriteria(LgbField.DEPARTMENT_IDS.getEname(),"," + rid + ",");
				lgbLayout.asGrid().filterData(c);
				hide();
			}
	    });
	    clearSearchButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				searchForm.clearValues();
				
			}});
	    layout.addMember(searchButton);
	    layout.addMember(clearSearchButton);
	    
		IButton saveQueryButton = new IButton("保存查询条件");
		saveQueryButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
//				sqf.askQueryName();
			}});
		layout.addMember(saveQueryButton);
	    return layout;
	}
	
	public DynamicForm getSearchForm(){
		return searchForm;
	}


	public void setLgbLayout(LgbLayout lgbLayout) {
		this.lgbLayout = lgbLayout;
	}
}
