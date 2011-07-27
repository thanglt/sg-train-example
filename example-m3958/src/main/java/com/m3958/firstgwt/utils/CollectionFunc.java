package com.m3958.firstgwt.utils;

import java.util.List;

import com.m3958.firstgwt.model.Role;

public class CollectionFunc {
	public final static boolean roleNameInRoles(List<Role> roles,String roleName){
		for (Role role : roles) {
			if(role.getOrname().equals(roleName)){
				return true;
			}
		}
		return false;
	}
}
