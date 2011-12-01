package com.mycompany.gwt.sales.server.service;

import com.mycompany.gwt.sales.client.service.UserServiceRPC;
import com.mycompany.salesDomain.entity.User;
import com.mycompany.salesDomain.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("userServiceRPCServlet")
public class UserServiceRPCServlet implements UserServiceRPC {

    private static final Logger log = Logger.getLogger(UserServiceRPCServlet.class);

    @Resource
    private UserService userService;

    public List<User> getAll() {
        return userService.getAll();
    }
}
