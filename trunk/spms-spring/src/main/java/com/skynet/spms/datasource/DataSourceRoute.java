package com.skynet.spms.datasource;

import com.skynet.spms.persistence.entity.base.baseEntity.BaseEntity;



public interface DataSourceRoute {

	DataSourceManager getDataSourceManager(String dsName,Class<? extends BaseEntity> cls);

	DataSourceAction getDataSourceAction(String dsName,Class<?> cls);
	
	DataSourceManager getDataSourceManager(String dsName);

	DataSourceAction getDataSourceAction(String dsName);

}