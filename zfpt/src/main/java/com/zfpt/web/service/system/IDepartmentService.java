package com.zfpt.web.service.system;

import java.util.List;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.service.GenericService;
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.model.system.Department;

public interface IDepartmentService extends GenericService {
 
	public Pager<List<Department>> queryForPage(Pager<List<Department>> page)throws ServiceException;
	
	public int deleteDepartmentById(Integer id)throws ServiceException;
	
	/**
	 * 方法名称: findDepartmentById
	 * 方法描述: 查询资源明细
	 * 返回类型: Department
	 * 创建人：chens
	 * 创建时间：2015年11月30日 上午11:17:20
	 * @throws
	 */
	public Department findDepartmentById(Integer id)throws ServiceException;
	
	/**
	 * 方法名称: findDepartmentsByParentId
	 * 方法描述: 根据父类id获取资源明细
	 * 返回类型: List<MenuTreeObject>
	 * 创建人：chens
	 * 创建时间：2015年11月30日 上午11:18:05
	 * @throws
	 */
	public List<MenuTreeObject> findDepartmentByParentId(Integer parentId)throws ServiceException;
	
	
    /**
     * 方法名称: saveDepartment
     * 方法描述: 保存资源信息
     * 返回类型: int
     * 创建人：chens
     * 创建时间：2015年11月30日 上午11:19:00
     * @throws
     */
	public int saveDepartment(Department department)throws ServiceException;
	
	/**
	 * 方法名称: updateDepartment
	 * 方法描述: 修改资源信息
	 * 返回类型: int
	 * 创建人：chens
	 * 创建时间：2015年11月30日 上午11:21:09
	 * @throws
	 */
	public int updateDepartment(Department department)throws ServiceException;
	
	
}
