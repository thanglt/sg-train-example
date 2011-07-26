package com.train.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class TestMySQL implements EntryPoint {

	public void onModuleLoad() {
		
		final DataSource userDS = DataSource.get("user");//与user.ds.xml中的 ID保持一致，匹配大小写
		
		VLayout layout = new VLayout();//纵向布局
		layout.setWidth100();
		layout.setHeight100();
		
		final ListGrid grid = new ListGrid();//数据表格
		grid.setWidth100();
		grid.setHeight(400);
		grid.setAutoFetchData(true);//自动获取所有数据
		grid.setDataSource(userDS);//设置DataSource
		grid.setUseAllDataSourceFields(true);//使用所有table的字段
        grid.setAlign(Alignment.CENTER);
		
		final DynamicForm form = new DynamicForm();//表单
		form.setDataSource(userDS);//设置DataSource
		
		TextItem nameItem = new TextItem("name" , "名称");//这里的name必须与user.ds.xml字段一致，匹配大小写
		
		TextItem ageItem = new TextItem("age" , "年龄");//这里的age必须与user.ds.xml字段一致，匹配大小写

		ButtonItem btnReset = new ButtonItem("Reset" , "Reset");
		
		ButtonItem btnCreate = new ButtonItem("Create" , "Create");
		
		ButtonItem btnUpdate = new ButtonItem("Update" , "Update");
		
		ButtonItem btnDelete = new ButtonItem("Delete" , "Delete");
		
		form.setItems(nameItem , ageItem ,btnReset ,btnCreate , btnUpdate , btnDelete);
		
		//事件监听
		//1.点击ListGrid数据，form 开始编辑
		grid.addCellClickHandler(new CellClickHandler() {
			public void onCellClick(CellClickEvent event) {
				ListGridRecord record = grid.getSelectedRecord();
				form.editRecord(record);
			}
		});
		
		//2.点击Create按钮，插入数据
		btnCreate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				Record record = new Record();
//				record.setAttribute("name", form.getValueAsString("name"));
//				record.setAttribute("age", form.getValue("age"));
				form.setSaveOperationType(DSOperationType.ADD);
				form.saveData();
//				userDS.addData(record);
			}
		});
		
		//3.点击Update按钮，进行数据更新
		btnUpdate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.setSaveOperationType(DSOperationType.UPDATE);
				form.saveData();
			}
		});
		
		//4.点击Delete按钮，数据删除
		btnDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.removeSelectedData();
			}
		});
		
		//5.点击Reset按钮，重置表单
		btnReset.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.clearValues();
			}
		});
		
		//拼装
		layout.addMember(grid);
		layout.addMember(form);
		//呈现
		layout.draw();
		
	}
}
