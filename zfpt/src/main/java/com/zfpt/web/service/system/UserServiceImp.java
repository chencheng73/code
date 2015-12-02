package com.zfpt.web.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zfpt.framework.dao.GenericDao;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.service.GenericServiceImpl;
import com.zfpt.web.common.MapperNameSpace;
import com.zfpt.web.model.system.Role;
import com.zfpt.web.model.system.User;
/**
 * 项目名称：zfpt   
 * 类名称：UserServiceImp   
 * 类描述：系统用户管理业务实现   
 * 创建人：chens
 * 创建时间：2015年11月23日 下午3:44:29   
 * 修改备注：   
 * @version
 */
@Service("userService")
public class UserServiceImp extends GenericServiceImpl implements IUserService,MapperNameSpace {
	@Autowired
	private GenericDao baseDao;
	
	public Pager<List<User>> queryForPage(Pager<List<User>> page) throws ServiceException {
        return  super.queryForPage(User.class, page);
	}

	public int saveUser(User user) throws ServiceException {
		 return super.insert(user);
	}

	public int updateUser(User user) throws ServiceException {
		return super.update(user);
	}
    
	public int deleteUserById(Integer id) throws ServiceException {
		/**删除用户对应的角色信息 **/
		deleteUserAndRoles(id);
		return baseDao.delete(User.class,id);
	}
 
	/**
	 * 查询用户明细
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @see com.zfpt.web.service.system.IUserService#findUserById(java.lang.Integer)
	 */
	public User findUserById(Integer id) throws ServiceException {
		User user=(User)baseDao.selectOne(User.class,id);
		List<Role> userRoles=null;
		if(user!=null){
           userRoles=findRolesByUserId(user.getId());  
           if(userRoles.size()>0){
        	   user.setRoles(userRoles);	
           }
		}
		return  user;
	}
	
	public User findUserById(Map<String,Object> dataMap) throws ServiceException {
		return super.selectOne(User.class,dataMap);
	}

	public User loginUser(User user) throws ServiceException {
		return super.selectOne(user);
	}
	
	/**
	 * 新增用户角色信息
	 * @param id
	 * @param roleIds
	 * @return
	 * @throws ServiceException
	 * @see com.zfpt.web.service.system.IUserService#saveUserAndRoles(java.lang.Integer, java.lang.Integer[])
	 */
	public int saveUserAndRoles(Integer id, Integer[] roleIds) throws ServiceException {
		List<Map<String,Object>> userAndRoleVos=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null;
		for(Integer roleId:roleIds){
            map=new HashMap<String, Object>();
            map.put("userid", id);
            map.put("roleid", roleId);
            userAndRoleVos.add(map);
		}
		/**删除之前授权的信息 **/
		deleteUserAndRoles(id);
        return super.getSqlSessionTemplate().insert(User.class.getName()+".saveUserAndRoles", userAndRoleVos);
	}
    /**
     * 根据用户获取角色信息
     * @param userId
     * @return
     * @throws ServiceException
     * @see com.zfpt.web.service.system.IUserService#findRolesByUserId(java.lang.Integer)
     */
	public List<Role> findRolesByUserId(Integer userId) throws ServiceException {
		return getSqlSessionTemplate().selectList(User.class.getName()+".getUserAndRoleById",userId);
	}
	
	/**
	 * 删除用户对应的角色
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @see com.zfpt.web.service.system.IUserService#deleteUserAndRoles(java.lang.Integer)
	 */
	private int deleteUserAndRoles(Integer id) throws ServiceException {
		return super.getSqlSessionTemplate().delete(User.class.getName()+".deleteUserAndRoles", id);
	}
    
	

 
}
