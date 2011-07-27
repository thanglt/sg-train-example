package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.House;



public class HouseChecker extends BaseChecker<House> {

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
		return false;
	}

	@Override
	protected boolean canMyRpc() {
		return false;
	}



}
