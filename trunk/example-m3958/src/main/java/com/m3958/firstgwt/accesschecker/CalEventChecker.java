package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.CalEvent;

public class CalEventChecker extends BaseChecker<CalEvent>{

	@Override
	protected boolean canAdd() {
		return false;
	}

	@Override
	protected boolean canFetch() {
		if(su.getUserId() == reqPs.getRelationModelId()){
			return true;
		}
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
