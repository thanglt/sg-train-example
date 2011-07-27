package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.Link;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.ObjectOperation;





public class LinkChecker extends BaseChecker<Link> {

	@Override
	protected boolean canAdd() {
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
		return hasObjectPerms(WebSite.class.getSimpleName(), getSiteIdFromPara(), ObjectOperation.REMOVE_CONTENT);
	}

	@Override
	protected boolean canUpdate() {
		return hasObjectPerms(WebSite.class.getSimpleName(), getSiteIdFromPara(), ObjectOperation.UPDATE_CONTENT);
	}

}
