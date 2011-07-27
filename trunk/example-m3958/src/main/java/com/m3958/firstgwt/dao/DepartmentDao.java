package com.m3958.firstgwt.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Department;
import com.m3958.firstgwt.model.User;
import com.wideplay.warp.persist.Transactional;

public class DepartmentDao  extends BaseDao<Department> {
	
	@Inject
	public DepartmentDao(DepartmentChangeStrategy task) {
		super(Department.class);
		extraStrategyTask = task;
	}
	

	@Transactional
	public Department remove(Department department) {
		department = emp.get().find(Department.class, department.getId());
		department.setParent(null);
		emp.get().remove(department);
		return department;
	}

	public Department findDepartmentByCname(String cname) {
		String qs = "SELECT o FROM Department AS o WHERE o.cname = :cname";
		Query q = emp.get().createQuery(qs);
		q.setParameter("cname", cname);
		return (Department) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Department> getAllowedDepartments(User u) {
//		Query dq = emp.get().createNamedQuery(Department.DepartmentNamedQueries.FIND_ALLOWED_DEPARTMENTS);
//		dq.setParameter(1, u);
//		return dq.getResultList();
		return new ArrayList<Department>();
	}
	
	/*
	 * 看看department的id是否属于（permmision里的objectidentity），这个permission所属的roles和用户的roles的交集。
	 * 所以必须对所有的department进行授权才能进行操作，这样显然不便利。应该进一步查找是否对于父目录有权限。
	 */
	@SuppressWarnings("unchecked")
	public List<Department> getAllowedDepartments(User u,String action) {
		String ds = "SELECT d FROM Department d WHERE d.id IN (SELECT p.objectIdentity FROM  User u ,IN(u.roles) r, IN(r.permissions) p WHERE u = :user) AND p.operation.name = :opName)";
		Query dq = emp.get().createQuery(ds);
		dq.setParameter("user", u);
		dq.setParameter("opName", action);
		return dq.getResultList();
	}



	
	
	@Transactional
	public Department addDepartment(Department department, Department parent) {
		if(parent!=null)parent = find(parent.getId());
		department.setParent(parent);
		setCreator(department);
		department = persist(department);
		return department;
	}

	@SuppressWarnings("unchecked")
	public List<Department> getDepartments(String departmentIds) {
		String qs = "SELECT d FROM Department AS d WHERE d.parentIds LIKE :parentIds";
		Query q = emp.get().createQuery(qs);
		q.setParameter("parentIds", "%" + departmentIds + "%");
		
		return q.getResultList();
	}


	@Override
	public Integer smartCustomCount() {
		return null;
	}

	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}


	@SuppressWarnings("unchecked")
	public List<Department> getRootDepartments() {
		String qs = "SELECT d FROM Department AS d WHERE d.parent is NULL";
		Query q = emp.get().createQuery(qs);
		return q.getResultList();
	}
	
	public Integer getRootDepartmentsNumber() {
		String qs = "SELECT COUNT(d) FROM Department AS d WHERE d.parent is NULL";
		Query q = emp.get().createQuery(qs);
		Long l = (Long) q.getSingleResult();
		return l.intValue();
	}	
	
	
//	@Override
//	public List<BaseModel> smartFetchChildren(){
//		//如果父id是-1，并且是非superman
//		if(getSmartCriteria().getParentId() == SmartConstants.NONE_EXIST_MODEL_ID && !sessionUser.isSuperman()){
//			return getAllowedNodes(Department.class.getSimpleName(),sessionUser.getUserId());
//		}else{
//			return super.smartFetchChildren();
//		}
//
//	}
//	
//	@Override
//	public Integer smartChildrenRowCount() {
//		if(!sessionUser.isSuperman() && !sessionUser.isSuperman()){
//			return getAllowedNodesNumber(Department.class.getSimpleName(),sessionUser.getUserId());
//		}else{
//			return super.smartChildrenRowCount();
//		}
//	}

	@Override
	public List<Department> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}


}
