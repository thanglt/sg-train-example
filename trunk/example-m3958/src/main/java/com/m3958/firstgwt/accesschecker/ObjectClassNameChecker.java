package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.ObjectClassName;



public class ObjectClassNameChecker extends BaseChecker<ObjectClassName> {

	@Override
	protected boolean canAdd() {
		return false;
	}

	@Override
	protected boolean canFetch() {
		if(isSuperSupermanLogin())return true;
		return false;
	}

	@Override
	protected boolean canRemove() {
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
