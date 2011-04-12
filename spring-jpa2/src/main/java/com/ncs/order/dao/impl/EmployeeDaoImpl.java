package com.ncs.order.dao.impl;

import com.ncs.order.dao.EmployeeDao;
import com.ncs.order.to.EmployeeTo;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl extends BaseDaoImpl<EmployeeTo,String> implements EmployeeDao {

}
