package com.m3958.firstgwt.dao;

import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.AssetFolder;
import com.m3958.firstgwt.model.BaseModel;

public class AssetFolderDao  extends BaseDao<AssetFolder>{
	

	@Inject
	public AssetFolderDao(AssetFolderChangeStrategy task) {
		super(AssetFolder.class);
		extraStrategyTask = task;
	}
	
	
	public AssetFolder findAssetFolder(int siteId,String name){
		Query q = emp.get().createNamedQuery(AssetFolder.NamedQueries.FIND_TOP_BY_NAME);
		q.setParameter("siteId", siteId);
		q.setParameter("name", name);
		return (AssetFolder) q.getSingleResult();
	}
	
	public List<AssetFolder> getTopXinJianCats(int siteId){
		Query q = emp.get().createNamedQuery(AssetFolder.NamedQueries.FIND_TOP);
		q.setParameter("siteId", siteId);
		return q.getResultList();
	}
	
	
	@Override
	public List<AssetFolder> smartFetchChildren(){
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


	
	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}


	@Override
	public List<AssetFolder> smartNamedQueryFetch() {
		return null;
	}


	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}




}
