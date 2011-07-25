package com.skynet.spms.manager;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;

import com.skynet.spms.persistence.entity.base.baseEntity.BaseEntity;

public interface CommonDao{

	void deleteEntity(String entityId,Class<? extends BaseEntity> cls);

	BaseEntity updateEntity(Map<String,Object> entity, String entityId,Class<? extends BaseEntity> cls);

	void insertEntity(BaseEntity entity);
	
	BaseEntity queryById(String entityId,Class<? extends BaseEntity> cls);

	List<BaseEntity> list(int startRow, int endRow, Class<? extends BaseEntity> cls);
	
	List<BaseEntity> queryByBean(BaseEntity query,
			int startRow, int endRow);

	Session getSession();

	Session getClearSession();
	
	FullTextSession getFullTextSession();
	
}
