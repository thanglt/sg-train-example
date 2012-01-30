package com.mycompany.webservice.server.dao;

import com.mycompany.webservice.server.entity.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

    public List<User> getList() {
        String hql = "from User";
        return this.getHibernateTemplate().find(hql);
    }

    public void create(User user) {
        this.getHibernateTemplate().save(user);
    }

    public void update(User user) {
        this.getHibernateTemplate().merge(user);
    }

    public User getById(String id) {
        return this.getHibernateTemplate().get(User.class , id);
    }

    public void deleteById(User user) {
        this.getHibernateTemplate().delete(user);
    }

}
