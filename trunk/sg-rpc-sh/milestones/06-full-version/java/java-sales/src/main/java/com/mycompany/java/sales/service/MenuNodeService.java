package com.mycompany.java.sales.service;

import com.mycompany.java.common.service.BaseService;
import com.mycompany.java.sales.entity.MenuNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MenuNodeService extends BaseService<MenuNode , String> {

    public List<MenuNode> getByCriteriaMap(Map<String , String> criteriaMap);

}
