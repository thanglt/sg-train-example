package com.m3958.firstgwt.utils;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.model.Permission;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.server.types.HasObjectPermission;
import com.m3958.firstgwt.server.types.ObjectOperation;

public class ObjectPermissionUtil {

	public static void createObjectPermissions(HasObjectPermission hp){
		for(ObjectOperation oo : hp.getPossibleOperations()){
			Permission p = new Permission();
			p.setCreatedAt(new Date());
			p.setName(hp.getOname() + ":" + oo.getDisplayName());
			p.setOpCode(oo);
			p.setCreator(hp.getCreator());
			hp.addObjectPermission(p);
		}
	}
	
	public static void createObjectRoles(HasObjectPermission hp){
		for(ObjectRoleName orn : hp.getPossibleRoleNames()){
			if(isRoleNameExist(hp,orn.getDisplayName()))continue;
			createObjectRole(hp,orn);
		}
	}
	
	
	public static Permission getPermission(HasObjectPermission hp,ObjectOperation opCode){
		for(Permission p :hp.getObjectPermissions()){
			if(p.getOpCode() == opCode){
				return p;
			}
		}
		return null;
	}

	public static Role createObjectRole(HasObjectPermission hp,ObjectRoleName orn){
		Role r = new Role();
		r.setCname(hp.getOname() + ":" + orn.getDisplayName());
		r.setOrname(orn.toString());
		r.setCreator(hp.getCreator());
		ObjectOperation[] ops;
		List<Permission> ps = new ArrayList<Permission>();;
		switch (orn) {
		case OWNER://所有权限
			for(Permission p : hp.getObjectPermissions()){//getObjectPermissions可能得到的不是真的list。
				ps.add(p);
			}
			r.setPermissions(ps);
			break;
		case READER:
			ops = new ObjectOperation[]{ObjectOperation.FETCH,
					ObjectOperation.LIST_CHILDREN};
			for(ObjectOperation op : ops){
				ps.add(hp.getPermission(op));
			}
			r.setPermissions(ps);
			break;
		case CONTENT_READER:
			ops = new ObjectOperation[]{ObjectOperation.FETCH,ObjectOperation.LIST_CONTENT,
					ObjectOperation.LIST_CHILDREN,ObjectOperation.FETCH_CONTENT};
			for(ObjectOperation op : ops){
				ps.add(hp.getPermission(op));
			}
			r.setPermissions(ps);
			break;
		case CONTENT_AUDIT:
			ops = new ObjectOperation[]{ObjectOperation.AUDIT_CONTENT};
			for(ObjectOperation op : ops){
				ps.add(hp.getPermission(op));
			}
			r.setPermissions(ps);
			break;
		case CONTENT_EDITOR:
			ops = new ObjectOperation[]{ObjectOperation.FETCH,ObjectOperation.LIST_CONTENT,
					ObjectOperation.LIST_CHILDREN,ObjectOperation.FETCH_CONTENT,ObjectOperation.ADD_CHILD,
					ObjectOperation.ADD_CONTENT,ObjectOperation.REMOVE_CHILD,ObjectOperation.REMOVE_CONTENT,ObjectOperation.UPDATE_CONTENT};
			for(ObjectOperation op : ops){
				ps.add(hp.getPermission(op));
			}
			r.setPermissions(ps);			
			break;
		case SITE_EDITOR:
			ops = new ObjectOperation[]{ObjectOperation.FETCH};
			for(ObjectOperation op : ops){
				ps.add(hp.getPermission(op));
			}
			r.setPermissions(ps);			
			break;
		case CONTENT_ADDER:
			ops = new ObjectOperation[]{ObjectOperation.FETCH,ObjectOperation.LIST_CONTENT,ObjectOperation.FETCH_CONTENT,ObjectOperation.ADD_CONTENT};
			for(ObjectOperation op : ops){
				ps.add(hp.getPermission(op));
			}
			r.setPermissions(ps);			
			break;
		case EDITOR:
			ops = new ObjectOperation[]{ObjectOperation.FETCH,ObjectOperation.LIST_CHILDREN,ObjectOperation.FETCH_CONTENT};
			for(ObjectOperation op : ops){
				ps.add(hp.getPermission(op));
			}
			r.setPermissions(ps);			
			break;
			
		default:
			r = null;
			break;
		}
		if(r != null)hp.addObjectRole(r);
		return r;
	}
	
	public static Role getObjectRole(HasObjectPermission hp,ObjectRoleName orn){
		for(Role r : hp.getObjectRoles()){
			if(r.getCname().split(":")[1].equals(orn.getDisplayName())){
				return r;
			}
		}
		return null;
	}
	
	private static boolean isRoleNameExist(HasObjectPermission hp,String roleName){
		for(Role r : hp.getObjectRoles()){
			if(roleName.equals(r.getCname()))return true;
		}
		return false;
	}

	public static boolean addObjectRole(List<Role> objectRoles, Role r) {
		if(r == null)return false;
		if(objectRoles.contains(r)){
			return false;
		}else{
			objectRoles.add(r);
			return true;
		}
	}
	
	public static boolean addObjectPermission(List<Permission> objectPermissions, Permission p) {
		if(p == null)return false;
		if(objectPermissions.contains(p)){
			return false;
		}else{
			objectPermissions.add(p);
			return true;
		}
	}
}
