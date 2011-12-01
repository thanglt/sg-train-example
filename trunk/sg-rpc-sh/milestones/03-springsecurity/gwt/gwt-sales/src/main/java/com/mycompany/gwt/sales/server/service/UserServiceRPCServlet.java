package com.mycompany.gwt.sales.server.service;

import com.mycompany.gwt.sales.client.dto.RoleDto;
import com.mycompany.gwt.sales.client.dto.UserDto;
import com.mycompany.gwt.sales.client.service.UserServiceRPC;
import com.mycompany.java.common.entity.Role;
import com.mycompany.java.common.entity.User;
import com.mycompany.java.common.service.UserService;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userServiceRPCServlet")
public class UserServiceRPCServlet implements UserServiceRPC {

    private static final Logger log = Logger.getLogger(UserServiceRPCServlet.class);

    @Resource
    private UserService userService;

    public List<UserDto> getAll() {
        return new ArrayList();   //TODO...
    }

    public UserDto getLoginUser() {
        User user = userService.getUserSessionInfo();
        UserDto userDto = DozerBeanMapperSingletonWrapper.getInstance().map(user , UserDto.class);
//        Set<Role> roleSet = user.getRoleSet() ;
//        for(Role role : roleSet){
//            RoleDto roleDto = DozerBeanMapperSingletonWrapper.getInstance().map(role , RoleDto.class);
//            userDto.getRoleSet().add(roleDto);
//        }
        return userDto;
    }
}
