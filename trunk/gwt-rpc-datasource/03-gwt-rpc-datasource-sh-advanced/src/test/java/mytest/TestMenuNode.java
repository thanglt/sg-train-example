package mytest;

import mytest.server.entity.MenuNode;
import mytest.server.service.MenuNodeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.List;

@ContextConfiguration(value = {"classpath:application-config.xml"})
public class TestMenuNode extends AbstractTransactionalJUnit4SpringContextTests{

    @Resource
    private MenuNodeService menuNodeService;

    @Test
    public void simple(){}

    @Test
    @Rollback(value = false)
    public void initData(){
        //level 1
        MenuNode menuNode = new MenuNode("Charles Madigen" , "root" , "testTree" , 1);
        menuNodeService.save(menuNode);

        //level 2
        MenuNode menuNode2 = new MenuNode("Rogine Leger" , menuNode.getId(), "testTree" , 1);
        MenuNode menuNode3 = new MenuNode("Gene Porter" , menuNode.getId() , "testTree", 1);
        menuNodeService.save(menuNode2);
        menuNodeService.save(menuNode3);

        //level 3
        MenuNode menuNode4 = new MenuNode("Olivier Doucet" , menuNode3.getId() , "testTree" , 0);
        MenuNode menuNode5 = new MenuNode("Cheryl Pearson" , menuNode3.getId() , "testTree" , 0);
        menuNodeService.save(menuNode4);
        menuNodeService.save(menuNode5);
    }

    @Test
    @Rollback(value = false)
    public void initData2(){
        //level 1
        MenuNode menuNode = new MenuNode("Test Root" , "root" , "testTree2" , 1);
        menuNodeService.save(menuNode);
        //level 2
        MenuNode menuNode2 = new MenuNode("Test App 1" , menuNode.getId() , "testTree2" , 0);
        MenuNode menuNode3 = new MenuNode("Test App 2" , menuNode.getId() , "testTree2", 0);
        MenuNode menuNode4 = new MenuNode("Test App 3" , menuNode.getId() , "testTree2", 0);
        menuNodeService.save(menuNode2);
        menuNodeService.save(menuNode3);
        menuNodeService.save(menuNode4);
    }



    @Test
    public void getAll(){
        List list = menuNodeService.getAll();
        Assert.assertEquals(5 , list.size());
    }

}
