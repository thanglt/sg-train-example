package com.mycompany.test;

import com.mycompany.java.sales.entity.Order;
import com.mycompany.java.sales.service.OrderService;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
@ContextConfiguration(value = {"classpath:application-config.xml"})
public class TestOrder extends AbstractTransactionalJUnit4SpringContextTests{

    @Resource
    private OrderService orderService;

    @Test
    public void simple() {
    }

    @Test
    @Rollback(value = false)
    public void batchSave(){
        for (int i = 0; i < 300; i++) {
            Order order = new Order();
            order.setCode("Order_Code_"+i);
            order.setRemark("this is order :" + i);
            orderService.save(order);
        }
    }

}
