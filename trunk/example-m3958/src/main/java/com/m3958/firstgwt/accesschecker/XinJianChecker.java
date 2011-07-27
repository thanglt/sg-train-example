package com.m3958.firstgwt.accesschecker;


import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.dao.XinJianCatDao;
import com.m3958.firstgwt.model.XinJian;
import com.m3958.firstgwt.model.XinJianCat;
import com.m3958.firstgwt.server.types.ObjectOperation;

public class XinJianChecker extends BaseChecker<XinJian> {

	@Override
	protected boolean canAdd() {
		int i = reqPs.getIdValue(CommonField.SITE_ID.getEname());
		if(isSiteEditor(i) || isSiteOwner(i))return true;
		XinJianCatDao sdao = injector.getInstance(XinJianCatDao.class);
		XinJianCat s = sdao.find(reqPs.getRelationModelId());
		if(s == null)return false;
		if(hasTreeContainerPerms(XinJianCat.class.getName(), s.getId(), ObjectOperation.ADD_CONTENT)){
				return true;
		}
		return false;
	}

	@Override
	protected boolean canFetch() {
		switch (getSubOpName()) {
		case MANY_TO_MANY:
			if(SmartConstants.NONE_EXIST_MODEL_ID == reqPs.getRelationModelId())return true;
			if(XinJianCat.class.getName().equals(reqPs.getRelationModelName())){
				XinJianCat s = emp.get().find(XinJianCat.class, reqPs.getRelationModelId());
				if(isSiteEditor(s.getSiteId()) || isSiteOwner(s.getSiteId()))return true;
			}
			return hasTreeContainerPerms(XinJianCat.class.getName(), reqPs.getRelationModelId(), ObjectOperation.FETCH_CONTENT);
		case FETCH_ONE:
			boolean cked = false;
			for(XinJianCat s : getModel().getXjCats()){
				if(!cked){
					if(isSiteEditor(s.getSiteId()) || isSiteOwner(s.getSiteId())){
						return true;
					}else{
						cked = true;
					}
				}
				if(hasTreeContainerPerms(XinJianCat.class.getName(), s.getId(), ObjectOperation.FETCH_CONTENT))return true;	
			}
			return false;
		default:
			return false;
		}
	}

	@Override
	protected boolean canRemove() {
		if(isSiteEditor(getModel().getSiteId()) || isSiteOwner(getModel().getSiteId()))return true;
		for(XinJianCat s : getModel().getXjCats()){
			if(hasTreeContainerPerms(XinJianCat.class.getName(), s.getId(), ObjectOperation.REMOVE_CONTENT)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean canUpdate() {
		if(isSiteEditor(getModel().getSiteId()) || isSiteOwner(getModel().getSiteId()))return true;
		for(XinJianCat s : getModel().getXjCats()){//对其中一个目录由update权限即可
			if(hasTreeContainerPerms(XinJianCat.class.getName(), s.getId(), ObjectOperation.UPDATE_CONTENT)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean canCustom() {
		int i =reqPs.getIdValue(CommonField.SITE_ID.getEname());
		if(isSiteEditor(i) || isSiteOwner(i))return true;
		switch (getSubOpName()) {
		case FETCH_RELATION_COUNT:
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	protected boolean canMyRpc() {
		return false;
	}
}
