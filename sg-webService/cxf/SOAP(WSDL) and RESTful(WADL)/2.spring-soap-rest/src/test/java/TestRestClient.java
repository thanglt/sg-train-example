import com.mycompany.webservice.server.entity.User;
import com.mycompany.webservice.server.webservice.type.UserListOutputType;
import junit.framework.TestCase;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.form.Form;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class TestRestClient extends TestCase {

    private WebClient client;
    private JAXBContext jc;
    private Unmarshaller unmarshaller;

    @Override
    public void setUp() throws Exception {
        System.out.println("start");
        super.setUp();

        jc = JAXBContext.newInstance(User.class);
        unmarshaller = jc.createUnmarshaller();

        client = WebClient.create("http://localhost:8082/services/rest/");
//        client.type("text/xml").accept("text/xml");
        client.type("application/xml").accept("application/xml");
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        System.out.println("end");
    }

    public void testGet() {
        client.path("userService/users");
        UserListOutputType output = client.get(UserListOutputType.class);
        System.out.println(output.getUser().size());
    }

    public void testPost1() throws JAXBException {
        client.path("userService/users");
        Form form = new Form();
        form.set("name", "testName");
        form.set("email", "testEmail");
        Response r = client.form(form);
        System.out.println(r.getStatus());
        System.out.println(getFromInputStreamUsingJaxb(r).toString());
    }

    public void testPost2() {
        client.path("userService/users2");
        User user = new User();
        user.setName("testName2");
        user.setEmail("testEmail2");
        User u = client.post(user, User.class);
        System.out.println(u.toString());
    }

    public void testPut() throws JAXBException {
        client.path("userService/users");
        User user = new User();
        user.setId("bf26f1e9-6bef-460d-a8bc-425e937857bf");
        user.setName("updatedName");
        user.setEmail("updatedEmail");
        Response r = client.put(user);
        System.out.println(r.getStatus());
        System.out.println(getFromInputStreamUsingJaxb(r).toString());
    }

    public void testDelete() {
        String id = "fa2b08d0-3322-49a9-9e10-71e75ec61ff2";
        client.path("userService/users/" + id);
        Response r = client.delete();
        System.out.println(r.getStatus());
    }

    private User getFromInputStreamUsingJaxb(Response r) throws JAXBException {
        InputStream is = (InputStream) r.getEntity();
        return (User) unmarshaller.unmarshal(is);
    }

}

