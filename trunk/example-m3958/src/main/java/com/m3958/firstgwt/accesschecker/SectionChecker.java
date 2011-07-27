package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.server.types.TreeModel;


public class SectionChecker extends TreeChecker<Section> {

	
	@Override
	protected boolean canFetch() {
		int i = reqPs.getIdValue(CommonField.SITE_ID.getEname());
		if(isSiteEditor(i) || isSiteOwner(i))return true;
		switch (getSubOpName()) {
		case FETCH_CHILDREN:
			if(reqPs.getParentId() == SmartConstants.NONE_EXIST_MODEL_ID)return true;
			return hasTreeContainerPerms(reqPs.getModelName(), reqPs.getParentId(), ObjectOperation.LIST_CHILDREN);
		case MANY_TO_MANY:
			return true;
		case NO_SUB_OPERATION:
			if(reqPs.getModelId() != SmartConstants.NONE_EXIST_MODEL_ID){
				return true;
			}
		default:
			return false;
		}
	}
	
	@Override
	protected boolean canAdd() {
		int i = reqPs.getIdValue(CommonField.SITE_ID.getEname());
		if(isSiteEditor(i) || isSiteOwner(i))return true;
		if(SmartConstants.NONE_EXIST_MODEL_ID == reqPs.getParentId()){
			return true;
		}
		return hasTreeContainerPerms(reqPs.getModelName(), reqPs.getParentId(), ObjectOperation.ADD_CHILD);
	}

	@Override
	protected boolean canCustom() {
		switch (getSubOpName()) {
		case MANAGE_RELATION:
			Section sc = getModel();
			if(isSiteEditor(sc.getSiteId()) || isSiteOwner(sc.getSiteId()))return true;
			return hasTreeContainerPerms(reqPs.getModelName(), reqPs.getModelId(), ObjectOperation.ADD_CONTENT);
		case SHARE_TO_USER:
			Section s = emp.get().find(Section.class, reqPs.getModelId());
			if(isSiteOwner(s.getSiteId())){
				return true;
			}
			return rso.hasObjectRole(s, ObjectRoleName.OWNER);
		default:
			break;
		}
		return false;

	}


	@Override
	protected boolean canMyRpc() {
		return false;
	}

	@Override
	protected boolean canRemove() {
		int i = reqPs.getIdValue(CommonField.SITE_ID.getEname());
		if(isSiteEditor(i) || isSiteOwner(i))return true;
		try {
			String modelName = reqPs.getModelName();
			Class c = Class.forName(modelName);
			int oid = reqPs.getModelId();
			TreeModel t = emp.get().find(c,oid);
			
			//僅僅更新內容
			if(t.getParentId() == reqPs.getParentId()){
				boolean b = hasTreeContainerPerms(modelName, oid, ObjectOperation.REMOVE);
				if(b)return true;
			}else{//只有主人才能移動目錄
				if(creatorCheck(modelName, oid))return true;
			}
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected boolean canUpdate() {
		int i = reqPs.getIdValue(CommonField.SITE_ID.getEname());
		if(isSiteEditor(i) || isSiteOwner(i))return true;
		try {
			String modelName = reqPs.getModelName();
			Class c = Class.forName(modelName);
			int oid = reqPs.getModelId();
			TreeModel t = emp.get().find(c,oid);
			
			//僅僅更新內容
			if(t.getParentId() == reqPs.getParentId()){
				boolean b = hasTreeContainerPerms(modelName, oid, ObjectOperation.UPDATE);
				if(b)return true;
			}else{//只有主人才能移動目錄
				if(creatorCheck(modelName, oid))return true;
			}
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	

}
