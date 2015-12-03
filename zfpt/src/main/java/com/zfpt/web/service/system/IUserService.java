package com.zfpt.web.service.system;

import java.util.List;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.service.GenericService;
import com.zfpt.web.model.system.Role;
import com.zfpt.web.model.system.User;

public interface IUserService extends GenericService {
 
	public Pager<List<User>> queryForPage(Pager<List<User>> page)throws ServiceException;
	
	public int deleteUserById(Integer id)throws ServiceException;
	/**
	 * 方法名称: findUserById
	 * 方法描述: 返回用户明细
	 * 返回类型: User
	 * 创建人：chens
	 * 创建时间：2015年11月26日 下午4:45:03
	 * @throws
	 */
	public User findUserById(Integer id)throws ServiceException;
	
	/**
	 * 方法名称: loginUser
	 * 方法描述: 验证用户信息
	 * 返回类型: User
	 * 创建人：chens
	 * 创建时间：2015年11月26日 上午11:40:49
	 * @throws
	 */
	public User loginUser(String loginCode)throws ServiceException;
	
	/**
	 * 方法名称: findRolesByUserId
	 * 方法描述: 根据用户id获取对应的角色列表
	 * 返回类型: List<Role>
	 * 创建人：chens
	 * 创建时间：2015年11月26日 上午11:39:43
	 * @throws
	 */
	public List<Role> findRolesByUserId(Integer id)throws ServiceException;
	
	
	/**
	 * 方法名称: saveUserAndRoles
	 * 方法描述: 保存用户信息
	 * 返回类型: int
	 * 创建人：chens
	 * 创建时间：2015年11月26日 上午11:37:53
	 * @throws
	 */
	public int saveUser(User user)throws ServiceException;
	
	/**
	 * 方法名称: updateUserAndRoles
	 * 方法描述: 修改用户信息
	 * 返回类型: int
	 * 创建人：chens
	 * 创建时间：2015年11月26日 上午11:37:53
	 * @throws
	 */
	public int updateUser(User user)throws ServiceException;
	
    /**
     * 方法名称: saveUserAndRoles
     * 方法描述: 保存用户关联角色信息
     * 返回类型: int
     * 创建人：chens
     * 创建时间：2015年11月26日 下午3:24:08
     * @throws
     */
	public int   saveUserAndRoles(Integer id, Integer[] roleIds)throws ServiceException;
	
	
}
