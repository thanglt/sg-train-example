package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.server.types.TreeModel;

public abstract class TreeChecker<T extends BaseModel<T>> extends BaseChecker<T>{

	@Override
	protected boolean canAdd() {
		if(SmartConstants.NONE_EXIST_MODEL_ID == reqPs.getParentId()){
			return true;
		}
		return hasTreeContainerPerms(reqPs.getModelName(), reqPs.getParentId(), ObjectOperation.ADD_CHILD);
	}

	@Override
	protected boolean canCustom() {
		return false;
	}

	@Override
	protected boolean canFetch() {
		switch (getSubOpName()) {
		case FETCH_CHILDREN:
			if(reqPs.getParentId() == SmartConstants.NONE_EXIST_MODEL_ID)return true;
			return hasTreeContainerPerms(reqPs.getModelName(), reqPs.getParentId(), ObjectOperation.LIST_CHILDREN);
		case MANY_TO_MANY:
			return true;
		default:
			return false;
		}
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
