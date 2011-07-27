package com.m3958.firstgwt.dao;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.model.Department;
import com.m3958.firstgwt.model.Lgb.LgbNamedQueries;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.session.SessionUser;

public class DepartmentChangeStrategy extends TreeChangeStrategy implements ModelChangeStrategy<Department> {
	
	@Inject
	SessionUser su;

	@Override
	public boolean extraPersistTask(Department model){
		createPerRoleAsignToUser(model);
		return true;
	}


	@Override
	public boolean extraRemoveTask(Department model) {
		if((model).getChildren().size() > 0){
			getErrors().addError(new Error("请先删除子目录！", ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		
		Long l = (Long) emp.get().createNamedQuery(LgbNamedQueries.COUNT_BY_DEPARTMENT).setParameter(1, model).getSingleResult();
		if(l > 0){
			getErrors().addError(new Error("请先删除内容！",  ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		return true;
	}
	
	
	@Override
	public boolean extraUpdateTask(Department model,Department newModel){
		return true;
	}


	@Override
	public boolean afterPersist(Department model) {
		return false;
	}

}
