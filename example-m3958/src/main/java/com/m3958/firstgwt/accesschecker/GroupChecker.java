package com.m3958.firstgwt.accesschecker;

import java.util.List;

import javax.persistence.Query;

import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.utils.StringUtils;


public class GroupChecker extends TreeChecker<Group> {

	@Override
	protected boolean canMyRpc() {
		return false;
	}
	
	
	//group中对于role的关系的维护是特殊的，比如独立出来。因为它涉及到role的问题，而role只对用户和Group有关联。
	@Override
	protected boolean canCustom() {
		//我的角色可以赋给别人，我创建的角色也可以赋给别人。
		switch (getSubOpName()) {
		case MANAGE_RELATION:
			if(Role.class.getName().equals(reqPs.getRelationModelName())){
				return canAddRemoveRole();
			}else if(User.class.getName().equals(reqPs.getRelationModelName())){
				//加入组，是组的创建者，并且用户也是组创建的，可以
				String uids = reqPs.getExtraParas();
				boolean uc = true;
				for(String s : uids.split(",")){
					try {
						int uid = Integer.parseInt(s);
						User u = emp.get().find(User.class, uid);
						if(!rso.isCreator(u))uc = false;
					} catch (Exception e) {}
				}
				if(rso.isCreator(getModel()) && uc){
					return true;
				}
			}
			break;
		case MQ:
			return true;
		default:
			break;
		}
		return super.canCustom();
	}

	@SuppressWarnings("unchecked")
	private boolean canAddRemoveRole() {
		String rids = reqPs.getExtraParas();
		int[] ridAry = StringUtils.getIdArray(rids, ",");
		
		Query q;
		List<Role> rs;
		
		//如果被操作的角色是superman角色，那么只能是超超级用户才能进行。
		if(hasSupermanRole(ridAry)){
			if(isSuperSupermanLogin()){
				return true;
			}else{
				return false;
			}
		}
		
		//是不是我创建的
		q = emp.get().createNamedQuery(Role.RoleNamedQueries.FIND_MY_CREATED_ROLES);
		q.setParameter(1, "%," + su.getUserId() + ",%");
		rs = q.getResultList();
		
		for(int i:ridAry){
			for(Role r:rs){
				if(i == r.getId()){
					return true;
				}
			}
		}
		//是不是我拥有的
		q = emp.get().createNamedQuery(Role.RoleNamedQueries.FIND_USER_ROLES);
		q.setParameter(1,su.getUserId());
		rs = q.getResultList();
		for(int i:ridAry){
			for(Role r:rs){
				if(i == r.getId()){
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private boolean hasSupermanRole(int[] ridAry) {
		Query q;
		List<Role> rs;
		q = emp.get().createNamedQuery(Role.RoleNamedQueries.FIND_SUPERMAN_ROLE);
		q.setParameter(1, SmartConstants.SUPERMAN_ROLE_NAME);
		rs = q.getResultList();
		for(int i:ridAry){
			for(Role r:rs){
				if(i == r.getId())return true;
			}
		}
		return false;
	}


}
