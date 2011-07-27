package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.FieldEnum;



public class HtmlCssChecker extends BaseChecker<FieldEnum> {

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
