package mytest.server.dao;

import mytest.server.entity.MenuNode;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuNodeDaoImpl extends HibernateDaoSupport implements MenuNodeDao{

    public MenuNode getById(String id) {
       return (MenuNode) getSession().get(MenuNode.class, id);
    }

    public List<MenuNode> getAll() {
        String hql = "from MenuNode";
        return getSession().createQuery(hql).list();
    }

    public MenuNode save(MenuNode menuNode) {
        getSession().saveOrUpdate(menuNode);
        return menuNode;
    }

    public MenuNode update(MenuNode menuNode) {
        getSession().saveOrUpdate(menuNode);
        return menuNode;
    }

    public void delete(MenuNode menuNode) {
        getSession().delete(menuNode);
    }
}
