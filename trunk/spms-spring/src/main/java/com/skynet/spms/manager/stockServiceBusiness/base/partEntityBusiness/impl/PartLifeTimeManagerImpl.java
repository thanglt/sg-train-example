package com.skynet.spms.manager.stockServiceBusiness.base.partEntityBusiness.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skynet.spms.manager.helper.SqlHelperTool;
import com.skynet.spms.manager.imp.CommonManagerImpl;
import com.skynet.spms.manager.stockServiceBusiness.base.partEntityBusiness.PartLifeTimeManager;
import com.skynet.spms.persistence.entity.stockServiceBusiness.base.partEntity.PartLifeTime;

@Service
@Transactional
public class PartLifeTimeManagerImpl extends CommonManagerImpl<PartLifeTime> implements PartLifeTimeManager{

	@Override
	public List<PartLifeTime> getPartLifeTime(
			Map values, int startRow, int endRow) {
		Criteria criteria= getSession().createCriteria(PartLifeTime.class);
		criteria.add(Restrictions.eq("deleted", false));
		SqlHelperTool.createCriteria(values, criteria, PartLifeTime.class, null);
		
		if(endRow>0){
			criteria.setFirstResult(startRow).setMaxResults(endRow-startRow);
		}

		return criteria.list();
	}
}