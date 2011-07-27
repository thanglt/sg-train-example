package com.m3958.firstgwt.accesschecker;


import java.util.List;

import javax.persistence.Query;

import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.UserField;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.model.User;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.ObjectOperation;
import com.m3958.firstgwt.utils.StringUtils;


public class UserChecker extends BaseChecker<User> {
	
	@Override
	protected boolean canRemove() {
		//不能删除自己。
		if(su.getUserId() == getModel().getId())return false;
		
		//如果是超级用户，有2种可能需要考虑，如果想编辑别的超级用户，并且自己不是超超级用户，那么返回NO，其它的都是yes。
		if(su.isSuperman()){
			//不能删除同级别的超级用户
			if(isSuperman(getModel())){
					return false;
			}
			return true;
		}else{//其他用户
			//如果对user所属的group有remove_content的权限。
			for(Group g:getModel().getGroups()){
//				if(canGroupAction(ObjectOperation.REMOVE_CONTENT,g.getId())){
				if(hasTreeContainerPerms(Group.class.getName(), g.getId(), ObjectOperation.REMOVE_CONTENT)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected boolean canUpdate() {
		//demo用户不可以更改
		if("demo".equals(getModel().getLoginName()))return false;
		//如果是超级用户，有2种可能需要考虑，如果想编辑别的超级用户，并且自己不是超超级用户，那么返回NO，其它的都是yes。
		if(su.isSuperman()){
			//如果是超超级用户
			if(isSuperSupermanLogin()){
				//如果编辑的是自己。
				if(SmartConstants.SUPERMAN_USER_NAME.equals(getModel().getLoginName())){
					return false;
				}
				return true;
			}
		//如果被编辑的也是超级用户，那么必须是自己编辑自己。
			if(isSuperman(getModel())){
				if(getModel().getId() ==su.getUserId()){
					return true;
				}else{
					return false;
				}
			}
			return true;
		}else{
			if(getModel().getId() == su.getUserId())return true;
			for(Group g:getModel().getGroups()){
				if(hasTreeContainerPerms(Group.class.getName(), g.getId(), ObjectOperation.UPDATE_CONTENT)){
					return true;
				}
			}
		}
		
		return false;
	}


	@Override
	protected boolean canFetch(){
		if(su.isSuperman())return true;
		switch (getSubOpName()) {
		case MANY_TO_MANY:
			//creator has fetch permission
			int gid = reqPs.getRelationModelId();
			Group g = emp.get().find(Group.class, gid);
			if(g != null && rso.isCreator(g)){
				return true;
			}
			if(Group.class.getName().equals(reqPs.getRelationModelName())){
				return hasTreeContainerPerms(Group.class.getName(), gid, ObjectOperation.LIST_CONTENT);
			}
			break;
		case FETCH_SHARED_USERS:
			if(WebSite.class.getName().equals(reqPs.getRelationModelName())){
				return isSiteOwner(reqPs.getRelationModelId());
			}else if(Section.class.getName().equals(reqPs.getRelationModelName())){
				Section s = emp.get().find(Section.class, reqPs.getRelationModelId());
				if(isSiteOwner(s.getSiteId())){
					return true;
				}
				return rso.hasObjectRole(s, ObjectRoleName.OWNER);
			}else if(Group.class.getName().equals(reqPs.getRelationModelName())){
				//如果是组的创建者，可以。
				int ggid = reqPs.getRelationModelId();
				Group gg = emp.get().find(Group.class, ggid);
				if(rso.isCreator(gg)){
					return true;
				}
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	private boolean isSuperman(User u) {
		for(Role r:u.getRoles()){
			if("superman".equals(r.getOrname()))return true;
		}
		return false;
	}

	@Override
	protected boolean canAdd() {
		if(su.isSuperman())return true;
		int groupId = reqPs.getIntegerValue(UserField.INIT_GROUP_ID.getEname());
		if(groupId == 0)return true;
		return hasTreeContainerPerms(Group.class.getName(), groupId, ObjectOperation.ADD_CONTENT);
	}




	@Override
	protected boolean canCustom() {
		//我的角色可以赋给别人，我创建的角色也可以赋给别人。
		switch (getSubOpName()) {
		case MANAGE_RELATION:
			if(Role.class.getName().equals(reqPs.getRelationModelName())){
				return canChangeRole();
			}
			break;
		default:
			break;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean canChangeRole() {
		
		//TODO 没有检验用户，不过暂时问题不大。
		
		String rids = reqPs.getExtraParas();
		int[] ridAry = StringUtils.getIdArray(rids, ",");
		
		User u = emp.get().find(User.class, reqPs.getModelId());
		
		String qs;
		Query q;
		List<Role> rs;
		
		//如果被管理的是超超级用户，那么不能删除超超级用户的超级用户角色
		if(isSuperSuperman(u) && !reqPs.isAddRelation()){
			if(hasSupermanRole(ridAry))return false;
		}
		
		//如果被操作的角色是superman角色，那么只能是超超级用户才能进行。
		if(hasSupermanRole(ridAry)){
			if(isSuperSupermanLogin()){
				return true;
			}else{
				return false;
			}
		}
		
		//是不是我创建的
		qs = "SELECT r FROM Role AS r WHERE r.creatorIds LIKE :uidLike AND r.id IN " + StringUtils.getSQLIntInClause(ridAry);
		q = emp.get().createQuery(qs);
		q.setParameter("uidLike", "%," + su.getUserId() + ",%");
		rs = q.getResultList();
		if(rs.size()>0)return true;

		//是不是我拥有的
		qs = "SELECT r FROM User AS u ,IN(u.roles) r WHERE u.id = :uid AND r.id IN " + StringUtils.getSQLIntInClause(ridAry);
		q = emp.get().createQuery(qs);
		q.setParameter("uid",su.getUserId());
		rs = q.getResultList();
		if(rs.size()>0)return true;

		return false;
	}




	@SuppressWarnings("unchecked")
	private boolean hasSupermanRole(int[] ridAry) {
		String qs;
		Query q;
		List<Role> rs;
		qs = "SELECT r FROM Role AS r WHERE r.ename = :ename AND r.id IN " + StringUtils.getSQLIntInClause(ridAry);
		q = emp.get().createQuery(qs);
		q.setParameter("ename", SmartConstants.SUPERMAN_ROLE_NAME);
		rs = q.getResultList();
		if(rs.size()>0){
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
