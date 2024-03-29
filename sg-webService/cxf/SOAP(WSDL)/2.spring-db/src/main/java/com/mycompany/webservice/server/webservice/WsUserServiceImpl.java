package com.mycompany.webservice.server.webservice;

import com.mycompany.webservice.server.entity.User;
import com.mycompany.webservice.server.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.List;

@Service
@WebService(endpointInterface = "com.mycompany.webservice.server.webservice.WsUserService")
public class WsUserServiceImpl implements WsUserService{

//    @Resource
    public UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<User> getList() {
        return userService.getList();
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
