package com.mycompany.gwt.sales.server.servlet;

import com.mycompany.gwt.common.shared.GenericGwtRpcList;
import com.mycompany.gwt.sales.client.dto.OrderDto;
import com.mycompany.gwt.sales.client.service.OrderServiceRPC;
import com.mycompany.java.sales.entity.Order;
import com.mycompany.java.sales.service.OrderService;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: Hu jing ling
 * Date: 8/16/11
 * Description: put description for the type here...
 */
@Service("orderServiceRPCServlet")
public class OrderServiceRPCServlet implements OrderServiceRPC {

    @Resource
    private Mapper mapper;

    @Resource
    private OrderService orderService;

    @Override
    @Transactional
    public List<OrderDto> fetch(Integer startRow, Integer endRow, String sortBy, Map<String, String> filterCriteria) {
        GenericGwtRpcList<OrderDto> outList = new GenericGwtRpcList<OrderDto>();

        List<Order> orderList = null;
        if (filterCriteria != null && filterCriteria.size() > 0) {
            Map<String, String> map = new HashMap<String, String>();
            String code = filterCriteria.get("code");
            if (StringUtils.isNotEmpty(code)) {
                map.put("code", code);
            }
            String remark = filterCriteria.get("remark");
            if (StringUtils.isNotEmpty(remark)) {
                map.put("remark", remark);
            }
            orderList = orderService.getByCriteria(map);
        } else {
            orderList = orderService.getAll();
        }

        List<OrderDto> dtoList = new ArrayList<OrderDto>();

        for (Order order : orderList) {
            dtoList.add(mapper.map(order, OrderDto.class));
        }

        // to implement sorting, use the transfered sortBy String. It contains the field name to sort,
        // preceded by a '-' if the sorting is to be descending.
        System.out.println("sortBy : " + sortBy);

        // if startRow and endRow are not null, the client wishes paged fetching.
        if (startRow != null && endRow != null) {
            // extract the requested part
            for (int i = startRow; (i < endRow && i < dtoList.size()); i++) {
                outList.add(dtoList.get(i));
            }
        } else {
            outList.addAll(dtoList);
        }

        // important to allow proper paging, we set the total counts of rows.
        outList.setTotalRows(dtoList.size());

        return outList;
    }

    @Override
    @Transactional
    public OrderDto add(OrderDto data) {
        Order order = mapper.map(data, Order.class);
        orderService.save(order);
        return mapper.map(order, OrderDto.class);
    }

    @Override
    @Transactional
    public OrderDto update(OrderDto data) {
        Order order = mapper.map(data, Order.class);
        orderService.update(order);
        return mapper.map(order, OrderDto.class);
    }

    @Override
    @Transactional
    public void remove(OrderDto data) {
        Order order = mapper.map(data, Order.class);
        orderService.delete(order);
    }
}
