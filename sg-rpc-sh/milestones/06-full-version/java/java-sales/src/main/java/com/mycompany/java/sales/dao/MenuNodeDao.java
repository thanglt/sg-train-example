package com.mycompany.java.sales.dao;

import com.mycompany.java.common.dao.BaseDao;
import com.mycompany.java.sales.entity.MenuNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jingling
 * Date: 8/11/11
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MenuNodeDao extends BaseDao<MenuNode, String> {

    public List<MenuNode> getByCriteriaMap(Map<String , String> criteriaMap);

}
