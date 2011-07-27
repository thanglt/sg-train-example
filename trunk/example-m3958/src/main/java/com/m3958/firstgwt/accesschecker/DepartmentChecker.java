package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.model.Department;


public class DepartmentChecker extends TreeChecker<Department>{

	@Override
	protected boolean canAdd() {
		return false;
	}

	@Override
	protected boolean canRemove() {
		if(creatorCheck(Department.class.getName(), reqPs.getModelId())){
			return true;
		}
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
