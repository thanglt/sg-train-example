package com.mycompany.gwt.sales.client.tools;

import com.google.inject.Singleton;
import com.mycompany.gwt.sales.client.dto.ResourceDto;
import com.mycompany.gwt.sales.client.dto.RoleDto;
import com.mycompany.gwt.sales.client.dto.UserDto;

import java.util.Set;

/**
 * 保存 Client 端用户信息
 */
@Singleton
public class UserTools {

    public static UserDto loginUser;
//    public static UserElseInfo

    public static boolean hasResource(String resourceName) {
        if (loginUser == null) {
            return false;
        }
        Set<RoleDto> roleSet = loginUser.getRoleSet();
        for (RoleDto role : roleSet) {
            Set<ResourceDto> resourceSet = role.getResourceSet();
            for (ResourceDto resource : resourceSet) {
                if (resource.getName().equals(resourceName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasRole(String roleValue) {
        if (loginUser == null) {
            return false;
        }
        Set<RoleDto> roleSet = loginUser.getRoleSet();
        for (RoleDto role : roleSet) {
            if (roleValue.equals(role.getValue())) {
                return true;
            }
        }
        return false;
    }
}
