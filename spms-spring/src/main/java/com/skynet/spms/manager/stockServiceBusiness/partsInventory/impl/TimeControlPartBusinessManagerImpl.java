package com.skynet.spms.manager.stockServiceBusiness.partsInventory.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skynet.spms.manager.helper.SqlHelperTool;
import com.skynet.spms.manager.imp.CommonManagerImpl;
import com.skynet.spms.manager.stockServiceBusiness.partsInventory.TimeControlPartBusinessManager;
import com.skynet.spms.persistence.entity.stockServiceBusiness.partsInventory.timeControlPartBusiness.TimeControlPartBusiness;

@Service
@Transactional
public class TimeControlPartBusinessManagerImpl extends CommonManagerImpl<TimeControlPartBusiness> implements TimeControlPartBusinessManager{

	@Override
	public List<TimeControlPartBusiness> getTimeControlPartBusinessByCondition(
			Map values, int startRow, int endRow) {
		Criteria criteria= getSession().createCriteria(TimeControlPartBusiness.class);
		criteria.add(Restrictions.eq("deleted", false));
		SqlHelperTool.createCriteria(values, criteria, TimeControlPartBusiness.class, null);     
		
		if(endRow>0){
			criteria.setFirstResult(startRow).setMaxResults(endRow-startRow);
		}

		return criteria.list();
	}

	@Override
	public List<TimeControlPartBusiness> getTimeControlPartBusiness(
			int startRow, int endRow) {
		Criteria criteria= getSession().createCriteria(TimeControlPartBusiness.class);
		criteria.add(Restrictions.eq("deleted", false));
		if(endRow>0){
			criteria.setFirstResult(startRow).setMaxResults(endRow-startRow);
		}

		return criteria.list();
	}

	
}