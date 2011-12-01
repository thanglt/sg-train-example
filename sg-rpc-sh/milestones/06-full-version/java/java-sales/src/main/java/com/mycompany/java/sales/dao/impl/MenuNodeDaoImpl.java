package com.mycompany.java.sales.dao.impl;

import com.mycompany.java.common.dao.impl.BaseDaoImpl;
import com.mycompany.java.sales.dao.MenuNodeDao;
import com.mycompany.java.sales.entity.MenuNode;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MenuNodeDaoImpl extends BaseDaoImpl<MenuNode , String> implements MenuNodeDao{

    @Override
    public List<MenuNode> getByCriteriaMap(Map<String , String> criteriaMap) {
        Criteria criteria = getSession().createCriteria(MenuNode.class);
        Iterator it = criteriaMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            criteria.add(Restrictions.eq(key , value));
        }
        criteria.addOrder(Order.asc("seq"));
        return criteria.list();
    }
}
