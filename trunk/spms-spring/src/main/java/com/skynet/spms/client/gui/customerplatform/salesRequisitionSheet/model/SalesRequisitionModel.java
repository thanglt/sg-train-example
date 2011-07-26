package com.skynet.spms.client.gui.customerplatform.salesRequisitionSheet.model;

import com.skynet.spms.client.gui.customerService.salesService.SalesRequisitionSheet.SalesRequisitionSheetItemListGrid;
import com.skynet.spms.client.gui.customerService.salesService.SalesRequisitionSheet.SalesRequisitionSheetListGrid;
import com.skynet.spms.client.model.IModelLocator;
import com.skynet.spms.client.vo.UserVo;
import com.smartgwt.client.data.DataSource;

/**
 * 存放业务数据
 * 
 * @author tqc
 * 
 */
public class SalesRequisitionModel implements IModelLocator<SalesRequisitionModel> {

	private static SalesRequisitionModel instance;

	private SalesRequisitionModel() {
	}

	public static SalesRequisitionModel getInstance() {
		if (instance == null) {
			instance = new SalesRequisitionModel();
		}
		return instance;
	}
 
	public SalesRequisitionSheetListGrid listGrid;
	public SalesRequisitionSheetItemListGrid itemListGrid;
	public DataSource dataSource;
	public DataSource itemDataSource;
	
	public String primaryKey;
	
	
	

}
