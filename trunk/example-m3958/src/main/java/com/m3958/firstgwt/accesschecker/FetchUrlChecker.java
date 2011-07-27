package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.FetchUrl;



public class FetchUrlChecker extends BaseChecker<FetchUrl> {

	@Override
	protected boolean canAdd() {
		return false;
	}

	@Override
	protected boolean canFetch() {
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
