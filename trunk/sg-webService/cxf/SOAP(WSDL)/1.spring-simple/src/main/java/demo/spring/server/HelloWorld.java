package demo.spring.server;

import demo.spring.server.vo.User;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface HelloWorld {
    String sayHi(@WebParam(name = "name")
                 String name);

    User getUser(@WebParam(name = "name")
                 String name);

    List<Integer> getSimpleList(@WebParam(name = "num") Integer num);

    List<User> getComplexList();
}
