package com.zfpt.web.service.system;

import java.util.List;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.service.GenericService;
import com.zfpt.web.model.system.Resource;
import com.zfpt.web.model.system.Role;
public interface IRoleService extends GenericService {
 
	public Pager<List<Role>> queryForPage(Pager<List<Role>> page)throws ServiceException;
	
	public int deleteRoleById(Integer id)throws ServiceException;
	/**
	 * 方法名称: findUserById
	 * 方法描述: 返回角色明细
	 * 返回类型: Role
	 * 创建人：chens
	 * 创建时间：2015年11月26日 下午4:45:03
	 * @throws
	 */
	public Role findRoleById(Integer id)throws ServiceException;
	
	/**
	 * 方法名称: findResourceByUserId
	 * 方法描述: 根据角色id获取对应的权限列表
	 * 返回类型: List<Role>
	 * 创建人：chens
	 * 创建时间：2015年11月26日 上午11:39:43
	 * @throws
	 */
	public List<Resource> findReourceByRoleId(Integer id)throws ServiceException;
	
	
	/**
	 * 方法名称: saveRole
	 * 方法描述: 保存用户信息
	 * 返回类型: int
	 * 创建人：chens
	 * 创建时间：2015年11月26日 上午11:37:53
	 * @throws
	 */
	public int saveRole(Role role)throws ServiceException;
	
	/**
	 * 方法名称: updateRole
	 * 方法描述: 修改用户信息
	 * 返回类型: int
	 * 创建人：chens
	 * 创建时间：2015年11月26日 上午11:37:53
	 * @throws
	 */
	public int updateRole(Role role)throws ServiceException;
	
    /**
     * 方法名称: saveRoleAndResource
     * 方法描述: 保存用户关联角色信息
     * 返回类型: int
     * 创建人：chens
     * 创建时间：2015年11月26日 下午3:24:08
     * @throws
     */
	public int   saveRoleAndResource(Integer id, Integer[] resource)throws ServiceException;
	
	
}
