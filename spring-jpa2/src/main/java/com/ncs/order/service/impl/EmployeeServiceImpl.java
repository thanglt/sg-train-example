package com.ncs.order.service.impl;

import com.ncs.order.dao.EmployeeDao;
import com.ncs.order.service.EmployeeService;
import com.ncs.order.to.EmployeeTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeServiceImpl extends BaseServiceImpl<EmployeeTo,String> implements EmployeeService{

    @Resource
	public void setBaseDao(EmployeeDao employeeDao) {
		super.setBaseDao(employeeDao);
	}

}
