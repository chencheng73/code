package com.zfpt.web.service.system;

import java.util.List;

import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.service.GenericService;
import com.zfpt.web.model.system.Employee;


/**      
 * 项目名称：zfpt   
 * 类名称：IEmployeeService   
 * 类描述：员工管理接口   
 * 创建人：lzj
 * 创建时间：2015年12月1日 下午4:44:25   
 * 修改备注：   
 * @version      
 */
  
public interface IEmployeeService extends GenericService{
	
	/**
	 * <P> 方法名称: saveEmployee</P> 
	 * <P> 方法描述: 保存员工</P> 
	 * <P> 返回类型: int</P> 
	 * <P> 创建人：lzj</P> 
	 * <P> 创建时间：2015年12月1日 下午5:12:54</P> 
	 * <P>@throws</P> 
	 */ 
	public int saveEmployee(Employee emp)throws ServiceException;
	
	
	
	/**
	 * <P> 方法名称: deleteEmployeeByEmpId</P> 
	 * <P> 方法描述:  删除员工</P> 
	 * <P> 返回类型: int</P> 
	 * <P> 创建人：lzj</P> 
	 * <P> 创建时间：2015年12月1日 下午5:11:40</P> 
	 * <P>@throws</P> 
	 */ 
		
	public int deleteEmployeeByEmpId(Long id)throws ServiceException;
	

	/**
	 * <P> 方法名称: findEmployeeByEmpId</P> 
	 * <P> 方法描述:  根据empId查询员工</P> 
	 * <P> 返回类型: Employee</P> 
	 * <P> 创建人：lzj</P> 
	 * <P> 创建时间：2015年12月1日 下午5:12:26</P> 
	 * <P>@throws</P> 
	 */ 
		
	public Employee findEmployeeByEmpId(Long id)throws ServiceException;
	
	/**
	 * <P> 方法名称: queryForPage</P> 
	 * <P> 方法描述:  带分页查询员工信息</P> 
	 * <P> 返回类型: Pager<List<Employee>></P> 
	 * <P> 创建人：lzj</P> 
	 * <P> 创建时间：2015年12月1日 下午5:05:25</P> 
	 * <P>@throws</P> 
	 */ 
	public Pager<List<Employee>> queryForPage(Pager<List<Employee>> page)throws ServiceException;
	
	/**
	 * <P> 方法名称: updateUser</P> 
	 * <P> 方法描述: 修改员工信息</P> 
	 * <P> 返回类型: int</P> 
	 * <P> 创建人：lzj</P> 
	 * <P> 创建时间：2015年12月1日 下午5:15:29</P> 
	 * <P>@throws</P> 
	 */ 
		
	public int updateEmployee(Employee emp)throws ServiceException;

}
