package com.mycompany.test;

import com.mycompany.java.sales.entity.MenuNode;
import com.mycompany.java.sales.service.MenuNodeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContextConfiguration(value = {"classpath:application-config.xml"})
public class TestMenuNode extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private MenuNodeService menuNodeService;

    @Test
    public void simple() {
    }

    @Test
    @Rollback(value = false)
    public void initData() {
        //level 1
        MenuNode menuNode = new MenuNode("Charles Madigen", "root", "testTree", 1, "icons/bullet_go.png");
        menuNodeService.save(menuNode);

        //level 2
        MenuNode menuNode2 = new MenuNode("Rogine Leger", menuNode.getId(), "testTree", 1, "icons/bullet_go.png");
        MenuNode menuNode3 = new MenuNode("Gene Porter", menuNode.getId(), "testTree", 1, "icons/bullet_go.png");
        menuNodeService.save(menuNode2);
        menuNodeService.save(menuNode3);

        //level 3
        MenuNode menuNode4 = new MenuNode("Olivier Doucet", menuNode3.getId(), "testTree", 0, "icons/anchor.png");
        MenuNode menuNode5 = new MenuNode("Cheryl Pearson", menuNode3.getId(), "testTree", 0, "icons/clock.png");
        menuNodeService.save(menuNode4);
        menuNodeService.save(menuNode5);
    }

    @Test
    @Rollback(value = false)
    public void initData2() {
        //level 1
        MenuNode menuNode = new MenuNode("Test Root", "root", "testTree2", 1, "icons/bullet_go.png");
        menuNodeService.save(menuNode);
        //level 2
        MenuNode menuNode2 = new MenuNode("Test App 1", menuNode.getId(), "testTree2", 0, "icons/application_xp_terminal.png");
        MenuNode menuNode3 = new MenuNode("Test App 2", menuNode.getId(), "testTree2", 0, "icons/bug.png");
        MenuNode menuNode4 = new MenuNode("Test App 3", menuNode.getId(), "testTree2", 0, "icons/asterisk_orange.png");
        menuNodeService.save(menuNode2);
        menuNodeService.save(menuNode3);
        menuNodeService.save(menuNode4);
    }

    @Test
    @Rollback(value = false)
    public void initData3(){
        MenuNode menuNode = new MenuNode("权限管理", "root", "userManage", 1, "icons/key.png");
        menuNodeService.save(menuNode);

        MenuNode menuNode2 = new MenuNode("用户管理", menuNode.getId(), "userManage", 0, "icons/user_gray.png");
        menuNode2.setSeq(1);
        menuNodeService.save(menuNode2);

        MenuNode menuNode3 = new MenuNode("角色管理", menuNode.getId(), "userManage", 0, "icons/user.png");
        menuNode3.setSeq(2);
        menuNodeService.save(menuNode3);

        MenuNode menuNode4 = new MenuNode("资源管理", menuNode.getId(), "userManage", 0, "icons/brick.png");
        menuNode4.setSeq(4);
        menuNodeService.save(menuNode4);

        MenuNode menuNode5 = new MenuNode("分配角色给用户", menuNode.getId(), "userManage", 0, "icons/user_go.png");
        menuNode5.setSeq(3);
        menuNodeService.save(menuNode5);

        MenuNode menuNode6 = new MenuNode("分配资源给角色", menuNode.getId(), "userManage", 0, "icons/brick_go.png");
        menuNode6.setSeq(5);
        menuNodeService.save(menuNode6);
    }

    @Test
    public void getAll() {
        List list = menuNodeService.getAll();
        Assert.assertEquals(5, list.size());
    }

    @Test
    public void getByCriteria(){
        Map map = new HashMap();
//        map.put("parentId" , "f79ce6a4-effa-45c9-b550-77cbdd2a4d5d");
        map.put("category" , "userManage");
        List result = menuNodeService.getByCriteriaMap(map);
        Assert.assertEquals(5 ,result.size()) ;
    }

}
