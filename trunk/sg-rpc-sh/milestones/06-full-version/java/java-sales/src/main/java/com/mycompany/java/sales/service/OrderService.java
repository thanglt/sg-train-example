package com.mycompany.java.sales.service;

import com.mycompany.java.common.service.BaseService;
import com.mycompany.java.sales.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
public interface OrderService extends BaseService<Order, String>{
    public List<Order> getByCriteria(Map<String, String> map);
}
