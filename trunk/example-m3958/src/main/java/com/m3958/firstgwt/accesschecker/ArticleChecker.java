package com.m3958.firstgwt.accesschecker;



import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.dao.SectionDao;
import com.m3958.firstgwt.model.Article;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.server.types.ObjectOperation;

public class ArticleChecker extends BaseChecker<Article> {

	@Override
	protected boolean canAdd() {
		int i = reqPs.getIdValue(CommonField.SITE_ID.getEname());
		if(isSiteEditor(i) || isSiteOwner(i))return true;
		SectionDao sdao = injector.getInstance(SectionDao.class);
		Section s = sdao.find(reqPs.getRelationModelId());
		if(s == null)return false;
		if(hasTreeContainerPerms(Section.class.getName(), s.getId(), ObjectOperation.ADD_CONTENT)){
				return true;
		}
		return false;
	}

	@Override
	protected boolean canFetch() {
		switch (getSubOpName()) {
		case MANY_TO_MANY:
			if(SmartConstants.NONE_EXIST_MODEL_ID == reqPs.getRelationModelId())return true;
			if(Section.class.getName().equals(reqPs.getRelationModelName())){
				Section s = emp.get().find(Section.class, reqPs.getRelationModelId());
				if(isSiteEditor(s.getSiteId()) || isSiteOwner(s.getSiteId()))return true;
			}
			return hasTreeContainerPerms(Section.class.getName(), reqPs.getRelationModelId(), ObjectOperation.FETCH_CONTENT);
		case FETCH_ONE:
			boolean cked = false;
			for(Section s : getModel().getSections()){
				if(!cked){
					if(isSiteEditor(s.getSiteId()) || isSiteOwner(s.getSiteId())){
						return true;
					}else{
						cked = true;
					}
				}
				if(hasTreeContainerPerms(Section.class.getName(), s.getId(), ObjectOperation.FETCH_CONTENT))return true;	
			}
			return false;
		case NOT_CREATOR_HAS_PERMISSION:
			return true;
		case NOT_CREATOR_HAS_GPERMISSION:
			return true;
		default:
			if(reqPs.getBooleanValue(SmartParameterName.FETCH_OWNER)){
				return true;
			}
			return false;
		}
	}

	@Override
	protected boolean canRemove() {
		if(isSiteEditor(getModel().getSiteId()) || isSiteOwner(getModel().getSiteId()))return true;
		for(Section s : getModel().getSections()){
			if(hasTreeContainerPerms(Section.class.getName(), s.getId(), ObjectOperation.REMOVE_CONTENT)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean canUpdate() {
		if(isSiteEditor(getModel().getSiteId()) || isSiteOwner(getModel().getSiteId()))return true;
		if(isObjectOwner())return true;
		for(Section s : getModel().getSections()){//对其中一个目录由update权限即可
			if(hasTreeContainerPerms(Section.class.getName(), s.getId(), ObjectOperation.UPDATE_CONTENT)){
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
