package com.m3958.firstgwt.client.layout;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Singleton;
import com.m3958.firstgwt.client.datasource.SavedFormQueryDataSource;
import com.m3958.firstgwt.client.jso.savedFormQueryRecord;
import com.m3958.firstgwt.client.rpc.MyDsCallback;
import com.m3958.firstgwt.client.types.SavedFormQueryField;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

@Singleton
public class LgbSavedQueryFormLayout {
	
	private final DynamicForm form = new DynamicForm();
	private final IButton removeBtn = new IButton("删除选择的查询");
	
	private SavedFormQueryDataSource sqds;
	
	public LgbSavedQueryFormLayout(SavedFormQueryDataSource sqds){
		this.sqds = sqds;
	}

	//Lazy load
	public Layout getSavedQueryLayout(){
		HLayout layout = new HLayout(5);
		layout.setWidth100();
		layout.setHeight100();
		form.setWidth(270);
		form.setHeight100();
		final SelectItem savedQueryItem = new SelectItem(SavedFormQueryField.QUERY_STRING.getEname(),"保存的查询");
		savedQueryItem.setWidth(200);
	    ListGridField nameField = new ListGridField(SavedFormQueryField.NAME.getEname(),"查询名称");
	    savedQueryItem.setOptionDataSource(sqds);
	    savedQueryItem.setValueField(SavedFormQueryField.QUERY_STRING.getEname());
	    savedQueryItem.setDisplayField(SavedFormQueryField.NAME.getEname());
	    savedQueryItem.setPickListWidth(350);
	    savedQueryItem.setPickListFields(nameField);
	    
	    form.setItems(savedQueryItem);
	    layout.addMember(form);
	    IButton scbt = new IButton("执行保存的查询");
	    scbt.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Criteria c = new Criteria((JavaScriptObject) form.getValue(SavedFormQueryField.QUERY_STRING.getEname()));
//				c.setAttribute(LgbField.DEPARTMENT_IDS.getEname(), "," + sw.getLgbtw().getDepartmentId() + ",");
//				sw.getLgbtw().getLgbGrid().filterData(c);
//				sw.hideWindow();
			}});
	    
	    removeBtn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord r = savedQueryItem.getSelectedRecord();
				sqds.removeData(r,new MyDsCallback(){

					@Override
					public void afterSuccess(DSResponse response,
							Object rawData, DSRequest request) {
//						auis.showTmpPrompt("已删除！", 50000);
						savedQueryItem.redraw();
					}});
			}});
	    HLayout hl = new HLayout(5);
	    hl.addMember(scbt);
	    hl.addMember(removeBtn);
	    layout.addMember(hl);
	    return layout;
	}
	
	public void removeFormQuery(){
		
	}
	
	public void saveFormQuery(String qn){
//		Criteria c = sw.buildCriteria();
//		for(Object s:c.getValues().keySet()){
//			System.out.println(s.toString());
//		}
//		c.setAttribute(LgbField.DEPARTMENT_IDS.getEname(),"");
//		String js = JSON.encode(c.getJsObj());
		savedFormQueryRecord record = new savedFormQueryRecord();
		record.setName(qn);
//		record.setQueryString(js);
		sqds.addData(record, new MyDsCallback(){
			@Override
			public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
				f.clearValues();
				getW().hide();
//				GlobalStaffs.showTmpPrompt("已保存！", 50000);
			}});
	}
	
	private Window w;
	
	private Window getW(){
		if(w == null){
			w = new Window();
			w.setTitle("输入查询名称");
			w.setWidth(350);
			w.setHeight(100);
			w.setIsModal(true);
			w.setShowModalMask(true);
			w.centerInPage();
			initWcontent();
		}
		return w;
	}
	
	private DynamicForm f; 
	
	public void initWcontent(){
		f = new DynamicForm();
		f.setNumCols(2);
		TextItem t = new TextItem(SavedFormQueryField.NAME.getEname(),"查询的名称");
		t.setRequired(true);
		f.setFields(t);
		
		IButton b = new IButton("保存当前查询");
		
		b.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if(f.validate(false)){
					Criteria c = new Criteria();
					c.addCriteria(SavedFormQueryField.NAME.getEname(), f.getValueAsString(SavedFormQueryField.NAME.getEname()));
					sqds.fetchData(c,new MyDsCallback(){
						@Override
						public void afterSuccess(DSResponse response, Object rawData, DSRequest request) {
							if(response.getData().length > 0){
								SC.warn("该查询名称已经存在！请输入其它名称");
							}else{
								saveFormQuery(f.getValueAsString(SavedFormQueryField.NAME.getEname()));
							}
						}});
				}
			}});
		w.addItem(f);
		w.addItem(b);
	}
	
	public void askQueryName(){
		getW().show();
	}
}
