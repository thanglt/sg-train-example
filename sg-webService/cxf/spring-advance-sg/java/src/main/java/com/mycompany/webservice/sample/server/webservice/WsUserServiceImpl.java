package com.mycompany.webservice.sample.server.webservice;

import com.mycompany.webservice.sample.server.entity.User;
import com.mycompany.webservice.sample.server.service.UserService;
import com.mycompany.webservice.sample.server.webservice.type.UserListOutputType;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@Service
@WebService(endpointInterface = "com.mycompany.webservice.sample.server.webservice.WsUserService")
public class WsUserServiceImpl implements WsUserService{

//    @Resource
    public UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserListOutputType getList() {
        UserListOutputType userListOutputType = new UserListOutputType();
        userListOutputType.getUser().addAll(userService.getList());
        return userListOutputType;
    }

    public void create(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userService.create(user);
    }

    public void update(String id , String name, String email){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        userService.update(user);
    }

    public void delete(String id) {
        userService.deleteById(id);
    }
}
