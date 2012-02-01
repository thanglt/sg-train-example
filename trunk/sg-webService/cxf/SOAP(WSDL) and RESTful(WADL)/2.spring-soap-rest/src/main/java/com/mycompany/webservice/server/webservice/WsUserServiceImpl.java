package com.mycompany.webservice.server.webservice;

import com.mycompany.webservice.server.entity.User;
import com.mycompany.webservice.server.service.UserService;
import com.mycompany.webservice.server.webservice.type.UserListOutputType;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.core.Response;

@Service("wsUserServiceImpl")
@WebService(endpointInterface = "com.mycompany.webservice.server.webservice.WsUserService")
public class WsUserServiceImpl implements WsUserService {

    @Resource
    public UserService userService;

    public UserListOutputType getList() {
        UserListOutputType userListOutputType = new UserListOutputType();
        userListOutputType.getUser().addAll(userService.getList());
        return userListOutputType;
    }

    public Response addUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userService.create(user);
        return Response.ok(user).build();
    }

    public Response addUser2(User user) {
        userService.create(user);
        return Response.ok(user).build();
    }

    public Response update(User user) {
        User u = userService.getById(user.getId());
        Response r;
        if(u!=null){
            u.setName(user.getName());
            u.setEmail(user.getEmail());
            userService.update(u);
            r = Response.ok(u).build();
        }else{
            r = Response.notModified().build();
        }
        return r;
    }

    public Response delete(String id) {
        User user = userService.getById(id);
        Response r;
        if(user!=null){
            userService.deleteById(id);
            r = Response.ok().build();
        }else{
            r = Response.notModified().build();
        }
        return r;
    }
}
