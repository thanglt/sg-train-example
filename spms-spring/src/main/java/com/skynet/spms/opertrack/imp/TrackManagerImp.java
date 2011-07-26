package com.skynet.spms.opertrack.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.skynet.spms.datasource.QueryGeneral;
import com.skynet.spms.opertrack.TrackManager;
import com.skynet.spms.persistence.entity.base.logEntity.GeneralOperateLogEntity;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class TrackManagerImp implements TrackManager {

	private Logger log = LoggerFactory.getLogger(TrackManagerImp.class);

//	 private SessionFactory sessionFactory;
	
	@Autowired
	private QueryGeneral queryGeneral;

	@Autowired
	private ApplicationContext context;

	private Session getSession() {
		
		SessionFactory factory= context.getBean(SessionFactory.class);
		
		return factory.getCurrentSession();

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skynet.spms.opertrack.imp.TrackManager#insertTrackRecord(com.skynet
	 * .spms.opertrack.TrackInfo)
	 */
	@Async
	@Override
	public void insertTrackRecord(List<GeneralOperateLogEntity> infoList) {
		log.info("save track info,size:"+infoList.size());
		for(GeneralOperateLogEntity info:infoList){
			getSession().save(info);
		}
		infoList.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<GeneralOperateLogEntity> getList(int startRow,int endRow){
		
		Date lastMonth=DateUtils.addMonths(new Date(), -1);
		List<GeneralOperateLogEntity> list= getSession().createQuery(
				"from GeneralOperateLogEntity where actionDate > :date" +
				" order by actionDate desc")
		.setDate("date", lastMonth)
		.setMaxResults(endRow)
		.list();
		
		return list.subList(startRow,list.size());
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<GeneralOperateLogEntity> queryList(int startRow, int endRow,
			Map<String, Object> values) {
		
		Criterion criteria=queryGeneral.generQueryHQL(values,GeneralOperateLogEntity.class);
		
		List<GeneralOperateLogEntity> list=getSession()
			.createCriteria(GeneralOperateLogEntity.class)
			.add(criteria)
			.addOrder(Order.desc("actionDate"))
			.setMaxResults(endRow).list();
				
		return list.subList(startRow,list.size());
	}
}
