package com.m3958.firstgwt.server.types;

import java.util.List;


import com.m3958.firstgwt.client.types.ObjectRoleName;
import com.m3958.firstgwt.model.Permission;
import com.m3958.firstgwt.model.Role;
import com.m3958.firstgwt.model.User;

public interface HasObjectPermission {
	List<Permission> getObjectPermissions();
	void setObjectPermissions(List<Permission> permissions);
	void createObjectPermissions();
	void createObjectRoles();
	Role createObjectRole(ObjectRoleName orn);
	List<Role> getObjectRoles();
	Role getObjectRole(ObjectRoleName orn);
	Permission getPermission(ObjectOperation opCode);
	String getOname();
	User getCreator();
	ObjectRoleName[] getPossibleRoleNames();
	ObjectOperation[] getPossibleOperations();
	boolean addObjectRole(Role r);
	boolean addObjectPermission(Permission p);
}
