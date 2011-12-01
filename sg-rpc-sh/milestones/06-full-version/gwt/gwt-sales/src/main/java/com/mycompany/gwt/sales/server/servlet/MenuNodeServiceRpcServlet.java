package com.mycompany.gwt.sales.server.servlet;

import com.mycompany.gwt.common.shared.GenericGwtRpcList;
import com.mycompany.gwt.sales.client.dto.MenuNodeDto;
import com.mycompany.gwt.sales.client.service.MenuNodeServiceRPC;
import com.mycompany.java.sales.entity.MenuNode;
import com.mycompany.java.sales.service.MenuNodeService;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service("menuNodeServiceRPCServlet")
public class MenuNodeServiceRpcServlet implements MenuNodeServiceRPC {

    @Resource
    private Mapper mapper;

    @Resource
    private MenuNodeService menuNodeService;

    @Transactional
    public List<MenuNodeDto> fetch(Integer startRow, Integer endRow, String sortBy, Map<String, String> filterCriteria) {
        GenericGwtRpcList<MenuNodeDto> outList = new GenericGwtRpcList<MenuNodeDto>();
        //init criteria map
        Map<String , String> criteriaMap = new HashMap<String , String>();
        if (filterCriteria != null && filterCriteria.size() > 0) {
            if (filterCriteria.containsKey("parentId")) {
                String parentId = filterCriteria.get("parentId");
                if (StringUtils.isNotEmpty(parentId)) {
                    criteriaMap.put("parentId" , parentId);
                }
            }
            if (filterCriteria.containsKey("category")) {
                String category = filterCriteria.get("category");
                if(StringUtils.isNotEmpty(category)){
                    criteriaMap.put("category" , category);
                }
            }
        }
        //get by criteria
        List<MenuNode> menuNodeList = menuNodeService.getByCriteriaMap(criteriaMap);
        List<MenuNodeDto> dtoList = new ArrayList<MenuNodeDto>();

        for (MenuNode menuNode : menuNodeList) {
            dtoList.add(mapper.map(menuNode, MenuNodeDto.class));
        }

        outList.addAll(dtoList);
        outList.setTotalRows(dtoList.size());
        return outList;
    }

    public MenuNodeDto add(MenuNodeDto data) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public MenuNodeDto update(MenuNodeDto data) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void remove(MenuNodeDto data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
