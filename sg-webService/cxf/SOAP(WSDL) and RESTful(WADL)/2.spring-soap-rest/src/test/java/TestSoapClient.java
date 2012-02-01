import com.mycompany.webservice.server.entity.User;
import com.mycompany.webservice.server.webservice.WsUserService;
import com.mycompany.webservice.server.webservice.type.UserListOutputType;
import junit.framework.TestCase;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class TestSoapClient extends TestCase {

    private WsUserService client; // web service client

    @Override
    public void setUp() throws Exception {
        System.out.println("start");
        super.setUp();

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(WsUserService.class);
        factory.setAddress("http://localhost:8082/services/userService");

        Object obj = factory.create();
        if (obj == null) {
            throw new RuntimeException("factory is null!");
        } else {
            this.client = (WsUserService) obj;
        }
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        System.out.println("end");
    }

    public void testUpdate(){
        User user = new User();
        user.setId("54d690cc-5e70-4460-891e-b1209647e99a");
        user.setName("111");
        user.setEmail("aaa");
        client.update(user); //java.lang.InstantiationException
        //TODO...
    }

    public void testGetList() {
        UserListOutputType userListOutputType = client.getList();
        for (User user : userListOutputType.getUser()) {
            System.out.println(user.toString());
        }
    }

}
