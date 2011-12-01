import com.mycompany.java.common.entity.Role;
import com.mycompany.java.common.service.RoleService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: HJL
 * Date: 11-8-22
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(value = {"classpath:application-config.xml"})
public class TestRole extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String TEST_ID = "e58cf55e-8b23-4729-87e1-80e95b8067df";

    @Resource
    private RoleService roleService;

    @Test
    public void simple(){
    }

    @Test
    @Rollback(value = false)
    public void saveRole(){
        Role role = new Role();
        role.setName("test role");
        role.setValue("ROLE_TEST");
        role.setIsSystem(false);
        role.setProjectId("TEST");
        roleService.save(role);
    }

    @Test
    public void queryRole(){
        Role r = roleService.get(TEST_ID);
        Assert.assertEquals("TEST" , r.getProjectId());
    }

    @Test
    @Rollback(value = false)
    public void updateRole(){
        Role r = roleService.get(TEST_ID);
        r.setDescription("updated on :" + new Date().toString());
        roleService.update(r);
    }

    @Test
    @Rollback(value = false)
    public void deleteUser(){
        roleService.delete(TEST_ID);
    }

}
