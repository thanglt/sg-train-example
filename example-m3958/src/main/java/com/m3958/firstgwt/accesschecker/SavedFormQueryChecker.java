package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.SavedFormQuery;



public class SavedFormQueryChecker extends BaseChecker<SavedFormQuery> {

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
		return true;
	}

	@Override
	protected boolean canMyRpc() {
		return false;
	}


}
