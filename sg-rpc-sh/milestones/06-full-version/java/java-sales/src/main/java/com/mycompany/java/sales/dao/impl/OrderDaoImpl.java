package com.mycompany.java.sales.dao.impl;

import com.mycompany.java.common.dao.impl.BaseDaoImpl;
import com.mycompany.java.sales.dao.OrderDao;
import com.mycompany.java.sales.entity.Order;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, String> implements OrderDao{
    @Override
    public List<Order> getByCriteria(Map<String, String> map) {
        Criteria criteria = getSession().createCriteria(Order.class);
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            criteria.add(Restrictions.like(key , "%"+value+"%"));
        }
        return criteria.list();
    }
}
