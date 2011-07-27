package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Section;

public class SectionDao extends BaseDao<Section>{
	

	@Inject
	public SectionDao(SectionChangeStrategy task) {
		super(Section.class);
		extraStrategyTask = task;
	}
	
	
	public Section findSection(int siteId,String name){
		Query q = emp.get().createNamedQuery(Section.NamedQueries.FIND_TOP_BY_NAME);
		q.setParameter("siteId", siteId);
		q.setParameter("name", name);
		return (Section) q.getSingleResult();
	}
	
	public List<Section> getTopSections(int siteId){
		Query q = emp.get().createNamedQuery(Section.NamedQueries.FIND_TOP);
		q.setParameter("siteId", siteId);
		return q.getResultList();
	}

	
	@Override
	public List<Section> smartFetchChildren(){
		if(getReqPs().getParentId() == SmartConstants.NONE_EXIST_MODEL_ID){
			int siteId = getReqPs().getIntegerValue(CommonField.SITE_ID.getEname());
			if(getRso().isSiteEditor(siteId) || getRso().isSiteOwner(siteId)){
				return getTopTreeNode();
			}
		}
		return super.smartFetchChildren();
	}
	
	@Override
	public Integer smartFetchChildrenCount() {
		if(getReqPs().getParentId() == SmartConstants.NONE_EXIST_MODEL_ID){
			int siteId = getReqPs().getIntegerValue(CommonField.SITE_ID.getEname());
			if(getRso().isSiteEditor(siteId) || getRso().isSiteOwner(siteId)){
				return getTopTreeNodeCount();
			}
		}
		return super.smartFetchChildrenCount();
	}
	
	//siteowner的话，显示所有。site编辑者的话也是返回全部。

	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}


	@Override
	public List<Section> smartNamedQueryFetch() {
		return null;
	}


	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}
}
