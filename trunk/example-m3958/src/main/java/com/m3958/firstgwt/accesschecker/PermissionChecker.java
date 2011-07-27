package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.client.types.PermissionExtraFields;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.model.Permission;
import com.m3958.firstgwt.model.Role;



public class PermissionChecker extends BaseChecker<Permission> {

	@Override
	protected boolean canAdd() {
		return false;
	}

	@Override
	protected boolean canFetch() {
		if(su.isSuperman())return true;
		
		switch (getSubOpName()) {
		case FETCH_ONE_TO_MANY://對象的creator才可以查看權限
			return creatorCheck(reqPs.getRelationModelName(), reqPs.getRelationModelId());
		default:
			break;
		}
		String s = reqPs.getStringValue(PermissionExtraFields.PERMISSION_OWNER_ID);
		if( s!= null && !s.isEmpty()){
			return canFetchMyOwnPermissions();
		}
		
		if(SmartSubOperationName.MANY_TO_MANY.toString().equals(reqPs.getSubOpType())){
			return canFetchHisPermissions();
		}
		
		if(reqPs.getCreatorIdFromIds() != SmartConstants.NONE_EXIST_MODEL_ID){
			return canFetchMyCreatedPermissions();
		}

		return false;
	}
	




	private boolean canFetchHisPermissions() {
		if(Role.class.getName().equals(reqPs.getRelationModelName())){
			return true;
		}
		return false;
	}

	private boolean canFetchMyCreatedPermissions() {
		if(su.getUserId() == reqPs.getCreatorIdFromIds())
			return true;
		return false;
	}

	private boolean canFetchMyOwnPermissions() {
		int i = Integer.parseInt(reqPs.getStringValue(PermissionExtraFields.PERMISSION_OWNER_ID));
		if(su.getUserId() == i){
			return true;
		}
		return false;
	}

	@Override
	protected boolean canRemove() {
		//权限的创建者。
		if(creatorCheck(Permission.class.getName(), reqPs.getModelId())){
			return true;
		}
		return false;
	}

	@Override
	protected boolean canUpdate() {
		return false;
	}

	@Override
	protected boolean canCustom() {
		return false;
	}

	@Override
	protected boolean canMyRpc() {
		return false;
	}



}
