package mytest.server.servlet;

import mytest.client.service.MenuNodeServiceRPC;
import mytest.server.entity.MenuNode;
import mytest.server.service.MenuNodeService;
import mytest.shared.MenuNodeDto;
import mytest.shared.lib.GenericGwtRpcList;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service("menuNodeServiceRpcServlet")
public class MenuNodeServiceRpcServlet implements MenuNodeServiceRPC {

    @Resource
    private MenuNodeService menuNodeService;

    public List<MenuNodeDto> fetch(Integer startRow, Integer endRow, String sortBy, Map<String, String> filterCriteria) {
        GenericGwtRpcList<MenuNodeDto> outList = new GenericGwtRpcList<MenuNodeDto>();
        List<MenuNode> menuNodeList = menuNodeService.getAll();
        List<MenuNodeDto> dtoList = new ArrayList<MenuNodeDto>();

        for (MenuNode menuNode : menuNodeList) {
            dtoList.add(DozerBeanMapperSingletonWrapper.getInstance().map(menuNode, MenuNodeDto.class));
        }

        if (filterCriteria != null && filterCriteria.size() > 0) {
            List<MenuNodeDto> filteredList = new ArrayList<MenuNodeDto>();
            if (filterCriteria.containsKey("parentId")) {
                if (null == filterCriteria.get("parentId")) {
                    filteredList.addAll(dtoList);
                } else {
                    String pattern = ".*" + filterCriteria.get("parentId") + ".*";
                    for (MenuNodeDto dto : dtoList) {
                        if (Pattern.matches(pattern, dto.getParentId())) {
                            filteredList.add(dto);
                        }
                    }
                }
            }
            dtoList = new ArrayList();
            if (filterCriteria.containsKey("category")) {
                String pattern = filterCriteria.get("category");
                for (MenuNodeDto dto : filteredList) {
                    if (Pattern.matches(pattern, dto.getCategory())) {
                        dtoList.add(dto);
                    }
                }
            }
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
