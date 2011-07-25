package com.skynet.spms.manager.stockServiceBusiness.discardServiceBusiness.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skynet.common.gwt.GwtActionHelper;
import com.skynet.spms.manager.helper.SqlHelperTool;
import com.skynet.spms.manager.imp.CommonManagerImpl;
import com.skynet.spms.manager.stockServiceBusiness.discardServiceBusiness.DiscardServiceBusinessManager;
import com.skynet.spms.persistence.entity.stockServiceBusiness.discardServiceBusiness.DiscardServiceBusiness;

@Service
@Transactional
public class DiscardServiceBusinessManagerImpl extends CommonManagerImpl<DiscardServiceBusiness> implements DiscardServiceBusinessManager{

	@Override
	public List<DiscardServiceBusiness> getDiscardServiceBusiness(
			Map values, int startRow, int endRow) {
		Criteria criteria= getSession().createCriteria(DiscardServiceBusiness.class);
		criteria.add(Restrictions.eq("deleted", false));
		SqlHelperTool.createCriteria(values, criteria, DiscardServiceBusiness.class, null);
		
		if(endRow>0){
			criteria.setFirstResult(startRow).setMaxResults(endRow-startRow);
		}

		return criteria.list();
	}

	@Override
	public DiscardServiceBusiness saveDiscardServiceBusiness(
			DiscardServiceBusiness discardServiceBusiness) {
		Session session = getSession();
		discardServiceBusiness.setCreateBy(GwtActionHelper.getCurrUser());
		discardServiceBusiness.setCreateDate(new Date());
		session.saveOrUpdate(discardServiceBusiness);
		return discardServiceBusiness;
	}

}