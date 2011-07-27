package com.m3958.firstgwt.accesschecker;

import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.model.Department;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.server.types.ObjectOperation;


public class LgbChecker extends BaseChecker<Lgb>{

	@Override
	protected boolean canAdd() {
		return hasTreeContainerPerms(Department.class.getName(), reqPs.getIntegerValue(LgbField.DEPARTMENT_ID.getEname()), ObjectOperation.ADD_CONTENT);
	}

	@Override
	protected boolean canFetch() {
			switch (getSubOpName()) {
			case FETCH_ONE:
				return hasTreeContainerPerms(Department.class.getName(), getModel().getDepartmentId(), ObjectOperation.FETCH_CONTENT);
			default:
				String d = reqPs.getStringValue(LgbField.DEPARTMENT_IDS.getEname());
				if(d == null || d.isEmpty())return false;
				String[] ss = d.split(",");
				int did = Integer.parseInt(ss[1]);
				return hasTreeContainerPerms(Department.class.getName(),did,ObjectOperation.FETCH_CONTENT);
			}
	}

	@Override
	protected boolean canRemove() {
		//必须对某个部门有删除内容的权限
		Lgb l = emp.get().find(Lgb.class, reqPs.getModelId());
		return hasTreeContainerPerms(Department.class.getName(), l.getDepartmentId(), ObjectOperation.REMOVE_CONTENT);
	}

	@Override
	protected boolean canUpdate() {
		Lgb l = emp.get().find(Lgb.class, reqPs.getModelId());
		return hasTreeContainerPerms(Department.class.getName(), l.getDepartmentId(), ObjectOperation.UPDATE_CONTENT);
	}

	@Override
	protected boolean canCustom() {
		return false;
	}

	@Override
	protected boolean canMyRpc() {
		return true;
	}

}
