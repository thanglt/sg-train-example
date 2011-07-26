package com.skynet.spms.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.skynet.spms.datasource.BeanPropUtil;
import com.skynet.spms.datasource.DataSourceAction;
import com.skynet.spms.datasource.DataSourceRoute;
import com.skynet.spms.datasource.EntityInfoService;
import com.skynet.spms.datasource.GetEntityCls;
import com.skynet.spms.service.SimpleDataSourceService;
import com.skynet.spms.web.control.SimpleDataSourceAdapte;

@Component("simpleDataSourceService")
public class SimpleDataSourceServiceImpl implements SimpleDataSourceService{
	
	private static final int PAGE_SIZE = 79;

	private Logger log = LoggerFactory.getLogger(SimpleDataSourceAdapte.class);

	@Autowired
	private DataSourceRoute actionRoute;
		
	@Override
	public DSResponse fetch(DSRequest dsRequest) throws Exception {
		
		DSResponse dsResponse = new DSResponse();
		// DataSource protocol: get filter criteria


		String dsName=dsRequest.getDataSourceName();
		BeanPropUtil.setDsName(dsName);
		
		DataSourceAction service=actionRoute.getDataSourceAction(dsName);
		
		log.debug("val:" + dsRequest.getValues());

		// DataSource protocol: get requested row range
		long startRow = dsRequest.getStartRow();
		long endRow = dsRequest.getEndRow();
		log.info("start:" + startRow + " end:" + endRow);
		List matchingItems = null;
		if (dsRequest.getValues().isEmpty()) {
			matchingItems = service.getList((int) startRow, (int) endRow);
		} else {
			// bean storage specific: lookup matching item beans
			matchingItems = service.doQuery(dsRequest.getValues(),
					(int) startRow, (int) endRow);
		}

		log.info("get record list:" + matchingItems.size());
		List<String> fieldList=dsRequest.getDataSource().getFieldNames();

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(Object obj:matchingItems){
			list.add(BeanPropUtil.getDataMap(fieldList, obj));
		}
		dsResponse.setData(list);
		// tell client what rows are being returned, and what's available
		dsResponse.setStartRow(startRow);
		dsResponse.setEndRow(matchingItems.size()+startRow);
		long totalNow=startRow+matchingItems.size();
		if(totalNow==endRow){
			totalNow+=PAGE_SIZE;
		}
		dsResponse.setTotalRows(totalNow);
		return dsResponse;
	}

	@Override
	public DSResponse add(DSRequest dsRequest, Map item)
			throws Exception {
		log.info("procesing DMI add operation");

		DSResponse dsResponse = new DSResponse();
		String dsName=dsRequest.getDataSourceName();
		BeanPropUtil.setDsName(dsName);
		
		List<String> fieldList=dsRequest.getDataSource().getFieldNames();
		
		DataSourceAction service=actionRoute.getDataSourceAction(dsName);

		
		// DataSource protocol: get new values to be saved
		if (item.isEmpty()) {
			dsResponse.setData(dsRequest.getCriteria());
			return dsResponse;
		}		
		Class cls=((GetEntityCls)service).getEntityClass();
		Object newItem = BeanPropUtil.getEntityByMap(fieldList, item, cls);
				
		service.insert(newItem);

		// DataSource protocol: return the committed item bean to the client for
		// cache update
		dsResponse.setData(item);
		return dsResponse;
	}

	@Override
	public DSResponse update(DSRequest dsRequest, Map newValues)
			throws Exception {
		log.info("procesing DMI update operation");

		DSResponse dsResponse = new DSResponse();
		String dsName=dsRequest.getDataSourceName();
		BeanPropUtil.setDsName(dsName);
		
		
		List<String> fieldList=dsRequest.getDataSource().getFieldNames();

		DataSourceAction service=actionRoute.getDataSourceAction(dsName);

		String pkName=dsRequest.getDataSource().getPrimaryKey();
		
		String itemID = (String) dsRequest.getFieldValue(pkName);
		// DataSource protocol: get new values to be saved
		if (newValues.isEmpty()) {
			dsResponse.setData(dsRequest.getCriteria());
			return dsResponse;
		}
		
		Object item = service.update(newValues, itemID);

		Map<String,Object> newItem=BeanPropUtil.getDataMap(fieldList, item);
		dsResponse.setData(newItem);
		
		return dsResponse;
	}

	@Override
	public Map remove(DSRequest dsRequest,Map item) throws Exception {
		log.info("procesing DMI remove operation");
		String dsName=dsRequest.getDataSourceName();
		BeanPropUtil.setDsName(dsName);
		
		DataSourceAction service=actionRoute.getDataSourceAction(dsName);

		String pk=dsRequest.getDataSource().getPrimaryKey();
		if (item.isEmpty()) {
			return item;
		}
		
		String itemID = (String)item.get(pk);		
		if (StringUtils.isNotBlank(itemID)) {
			service.delete(itemID);
		}
		
		return item;
	}
}
