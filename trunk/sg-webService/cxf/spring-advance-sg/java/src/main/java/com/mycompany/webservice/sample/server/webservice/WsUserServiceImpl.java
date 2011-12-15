package com.mycompany.webservice.sample.server.webservice;

import com.mycompany.webservice.sample.server.entity.User;
import com.mycompany.webservice.sample.server.service.UserService;
import com.mycompany.webservice.sample.server.utils.MailUtils;
import com.mycompany.webservice.sample.server.webservice.type.OperationOutputType;
import com.mycompany.webservice.sample.server.webservice.type.UserListOutputType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.List;

@Service("wsUserService")
@WebService(endpointInterface = "com.mycompany.webservice.sample.server.webservice.WsUserService")
public class WsUserServiceImpl implements WsUserService{

    @Resource
    public UserService userService;

    public UserListOutputType getList() {
        UserListOutputType userListOutputType = new UserListOutputType();
        userListOutputType.getUser().addAll(userService.getList());
        return userListOutputType;
    }

    public OperationOutputType create(String name, String email) {
        if(MailUtils.isNotValidEmail(email)){
            return new OperationOutputType("false" , "is a unavailable email!");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userService.create(user);
        return new OperationOutputType("true" , "record created");
    }

    public OperationOutputType update(String id, String name, String email){
        if(MailUtils.isNotValidEmail(email)){
            return new OperationOutputType("false" , "is a unavailable email!");
        }
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        userService.update(user);
        return new OperationOutputType("true" , "record updated");
    }

    public OperationOutputType delete(String id) {
        userService.deleteById(id);
        return new OperationOutputType("true" , "record deleted");
    }
}
