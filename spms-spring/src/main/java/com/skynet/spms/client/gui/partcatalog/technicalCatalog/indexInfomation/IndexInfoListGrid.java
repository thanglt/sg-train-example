package com.skynet.spms.client.gui.partcatalog.technicalCatalog.indexInfomation;
import java.util.ArrayList;
import java.util.List;

import com.skynet.spms.client.entity.DataInfo;
import com.skynet.spms.client.feature.data.DataSourceTool;
import com.skynet.spms.client.feature.data.DataSourceTool.PostDataSourceInit;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import com.smartgwt.client.types.DateDisplayFormat;



public class IndexInfoListGrid extends ListGrid {
	
	public IndexInfoListGrid() {
		DataSourceTool dataSourceTool = new DataSourceTool();
		dataSourceTool.onCreateDataSource("partCatalog.technical","partIndex_dataSource", new PostDataSourceInit() {	
			@Override
			public void doPostOper(DataSource dataSource, DataInfo dataInfo) {
				drawIndexInfoListGrid(dataSource);	
			}
		});	
	}

	public void drawIndexInfoListGrid(DataSource dataSource) {  
		
		setDataSource(dataSource); 
		//setAutoFetchData(true);
        setShowFilterEditor(true);
        setShowAllRecords(false);
        //setShowRowNumbers(true);
        
        setSelectionType(SelectionStyle.SIMPLE);
        setSelectionAppearance(SelectionAppearance.CHECKBOX);
        setCellHeight(22);   
		
		List<ListGridField> fieldList = new ArrayList<ListGridField>();
		
		//原厂商件号
	    ListGridField lgfManufacturerNumber = new ListGridField("manufacturerPartNumber");
	    lgfManufacturerNumber.setCanFilter(true);
	    lgfManufacturerNumber.setCanEdit(false);
	    fieldList.add(lgfManufacturerNumber);
	    //商飞件号
        ListGridField lgfPartNumber = new ListGridField("partNumber");
        lgfPartNumber.setCanFilter(true);
        lgfPartNumber.setCanEdit(false);
        fieldList.add(lgfPartNumber);
        
        /*//超长零件号
        ListGridField lgfOverlengthNumber = new ListGridField("overlengthPartNumber");  
        lgfOverlengthNumber.setCanEdit(false);
        fieldList.add(lgfOverlengthNumber);
        //唯一部件标识号
        ListGridField lgfUcin = new ListGridField("uniqueComponentIdentificationNumber");
        lgfUcin.setCanEdit(false);
        fieldList.add(lgfUcin);*/
        
        //制造商代码
        ListGridField lgfManufacturerCode = new ListGridField("m_ManufacturerCode.code");
        lgfManufacturerCode.setCanFilter(true);
        lgfManufacturerCode.setCanEdit(false);
        fieldList.add(lgfManufacturerCode);
        
        //发布状态
        ListGridField lgfPubStatus = new ListGridField("m_BussinessPublishStatusEntity.m_PublishStatus");
        lgfPubStatus.setCanFilter(true);
        lgfPubStatus.setCanEdit(false);
        fieldList.add(lgfPubStatus);
        
        //发布时间
        ListGridField lgfPubTime = new ListGridField("m_BussinessPublishStatusEntity.actionDate");
        lgfPubTime.setCanFilter(true);
        lgfPubTime.setCanEdit(false);
        lgfPubTime.setWidth(120);
        lgfPubTime.setDateFormatter(DateDisplayFormat.TOJAPANSHORTDATETIME);
        fieldList.add(lgfPubTime);

        ListGridField[] fields = new ListGridField[fieldList.size()];
        fieldList.toArray(fields);
        
        setFields(fields);
        fetchData();
    }  
}