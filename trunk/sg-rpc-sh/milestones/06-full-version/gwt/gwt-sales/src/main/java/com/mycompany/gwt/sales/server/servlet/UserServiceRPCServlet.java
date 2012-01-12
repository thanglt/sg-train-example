package com.mycompany.gwt.sales.server.servlet;

import com.mycompany.gwt.common.shared.GenericGwtRpcList;
import com.mycompany.gwt.sales.client.dto.RoleDto;
import com.mycompany.gwt.sales.client.dto.UserDto;
import com.mycompany.gwt.sales.client.service.UserServiceRPC;
import com.mycompany.java.common.entity.Role;
import com.mycompany.java.common.entity.User;
import com.mycompany.java.common.service.RoleService;
import com.mycompany.java.common.service.UserService;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("userServiceRPCServlet")
@Transactional
public class UserServiceRPCServlet implements UserServiceRPC {

    private static final Logger log = Logger.getLogger(UserServiceRPCServlet.class);

    @Resource
    private Mapper mapper;

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    public UserDto getLoginUser() {
        User user = userService.getUserSessionInfo();
        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public List<RoleDto> getRoleListByUser(String userId) {
        List<RoleDto> roleDtoList = new ArrayList<RoleDto>();
        for(Role role : userService.getRoleListByUser(userId)){
            roleDtoList.add(mapper.map(role , RoleDto.class));
        }
        return roleDtoList;
    }

    @Override
    public List<RoleDto> getValidRoleListByUser(String userId) {
        List<RoleDto> roleDtoList = new ArrayList<RoleDto>();
        for(Role role : userService.getValidRoleListByUser(userId)){
            roleDtoList.add(mapper.map(role , RoleDto.class));
        }
        return roleDtoList;
    }

    @Override
    @Transactional
    public String assignRoleToUser(String roleId, String userId) {
        Role role = roleService.get(roleId);  //待添加角色
        User user = userService.get(userId);//用户实例
        Set roleSet = user.getRoleSet();
        roleSet.add(role);//添加角色
        userService.update(user);
        return "OK";
    }

    @Override
    @Transactional
    public String removeRoleFromUser(String roleId, String userId) {
        Role role = roleService.get(roleId);//待移除角色
        User user = userService.get(userId);//用户实例
        Set roleSet = user.getRoleSet();
        roleSet.remove(role);//移除角色
        userService.update(user);
        return "OK";
    }


    @Override
    public List<UserDto> fetch(Integer startRow, Integer endRow, String sortBy, Map<String, String> filterCriteria) {
        GenericGwtRpcList<UserDto> outList = new GenericGwtRpcList<UserDto>();

        // we copy the original list, cause we do not want to alter it.
        List<User> userList = userService.getAll();
        List<UserDto> dtoList = new ArrayList<UserDto>();

        for (User user : userList) {
            dtoList.add(mapper.map(user, UserDto.class));
        }

        // to implement filtering, retrieve the filter criterias from filterCriteria and build your query accordingly.
        // for the sake of argument, we filter the list:
        if (filterCriteria != null && filterCriteria.size() > 0) {
            //...
        }

        // to implement sorting, use the transfered sortBy String. It contains the field name to sort,
        // preceded by a '-' if the sorting is to be descending.
//        System.out.println("sortBy : " + sortBy);

         // if startRow and endRow are not null, the client wishes paged fetching.
        if (startRow != null && endRow != null) {
            // extract the requested part
            for (int i = startRow; (i < endRow && i < dtoList.size()); i++) {
                outList.add(dtoList.get(i));
            }
        } else {
            outList.addAll(dtoList);
        }

        // important to allow proper paging, we set the total counts of rows.
        outList.setTotalRows(dtoList.size());

        return outList;
    }

    @Override
    public UserDto add(UserDto data) {
        User user = mapper.map(data, User.class);
        userService.save(user);
        return mapper.map(user , UserDto.class);
    }

    @Override
    public UserDto update(UserDto data) {
        User user = mapper.map(data, User.class);
        userService.update(user);
        return mapper.map(user , UserDto.class);
    }

    @Override
    public void remove(UserDto data) {
        User user = mapper.map(data, User.class);
        userService.delete(user);
    }
}
