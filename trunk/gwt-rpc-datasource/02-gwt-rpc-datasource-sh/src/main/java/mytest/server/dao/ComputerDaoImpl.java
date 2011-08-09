package mytest.server.dao;

import mytest.server.entity.Computer;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComputerDaoImpl extends HibernateDaoSupport implements ComputerDao{

    public Computer getById(String id) {
        return (Computer) getSession().get(Computer.class, id);
    }

    public List<Computer> getAll() {
        String hql = "from Computer";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    public Computer save(Computer computer) {
        getSession().saveOrUpdate(computer);
        return computer;
    }

    public Computer update(Computer computer) {
        getSession().saveOrUpdate(computer);
        return computer;
    }

    public void delete(Computer computer) {
        getSession().delete(computer);
    }
}
