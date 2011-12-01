package com.mycompany.test;

import com.mycompany.java.sales.dao.UserDao;
import com.mycompany.java.sales.entity.User;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.List;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestUser extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private UserDao userDao;

    @Test
    public void simple() {
    }

    @Test
    public void getAll() {
        List list = userDao.getAll();
        Assert.assertEquals(0, list.size());
    }

    @Test
    @Rollback(value = true)
    public void save() {
        User user = new User();
        user.setName("testUser");
        userDao.save(user);
        List list = userDao.getAll();
        Assert.assertEquals(1, list.size());
    }

    @Test
    @Rollback(value = true)
    public void update() {
        User user = new User();
        user.setName("testUser");
        userDao.save(user);

        User user2 = userDao.get("name", "testUser");
        user2.setName("updated");
        userDao.update(user2);

        User user3 = userDao.get("name", "updated");
        Assert.assertEquals("updated", user3.getName());
    }

    @Test
    @Rollback(value = true)
    public void delete() {
        User user = new User();
        user.setName("testUser");
        userDao.save(user);

        userDao.delete(user);
//        User user2 = userDao.get("name", "testUser");
//        userDao.delete(user2);

        User user3 = userDao.get("name", "testUser");
        Assert.assertNull(user3);
    }


}
