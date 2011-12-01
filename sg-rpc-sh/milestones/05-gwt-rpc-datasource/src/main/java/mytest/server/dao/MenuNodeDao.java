package mytest.server.dao;

import mytest.server.entity.MenuNode;

import java.util.List;

public interface MenuNodeDao {

    MenuNode getById(String id);

    List<MenuNode> getAll();

    MenuNode save(MenuNode menuNode);

    MenuNode update(MenuNode menuNode);

    void delete(MenuNode menuNode);

}

