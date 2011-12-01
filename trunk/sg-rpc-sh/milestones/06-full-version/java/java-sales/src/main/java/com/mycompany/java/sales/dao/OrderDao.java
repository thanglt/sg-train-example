package com.mycompany.java.sales.dao;

import com.mycompany.java.common.dao.BaseDao;
import com.mycompany.java.sales.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
public interface OrderDao extends BaseDao<Order, String> {
    public List<Order> getByCriteria(Map<String, String> map);
}
