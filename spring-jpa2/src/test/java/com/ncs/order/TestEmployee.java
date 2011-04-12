package com.ncs.order;

import com.ncs.order.service.EmployeeService;
import com.ncs.order.to.EmployeeTo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.Set;

@ContextConfiguration(locations = {"classpath:spring/test-config.xml" })
public class TestEmployee extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String TEST_NAME = "testName";

    @Resource
    private EmployeeService employeeService;

//    @Test
//    public void simple(){
//    }

    @Test
    public void test1SaveAndGet(){
        createAndSaveEmployee(TEST_NAME, 10);

        EmployeeTo employee = employeeService.get("name" , TEST_NAME);
        Assert.assertEquals(TEST_NAME , employee.getName());
        Assert.assertEquals(10 , employee.getAge());
    }

    private void createAndSaveEmployee(String name, int age) {
        EmployeeTo employee = new EmployeeTo();
        employee.setName(name);
        employee.setAge(age);
        employeeService.save(employee);
    }

    @Test
    public void test3Update(){
        createAndSaveEmployee(TEST_NAME , 10);

        EmployeeTo employee = employeeService.get("name" , TEST_NAME);
        Assert.assertEquals(TEST_NAME , employee.getName());
        Assert.assertEquals(10 , employee.getAge());

        employee.setAge(20);
        employeeService.update(employee);

        EmployeeTo employeeTo2 = employeeService.get("name" , TEST_NAME);
        Assert.assertEquals(TEST_NAME , employeeTo2.getName());
        Assert.assertEquals(20 , employeeTo2.getAge());
    }

    @Test
    public void test4Delete(){
        createAndSaveEmployee(TEST_NAME , 10);

        EmployeeTo employeeTo = employeeService.get("name" , TEST_NAME);
        employeeService.delete(employeeTo);

        Set set = employeeService.getList("name" , TEST_NAME);
        Assert.assertEquals(0 , set.size());
    }

}
