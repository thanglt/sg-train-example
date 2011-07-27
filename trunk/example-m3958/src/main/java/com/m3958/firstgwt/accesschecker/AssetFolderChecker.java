package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.AssetFolder;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.server.types.TreeModel;


public class AssetFolderChecker extends TreeChecker<AssetFolder> {

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
	protected boolean canMyRpc() {
		return false;
	}

	@Override
	protected boolean canRemove() {
		return false;
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
	
	@Override
	protected boolean canCustom() {
		switch (getSubOpName()) {
		case MANAGE_RELATION:
			if(getModel() == null)return true;
			int i = getModel().getSiteId();
			if(isSiteEditor(i) || isSiteOwner(i))return true;
			int afid = reqPs.getModelId();
			if(afid == SmartConstants.NONE_EXIST_MODEL_ID){
				return true;
			}else{
				return hasObjectPerms(AssetFolder.class.getSimpleName(), afid, ObjectOperation.ADD_CONTENT);
			}
		default:
			break;
		}
		return false;
	}
	
}
