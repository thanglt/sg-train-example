package com.mycompany.java.sales.service.impl;

import com.mycompany.java.common.service.impl.BaseServiceImpl;
import com.mycompany.java.sales.dao.OrderDao;
import com.mycompany.java.sales.entity.Order;
import com.mycompany.java.sales.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    public void setBaseDao(OrderDao orderDao) {
        super.setBaseDao(orderDao);
    }

    @Override
    public List<Order> getByCriteria(Map<String, String> map) {
        return orderDao.getByCriteria(map);
    }
}
