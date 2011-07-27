package com.m3958.firstgwt.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.User;
import com.wideplay.warp.persist.Transactional;

public class GroupDao  extends BaseDao<Group> implements IDao<Group> {
	

	@Inject
	public GroupDao(GroupChangeStrategy task) {
		super(Group.class);
		extraStrategyTask = task;
	}
	
	@Transactional
	public List<User> addUserToGroup(){
		Group g = find(getReqPs().getModelId());
		if(g == null)return null;
		
		String uIdentity = getReqPs().getExtraParas();
		UserDao udao = injector.getInstance(UserDao.class);
		User u = udao.findByLoginNameOrEmailOrMobile(uIdentity);
		List<User> users = new ArrayList<User>();
		if(u!=null){
			u.addGroup(g);
			g.manageRelation(u, true,"", getErrors());
			users.add(u);
		}
		return users; 
	}
	
	public Group findByName(String name){
		String qs = "SELECT g FROM Group AS g WHERE g.name = :name";
		Query q = emp.get().createQuery(qs);
		q.setParameter("name", name);
		try{
			return (Group) q.getSingleResult();
		}catch (Exception e) {
			return null;
		}
	}
	
	public Group findByNameOrId(String nameOrId){
		int id = 0;
		try{
			id = Integer.parseInt(nameOrId);
			if((id+"").equals(nameOrId)){
				return find(id);
			}else{
				return findByName(nameOrId);
			}
		}catch (Exception e) {
			return findByName(nameOrId);
		}
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
	public List<Group> smartNamedQueryFetch() {
		return null;
	}

	@Override
	public Integer smartNamedQueryFetchCount() {
		return null;
	}

}
