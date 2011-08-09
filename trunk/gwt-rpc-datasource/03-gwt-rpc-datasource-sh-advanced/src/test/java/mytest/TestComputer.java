package mytest;

import mytest.server.entity.Computer;
import mytest.server.service.ComputerService;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;

@ContextConfiguration(locations = {"classpath:application-config.xml"})
public class TestComputer extends AbstractTransactionalJUnit4SpringContextTests{

    @Resource
    private ComputerService computerService;

    @Test
    public void simple(){
    }

    @Test
    @Rollback(value = false)
    public void batchSave(){
        for (int i = 0; i < 15; i++) {
            Computer computer = new Computer();
            computer.setCode("code-" + i);
            computer.setType("type-" + i);
            computerService.save(computer);
        }
    }





}
