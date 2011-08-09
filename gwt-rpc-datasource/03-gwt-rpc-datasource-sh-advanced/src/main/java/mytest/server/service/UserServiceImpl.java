package mytest.server.service;

import mytest.server.dao.UserDao;
import mytest.server.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    public User getById(String id) {
        return userDao.getById(id);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User save(User user) {
        return userDao.save(user);
    }

    public User update(User user) {
        return userDao.update(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }
}
