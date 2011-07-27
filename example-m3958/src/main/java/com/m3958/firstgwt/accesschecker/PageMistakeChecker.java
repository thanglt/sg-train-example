package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.WebHost;


public class PageMistakeChecker extends BaseChecker<WebHost> {

	@Override
	protected boolean canAdd() {
		int i = reqPs.getRelationModelId();
		if(isSiteOwner(i))return true;
		return false;
	}

	@Override
	protected boolean canFetch() {
		int i = reqPs.getRelationModelId();
		if(isSiteOwner(i))return true;
		return false;
	}

	@Override
	protected boolean canRemove() {
		int sid = getModel().getWebSite().getId();
		if(isSiteOwner(sid))return true;
		return false;
	}

	@Override
	protected boolean canUpdate() {
		int sid = getModel().getWebSite().getId();
		if(isSiteOwner(sid))return true;
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
