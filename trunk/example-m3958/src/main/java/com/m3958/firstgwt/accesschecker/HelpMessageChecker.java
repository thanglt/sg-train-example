package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.HelpMessage;



public class HelpMessageChecker extends BaseChecker<HelpMessage> {

	@Override
	protected boolean canAdd() {
		return false;
	}

	@Override
	protected boolean canFetch() {
		return true;
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
