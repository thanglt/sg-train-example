package com.m3958.firstgwt.dao;

import java.util.List;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Comment;


public class CommentDao  extends BaseDao<Comment> {
	
	@Inject
	public CommentDao(CommentChangeStrategy task) {
		super(Comment.class);
		extraStrategyTask = task;
	}
	
	
	@Override
	public List<Comment> smartFetchChildren(){
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
	public List<Comment> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}
