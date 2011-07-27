package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.ComponentPreference;



public class ComponentPreferenceChecker extends BaseChecker<ComponentPreference> {

	@Override
	protected boolean canAdd() {
		return true;
	}

	@Override
	protected boolean canFetch() {
		return true;
	}

	@Override
	protected boolean canRemove() {
		return true;
	}

	@Override
	protected boolean canUpdate() {
		return true;
	}

	@Override
	protected boolean canCustom() {
		return true;
	}

	@Override
	protected boolean canMyRpc() {
		return false;
	}


}
