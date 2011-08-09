package mytest.server.dao;

import mytest.server.entity.User;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

    public User getById(String id) {
        return (User) getSession().get(User.class, id);
    }

    public List<User> getAll() {
        String hql = "from User";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    public User save(User user) {
        getSession().saveOrUpdate(user);
        return user;
    }

    public User update(User user) {
        getSession().saveOrUpdate(user);
        return user;
    }

    public void delete(User user) {
        getSession().delete(user);
    }
}
