package com.mycompany.java.sales.service.impl;

import com.mycompany.java.common.service.impl.BaseServiceImpl;
import com.mycompany.java.sales.dao.MenuNodeDao;
import com.mycompany.java.sales.entity.MenuNode;
import com.mycompany.java.sales.service.MenuNodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuNodeServiceImpl extends BaseServiceImpl<MenuNode, String> implements MenuNodeService {

    @Resource
    private MenuNodeDao menuNodeDao;

    @Resource
    public void setBaseDao(MenuNodeDao menuNodeDao) {
        super.setBaseDao(menuNodeDao);
    }

    public List<MenuNode> getByCriteriaMap(Map<String , String> criteriaMap){
        return menuNodeDao.getByCriteriaMap(criteriaMap);
    }

}
