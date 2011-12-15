package demo.spring.server;

import javax.jws.WebService;

@WebService(endpointInterface = "demo.spring.server.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String name) {
        System.out.println("sayHi called");
        return "Hello " + name;
    }
}
