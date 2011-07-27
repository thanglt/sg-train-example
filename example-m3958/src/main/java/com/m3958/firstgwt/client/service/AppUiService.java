package com.m3958.firstgwt.client.service;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.FieldEnumDataSource;
import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.FieldEnumField;
import com.m3958.firstgwt.client.types.ProtectLevel;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TextMatchStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGridField;


@Singleton
public class AppUiService {

	
	@Inject
	private FieldEnumDataSource fieldDs;
	
	public Btname[] getBtstatus(Btname[] status,Btname...extraBtnames){
		Btname[] btns = new Btname[status.length + extraBtnames.length];
		int i=0;
		for(;i<status.length;i++){
			btns[i] = status[i];
		}
		for(int j=0;j<extraBtnames.length;j++){
			btns[i+j] = extraBtnames[j];
		}
		return btns;
	}
	
	public SelectItem getProtectLevelItem(){
	    SelectItem protectLevelItem = new SelectItem(CommonField.PROTECT_LEVEL.getEname(),CommonField.PROTECT_LEVEL.getCname());
	    protectLevelItem.setValueMap(ProtectLevel.PERMISSION,ProtectLevel.LOGIN,ProtectLevel.PUBLIC);
	    return protectLevelItem;
	}
	
	public Btname[] getBtstatusWithout(Btname[] status,Btname...without){
		List<Btname> bts = new ArrayList<Btname>();
		for(Btname bt :status){
			boolean find = false;
			for(Btname btn: without){
				if(btn == bt){
					find = true;
					break;
				}
			}
			if(!find)bts.add(bt);
		}
		Btname[] bbttss = new Btname[bts.size()];
		for(int i=0;i<bbttss.length;i++){
			bbttss[i] = bts.get(i);
		}
		return bbttss;
	}
	
	public ListGridField getAuditField(){
	    ListGridField auditField = new ListGridField(CommonField.AUDIT.getEname(),CommonField.AUDIT.getCname());
	    auditField.setWidth(20);
	    return auditField;
	}

	

	public ComboBoxItem createCombItem(String ename,String cname,String pname){
		ComboBoxItem item = new ComboBoxItem(ename, cname);
	    Criteria minzuC = new Criteria();
	    minzuC.addCriteria(FieldEnumField.FIELD_TYPE.getEname(), pname);
	    item.setOptionDataSource(fieldDs);
	    item.setValueField("fieldValue");
	    item.setAnimatePickList(true);
	    item.setTextMatchStyle(TextMatchStyle.EXACT);
	    item.setPickListCriteria(minzuC);
		return item;
	}
	
	public SelectItem createSelectItem(String ename,String cname,String pname){
		SelectItem item = new SelectItem(ename, cname);
	    Criteria minzuC = new Criteria();
	    minzuC.addCriteria(FieldEnumField.FIELD_TYPE.getEname(), pname);
	    item.setOptionDataSource(fieldDs);
	    item.setValueField("fieldValue");
	    item.setAnimatePickList(true);
	    item.setTextMatchStyle(TextMatchStyle.EXACT);
	    item.setPickListCriteria(minzuC);
		return item;
	}
	
	private Label promptLabel;
	
	private Label getLb(){
		if(promptLabel == null){
			promptLabel = new Label();
		}
		return promptLabel;
	}
	
	private Window promptWindow;
	
	private Window getPw(){
		if(promptWindow == null){
			promptWindow = new Window();
			promptWindow.setHeight(120);
			promptWindow.setWidth(450);
			promptWindow.centerInPage();
			promptWindow.setShowTitle(false);
			promptWindow.setShowFooter(false);
			promptWindow.setShowCloseButton(false);
			promptWindow.setShowMaximizeButton(false);
			promptWindow.setShowMinimizeButton(false);
			promptLabel.setHeight(40);
			VLayout l = new VLayout();
			l.setWidth100();
			l.setHeight100();
			l.setAlign(Alignment.CENTER);
			l.setAlign(VerticalAlignment.CENTER);
			l.addMember(getLb());
			promptWindow.addItem(l);
		}
		return promptWindow;
	}
	
	
	public ListGridField getIdField(boolean hidden){
	    ListGridField pkField = new ListGridField(CommonField.ID.getEname());
	    pkField.setType(ListGridFieldType.INTEGER);
	    pkField.setHidden(hidden);
	    return pkField;
	}
	
	public ListGridField getParentIdField(){
	    ListGridField pkField = new ListGridField(CommonField.PARENT_ID.getEname());
	    pkField.setType(ListGridFieldType.INTEGER);
	    pkField.setHidden(true);
	    return pkField;
	}
	
	public ListGridField getPositionField(){
	    ListGridField positionField = new ListGridField(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
	    positionField.setType(ListGridFieldType.INTEGER);
	    positionField.setWidth(20);
	    return positionField;
	}
	
	public TextItem getPerpageItem(boolean isVertital){
		TextItem perpageItem = new TextItem(CommonField.PER_PAGE.getEname(), CommonField.PER_PAGE.getCname());
		if(isVertital)
			perpageItem.setTitleOrientation(TitleOrientation.TOP);
		return perpageItem;
	}
	
	public ListGridField getVersionField(){
	    ListGridField versionField = new TreeGridField("version");
	    versionField.setHidden(true);
	    return versionField;
	}
	
	public HiddenItem getIdHiddenItem(){
		HiddenItem idItem = new HiddenItem(CommonField.ID.getEname());
		return idItem;
	}
	
	public TextItem getIdDisableItem(boolean isVertical){
		TextItem idItem = new TextItem(CommonField.ID.getEname(),CommonField.ID.getCname());
		if(isVertical)
			idItem.setTitleOrientation(TitleOrientation.TOP);
		idItem.setDisabled(true);
		return idItem;
	}
	
	public HiddenItem getSiteIdItem(){
		HiddenItem idItem = new HiddenItem(CommonField.SITE_ID.getEname());
		return idItem;
	}
	
	public TextItem getPositionItem(){
		TextItem positionItem = new TextItem(CommonField.POSITION.getEname(),CommonField.POSITION.getCname());
		return positionItem;
	}
	
	public HiddenItem getVersionHiddenItem(){
		HiddenItem versionItem = new HiddenItem(CommonField.VERSION.getEname());
		return versionItem;
	}
	
	public HiddenItem getCreatedAtHiddenItem(){
		HiddenItem createdAtItem = new HiddenItem(CommonField.CREATED_AT.getEname());
		return createdAtItem;
	}
	
	public ListGridField getCreatedAtField(boolean isShow){
	    ListGridField createdAtField = new ListGridField(CommonField.CREATED_AT.getEname(),CommonField.CREATED_AT.getCname());
	    createdAtField.setHidden(!isShow);
	    return createdAtField;
	}
	
	public final void showTmpPrompt(String prompt,int millis){
		String s = "<div  align=\"center\">" + prompt + "</div>";
		getLb().setContents(s);
		getPw().show();
		Timer t = new Timer(){
			@Override
			public void run() {
				getPw().hide();
			}
		};
		t.schedule(millis);
	}
	
	public final void showTmpPrompt(String prompt){
		String s = "<div  align=\"center\">" + prompt + "</div>";
		getLb().setContents(s);
		getPw().show();
		Timer t = new Timer(){
			@Override
			public void run() {
				getPw().hide();
			}
		};
		t.schedule(1000);
	}
	
	public int[] getLeftAndTopIfCenter(int boxwidth,int boxheight){
		int hl = Page.getWidth();
		int vl = Page.getHeight();
		int left = (hl - boxwidth)/2;
		int top = (vl - boxheight)/2;
		return new int[]{left,top};
	}
	
	public int getLeftGapIfCenter(int boxwidth){
		int hl = Page.getWidth();
		return (hl - boxwidth)/2;
	}
	
	public int getTopGapIfCenter(int boxheight){
		int vl = Page.getHeight();
		return (vl - boxheight)/2;
	}

}
