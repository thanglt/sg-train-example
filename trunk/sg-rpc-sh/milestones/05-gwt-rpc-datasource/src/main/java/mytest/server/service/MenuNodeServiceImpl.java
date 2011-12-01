package mytest.server.service;

import mytest.server.dao.MenuNodeDao;
import mytest.server.entity.MenuNode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuNodeServiceImpl implements MenuNodeService {

    @Resource
    private MenuNodeDao menuNodeDao;

    public MenuNode getById(String id) {
        return menuNodeDao.getById(id);
    }

    public List<MenuNode> getAll() {
        return menuNodeDao.getAll();
    }

    public MenuNode save(MenuNode menuNode) {
        return menuNodeDao.save(menuNode);
    }

    public MenuNode update(MenuNode menuNode) {
        return menuNodeDao.update(menuNode);
    }

    public void delete(MenuNode menuNode) {
        menuNodeDao.delete(menuNode);
    }
}
