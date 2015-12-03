package com.zfpt.web.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfpt.framework.dao.GenericDao;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.service.GenericServiceImpl;
import com.zfpt.web.common.MapperNameSpace;
import com.zfpt.web.model.system.Employee;
import com.zfpt.web.model.system.Role;
import com.zfpt.web.model.system.User;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl implements IEmployeeService,MapperNameSpace{
	
	@Autowired
	private GenericDao baseDao;
	@Override
	public int saveEmployee(Employee emp) throws ServiceException {
		return super.insert(emp);
	}

	@Override
	public int deleteEmployeeByEmpId(Long id) throws ServiceException {
		
		return super.delete(Employee.class, id);
	}

	@Override
	public Employee findEmployeeByEmpId(Long id) throws ServiceException {
		Employee emp = (Employee) baseDao.selectOne(Employee.class, id);
		return emp;
	}

	@Override
	public Pager<List<Employee>> queryForPage(Pager<List<Employee>> page)
			throws ServiceException {
		 return  super.queryForPage(Employee.class, page);
	}

	@Override
	public int updateEmployee(Employee emp) throws ServiceException {
		return super.update(emp);
	}
}
