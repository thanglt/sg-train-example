package com.m3958.firstgwt.accesschecker;


import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.Vote;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.ObjectOperation;





public class VoteChecker extends BaseChecker<Vote> {

	@Override
	protected boolean canAdd() {
		int vid = reqPs.getParentId();
		if(vid == SmartConstants.NONE_EXIST_MODEL_ID){
			return true;
		}
		Vote v = emp.get().find(Vote.class, vid);
		if(rso.isCreator(v)){
			return true;
		}
		return hasObjectPerms(WebSite.class.getSimpleName(), getSiteIdFromPara(), ObjectOperation.ADD_CONTENT);
	}

	@Override
	protected boolean canCustom() {
		return false;
	}

	@Override
	protected boolean canFetch() {
		return hasObjectPerms(WebSite.class.getSimpleName(), getSiteIdFromPara(), ObjectOperation.FETCH_CONTENT);
	}

	@Override
	protected boolean canMyRpc() {
		return false;
	}

	@Override
	protected boolean canRemove() {
		if(rso.isCreator(getModel())){
			return true;
		}
		return hasObjectPerms(WebSite.class.getSimpleName(), getSiteIdFromPara(), ObjectOperation.REMOVE_CONTENT);
	}

	@Override
	protected boolean canUpdate() {
		if(rso.isCreator(getModel())){
			return true;
		}
		return hasObjectPerms(WebSite.class.getSimpleName(), getSiteIdFromPara(), ObjectOperation.UPDATE_CONTENT);
	}

}
