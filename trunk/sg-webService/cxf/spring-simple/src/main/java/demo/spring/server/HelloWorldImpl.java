package demo.spring.server;

import javax.jws.WebService;

@WebService(endpointInterface = "demo.spring.server.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }
}
