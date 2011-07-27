package com.m3958.firstgwt.dao;

import java.util.List;

import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Role;


public class RoleDao  extends BaseDao<Role>{

	public RoleDao() {
		super(Role.class);
	}


	public Role findByEname(String roleEname) {
		Role r = null;
		
		try {
			r = (Role) emp.get().createQuery("SELECT r FROM Role r WHERE r.ename = :ename").
			setParameter("ename", roleEname).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return r;
	}


	@Override
	public Integer smartCustomCount() {
		return null;
	}


	@Override
	public List<BaseModel> smartCustomFetch() {
		return null;
	}


	@Override
	public List<Role> smartNamedQueryFetch() {
		return null;
	}


	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}







}
