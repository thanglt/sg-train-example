package com.m3958.firstgwt.accesschecker;


import java.util.List;

import javax.persistence.Query;

import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.Permission;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.utils.StringUtils;

public class RoleChecker extends BaseChecker<Role>{

	@Override
	protected boolean canAdd() {
		return true;
	}

	@Override
	protected boolean canFetch() {
		if(su.isSuperman())return true;
		
		switch (getSubOpName()) {
		case MANY_TO_MANY:
			if(User.class.getName().equals(reqPs.getRelationModelName())){
				
			}
			break;

		default:
			return su.getUserId() == reqPs.getCreatorIdFromIds();
		}
		
		//查看某个用户拥有的角色
		if(SmartSubOperationName.MANY_TO_MANY == getSubOpName() && User.class.getName().equals(reqPs.getRelationModelName())){
			return canFetchUserRoles();
		}
		
		//查看某个组拥有的角色。
		if(SmartSubOperationName.MANY_TO_MANY == getSubOpName() && Group.class.getName().equals(reqPs.getRelationModelName())){
			return canFetchGroupRoles();
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean canFetchGroupRoles() {
		//group是否是我所创建？或者我所创建的用户所创建。
		String qs = "SELECT G FROM Group AS G WHERE G.creatorIds Like :uidStr AND G.id = :gid";
		Query q = emp.get().createQuery(qs);
		q.setParameter("uidStr", "%," + su.getUserId() + ",%");
		q.setParameter("gid", reqPs.getRelationModelId());
		List<Group> groups = q.getResultList();
		if(groups.size() > 0)return true;		
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean canFetchUserRoles() {
		//这个用户是否是我所创建。
		String qs = "SELECT u FROM User AS u WHERE u.creatorIds Like :uidStr AND u.id = :uid";
		Query q = emp.get().createQuery(qs);
		q.setParameter("uidStr", "%," + su.getUserId() + ",%");
		q.setParameter("uid", reqPs.getRelationModelId());
		List<User> users = q.getResultList();
		if(users.size() > 0)return true;
		
		//查看自己所有用的角色
		if(su.getUserId() == reqPs.getRelationModelId())return true;
		
		
		return false;
	}


	@Override
	protected boolean canRemove() {
		return isMycreatedRole();
	}

	@Override
	protected boolean canUpdate() {
		return isMycreatedRole();
	}



	@Override
	protected boolean canCustom() {
		switch (getSubOpName()) {
		case MANAGE_RELATION:
			if(Permission.class.getName().equals(reqPs.getRelationModelName())){
				return canChangeRole();
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private boolean isMycreatedRole() {
		String qs = "SELECT r FROM Role AS r WHERE r.creatorIds LIKE :creatorIds AND r = :role";
		Query q = emp.get().createQuery(qs);
		q.setParameter("creatorIds", "%," + su.getUserId() + ",%");
		q.setParameter("role", getModel());
		List<Role> rs = q.getResultList();
		if(rs.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean canChangeRole() {
		//超级用户可以赋予任何权限
		if(su.isSuperman()){
			return true;
		}
		
		//被管理的role是否是我创建？
		if(!isMycreatedRole())return false;
		
		int[] ints = StringUtils.getIdArray(reqPs.getExtraParas(), ",");
		String sqls = StringUtils.getSQLIntInClause(ints);
		
		//permission是否是我所创建
		String qs = "SELECT p FROM Permission AS p WHERE p.creatorIds LIKE :creatorIds AND p.id IN " + sqls;
		Query q = emp.get().createQuery(qs);
		q.setParameter("creatorIds", "%," + su.getUserId() + ",%");
		List<Permission> ps = q.getResultList();
		if(ps.size() == ints.length)return true;
		
		//permission是否是我所拥有？
		qs = "SELECT DISTINCT p FROM User AS u , IN(u.roles) r,IN(r.permissions) p WHERE p.id IN " + sqls;
		q = emp.get().createQuery(qs);
		ps = q.getResultList();
		if(ps.size() == ints.length){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected boolean canMyRpc() {
		return false;
	}
	

}
