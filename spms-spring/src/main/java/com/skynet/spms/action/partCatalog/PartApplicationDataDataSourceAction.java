package com.skynet.spms.action.partCatalog;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skynet.spms.datasource.DataSourceAction;
import com.skynet.spms.manager.partCatalog.PartApplicationDataManager;
import com.skynet.spms.persistence.entity.partCatalog.technicalCatalog.applicationData.PartApplicationData;

@Component
public class PartApplicationDataDataSourceAction implements DataSourceAction<PartApplicationData> {
	

	private Logger log=LoggerFactory.getLogger(PartApplicationDataDataSourceAction.class);
	
	@Autowired
	PartApplicationDataManager partApplicationDataManager;

	@Override
	public String[] getBindDsName() {
		// TODO Auto-generated method stub
		return new String[]{"applicationData_dataSource"}; 
	}

	@Override
	public void insert(PartApplicationData item) {
		log.info("===============PartApplicationDataDataSourceAction.insert()");
		log.info("id : " + item.getId());
		log.info("PartIndexId : " + item.getPartIndexId());
		log.info("RecommendedQuantity : " + item.getRecommendedQuantity());
		log.info("isQuickEngineChange" + item.isQuickEngineChange());
		log.info("ETOPSFlightIndicator : " + item.getM_ETOPSFlightIndicator());
		log.info("EngineLevelOfMaintenanceCode" + item.getM_EngineLevelOfMaintenanceCode());
		log.info("ExportControlClassificationCode" + item.getM_ExportControlClassificationCode());
		log.info("MaintenanceOverhaulRepairCode" + item.getM_MaintenanceOverhaulRepairCode());
		log.info("MiscellaneousText : " + item.getMiscellaneousText());
		partApplicationDataManager.insertEntity(item);
	}

	@Override
	public PartApplicationData update(Map<String, Object> newValues, String itemID) {
		log.info("===============PartApplicationDataDataSourceAction.update()");
		for(Map.Entry<String, Object> entry : newValues.entrySet()){
			log.info(entry.getKey() + " : " + entry.getValue() );
		}
		PartApplicationData pad = partApplicationDataManager.updateEntity(newValues, itemID, PartApplicationData.class);
		
		return pad;
	}

	@Override
	public void delete(String itemID) {
		log.info("===============PartApplicationDataDataSourceAction.delete()");
		log.info("PartApplicationDataId ：" + itemID);
		partApplicationDataManager.deleteEntity(itemID, PartApplicationData.class);
	}

	@Override
	public List<PartApplicationData> doQuery(Map<String, Object> values, int startRow,
			int endRow) {
		log.info("===============BasicInfoDataSourceAction.doQuery()");
		for(Map.Entry<String, Object> entry : values.entrySet()){
			log.info(entry.getKey() + " : " + entry.getValue() );
		}
		List<PartApplicationData> list = partApplicationDataManager.queryByProps(values);
	
		return list;
	}

	@Override
	public List<PartApplicationData> getList(int startRow, int endRow) {
		log.info("===============PartApplicationDataDataSourceAction.getList()");
		List<PartApplicationData> list = partApplicationDataManager.list(startRow, endRow, PartApplicationData.class);
		
		return list;
	}
	
	
}
