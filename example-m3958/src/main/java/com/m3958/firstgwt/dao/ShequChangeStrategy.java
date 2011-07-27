package com.m3958.firstgwt.dao;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Department;
import com.m3958.firstgwt.model.Shequ;

public class ShequChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<Shequ> {
	
	@Override
	public boolean extraPersistTask(Shequ model) {
		setInitDepartment(model);
		return true;
	}

	@Override
	public boolean extraUpdateTask(Shequ model,Shequ newModel) {
		return true;
	}
	
	private boolean setInitDepartment(BaseModel model) {
		int dpid = getReqPs().getRelationModelId();
		Department dp = emp.get().find(Department.class, dpid);
		if(dp != null){
			((Shequ)model).setDepartment(dp);
		}
		return true;
	}

	@Override
	public boolean extraRemoveTask(Shequ model) {
		return true;		
	}

	@Override
	public boolean afterPersist(Shequ model) {
		return false;
	}

}
