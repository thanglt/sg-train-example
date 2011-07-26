package com.skynet.spms.client.gui.portal;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.skynet.spms.client.constants.PortalConstants;
import com.skynet.spms.client.gui.portal.PortalPanel.PortalMember;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class MockMember  implements PortalMember{
	
	private PortalConstants portalConst = GWT.create(PortalConstants.class);

	private Logger log=Logger.getLogger("mock member");
	
	private ListGrid taskList;
	
//	final DynamicForm form=new DynamicForm();


//	private DataSourceTool dsTool = new DataSourceTool();

	public MockMember(){
		taskList = new CustomGrid();
		
		taskList.setCanEdit(false);
		taskList.setShowRecordComponentsByCell(true);
		taskList.setShowRecordComponents(true);
		
		taskList.setWidth100();
		taskList.setHeight100();
		
		try{

		DataSource dataSource=DataSource.getDataSource("mockTestPage");

		taskList.setDataSource(dataSource);
		ListGridField[] fields=new ListGridField[]{
			new ListGridField("itemName"),
			new ListGridField("SKU"),
			new ListGridField("description"),
			new ListGridField("category"),
			new ListGridField("item.units"),
			new ListGridField("item.unitCost"),
			new ListGridField("itemList_m"),
			new ListGridField("subItem_m")
			
		};
		taskList.setFields(fields);
		
//		form.setDataSource(dataSource);
		}catch(Exception e){
			log.warning(e.getMessage());
		}
	}
	
	@Override
	public Canvas getCanvas() {
		
		taskList.fetchData();
//		VLayout layout=new VLayout();
//		layout.addMember(taskList);
//		layout.addMember(form);
		
		return taskList;

	}
	

	@Override
	public String getName() {
		return "Mock";
	}

	@Override
	public String getDescription() {
		return "Mock";
	}
	
	private static class CustomGrid extends ListGrid{
		
		@Override
		protected Canvas createRecordComponent(final ListGridRecord record,
				final Integer colNum) {
			
			String fieldName = getFieldName(colNum);
			
			if("itemList_m".equals(fieldName)){
				Record[] recordArray=record.getAttributeAsRecordArray("itemList");
				HLayout layout=new HLayout();
				for(Record rec:recordArray){
					Label lab=new Label();
					lab.setContents("item:name=>"+rec.getAttributeAsString("name")
							+" id:"+rec.getAttributeAsInt("id"));
					layout.addMember(lab);
				}
				return layout;
				
			}else if("subItem_m".equals(fieldName)){
				Record rec=record.getAttributeAsRecord("subItem");
				Label lab=new Label();
				lab.setContents("item:name=>"+rec.getAttributeAsString("name")
						+" id:"+rec.getAttributeAsInt("id"));
				return lab;
			}else{
				return null;
			}

			
			
		}

		
		
	}

}
