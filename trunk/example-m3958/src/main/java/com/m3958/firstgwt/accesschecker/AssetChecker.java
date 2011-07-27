package com.m3958.firstgwt.accesschecker;


import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartParameterName;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.AssetFolder;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.ObjectOperation;



public class AssetChecker extends BaseChecker<Asset> {

	@Override
	protected boolean canAdd() {
		return false;
	}

	@Override
	protected boolean canFetch() {
		switch (getSubOpName()) {
		case FETCH_ONE_TO_MANY:
			if(Lgb.class.getName().equals(reqPs.getRelationModelName()))return true;
		case MANY_TO_MANY:
			if(SmartConstants.NONE_EXIST_MODEL_ID == reqPs.getRelationModelId())return true;
			if(AssetFolder.class.getName().equals(reqPs.getRelationModelName())){
				AssetFolder s = emp.get().find(AssetFolder.class, reqPs.getRelationModelId());
				if(isSiteEditor(s.getSiteId()) || isSiteOwner(s.getSiteId()))return true;
			}
			return hasTreeContainerPerms(AssetFolder.class.getName(), reqPs.getRelationModelId(), ObjectOperation.FETCH_CONTENT);
		case NOT_CREATOR_HAS_PERMISSION:
			return true;
		case NOT_CREATOR_HAS_GPERMISSION:
			return true;
		case FETCH_ONE:
			boolean b = creatorCheck(Asset.class.getName(), reqPs.getModelId());
			if(b)return true;
//			Asset a = (Asset) getDao().find(reqPs.getModelId());
			b =  hasObjectPerms(Asset.class.getSimpleName(), reqPs.getModelId(),ObjectOperation.FETCH);
			if(b)return true;
//			List<AssetFolder> folders =  a.getFolders();
//			if(folders.size() == 0)return false;
//			for(AssetFolder f:folders){
//				if(hasTreeContainerPerms(AssetFolder.class.getName(),f.getId(),ObjectOperation.FETCH_CONTENT)){
//					return true;
//				}
//			}
//			break;
			
			boolean cked = false;
			for(AssetFolder s : getModel().getFolders()){
				if(!cked){
					if(isSiteEditor(s.getSiteId()) || isSiteOwner(s.getSiteId())){
						return true;
					}else{
						cked = true;
					}
				}
				if(hasTreeContainerPerms(AssetFolder.class.getName(), s.getId(), ObjectOperation.FETCH_CONTENT))return true;	
			}
			return false;
		default://如果有fetchOwner存在，是安全的。
			if(reqPs.getFetchOwner())return true;
		}
		return false;
	}

	@Override
	protected boolean canRemove() {
		if(getModel().getSiteId() == 0 || getModel().getSiteId() == SmartConstants.NONE_EXIST_MODEL_ID)return true;
		if(isSiteEditor(getModel().getSiteId()) || isSiteOwner(getModel().getSiteId()))return true;
		if(isObjectOwner())return true;
		for(AssetFolder s : getModel().getFolders()){
			if(hasTreeContainerPerms(AssetFolder.class.getName(), s.getId(), ObjectOperation.REMOVE_CONTENT)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean canUpdate() {
		if(getModel().getSiteId() == 0 || getModel().getSiteId() == SmartConstants.NONE_EXIST_MODEL_ID)return true;
		if(isSiteEditor(getModel().getSiteId()) || isSiteOwner(getModel().getSiteId()))return true;
		for(AssetFolder s : getModel().getFolders()){//对其中一个目录由update权限即可
			if(hasTreeContainerPerms(AssetFolder.class.getName(), s.getId(), ObjectOperation.UPDATE_CONTENT)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean canCustom() {
		switch (getSubOpName()) {
		case FETCH_RELATION_COUNT:
			return true;
		case MANAGE_RELATION:
			int rid = reqPs.getIdValue(SmartParameterName.EXTRAPARAS);
			if(isSiteEditor(rid) || isSiteOwner(rid))return true;
			if(WebSite.class.getName().equals(reqPs.getRelationModelName())){
				return hasObjectPerms(WebSite.class.getSimpleName(), rid, ObjectOperation.ADD_CONTENT);
			}
			break;
		case REMOTE_ASSET:
			int siteId = reqPs.getIdValue(CommonField.SITE_ID.getEname());
			if(siteId == SmartConstants.NONE_EXIST_MODEL_ID)return false;
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
