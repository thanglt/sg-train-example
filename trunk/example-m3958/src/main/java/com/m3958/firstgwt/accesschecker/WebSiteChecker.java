package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.model.DiskFile;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.ObjectOperation;



public class WebSiteChecker extends BaseChecker<WebSite> {
	
	@Override
	protected boolean canAdd() {
		if(WebSite.class.getName().equals(reqPs.getModelName())){
			return true;
		}else if(DiskFile.class.getName().equals(reqPs.getModelName())){
			return hasObjectPerms(WebSite.class.getSimpleName(), reqPs.getIdValue(CommonField.SITE_ID.getEname()), ObjectOperation.ADD_CONTENT);
		}
		return false;
	}

	@Override
	protected boolean canFetch() {
		switch (getSubOpName()) {
		case FETCH_ONE:
			int siteId = getSiteId();
			return hasObjectPerms(WebSite.class.getSimpleName(), siteId, ObjectOperation.FETCH);
		case HAS_PERMISSION:
			return true;
		default:
			if(DiskFile.class.getName().equals(reqPs.getModelName())){
				return isSiteOwner(reqPs.getIdValue(CommonField.SITE_ID.getEname()));
			}
			return false;
		}
	}

	@Override
	protected boolean canRemove() {
		if(WebSite.class.getName().equals(reqPs.getModelName())){
			int siteId = getSiteId();
			return hasObjectPerms(WebSite.class.getSimpleName(), siteId, ObjectOperation.REMOVE_CONTENT);
		}else if(DiskFile.class.getName().equals(reqPs.getModelName())){
			return hasObjectPerms(WebSite.class.getSimpleName(), reqPs.getIdValue(CommonField.SITE_ID.getEname()), ObjectOperation.REMOVE_CONTENT);
		}
		return false;
	}

	@Override
	protected boolean canUpdate() {
		if(WebSite.class.getName().equals(reqPs.getModelName())){
			int siteId = getSiteId();
			return hasObjectPerms(WebSite.class.getSimpleName(), siteId, ObjectOperation.UPDATE_CONTENT);
		}else if(DiskFile.class.getName().equals(reqPs.getModelName())){
			return hasObjectPerms(WebSite.class.getSimpleName(), reqPs.getIdValue(CommonField.SITE_ID.getEname()), ObjectOperation.UPDATE_CONTENT);
		}
		
		return false;
	}

	@Override
	protected boolean canCustom() {
		SmartSubOperationName sson = SmartSubOperationName.NO_SUB_OPERATION;
		try {
			sson = SmartSubOperationName.valueOf(reqPs.getSubOpType().toUpperCase());
		} catch (Exception e) {}
		switch (sson) {
		case UNZIP:
			return canAdd();
		case PASTER_DISK_FILE:
			return canAdd();
		case FETCH_ONE:
			if(DiskFile.class.getName().equals(reqPs.getModelName())){
				return hasObjectPerms(WebSite.class.getSimpleName(), reqPs.getIdValue(CommonField.SITE_ID.getEname()), ObjectOperation.FETCH_CONTENT);
			}
		case MQ:
			return isSiteOwner(reqPs.getIdValue(CommonField.SITE_ID.getEname()));
		case SHARE_TO_USER:
			return isSiteOwner(reqPs.getModelId());
		default:
			break;
		}
		return false;
	}

	@Override
	protected boolean canMyRpc() {
		return false;
	}
	
	private int getSiteId(){
		WebSite ws = getModel();
		return ws.getId();
	}
}
