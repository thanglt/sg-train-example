package mytest;

import mytest.server.entity.User;
import mytest.server.service.UserService;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;

@ContextConfiguration(locations = {"classpath:application-config.xml"})
public class TestUser extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private UserService userService;

    @Test
    public void simple() {
    }

    @Test
    @Rollback(value = false)
    public void batchSave() {
        for (int i = 0; i < 2000; i++) {
            User user = new User();
            user.setName("User-" + i);
            user.setLocation("Location-" + i);
            userService.save(user);
        }
    }

    @Test
    @Rollback(value = false)
    public void update() {
        User user = userService.getById("08a14c75-88d9-4f50-9fc5-adde0b6dbab2");
        user.setName("updated name");
        user.setLocation("updated location");
        userService.update(user);
    }

    @Test
    @Rollback(value = false)
    public void delete(){
        User user = userService.getById("08a14c75-88d9-4f50-9fc5-adde0b6dbab2");
        userService.delete(user);
    }


}
