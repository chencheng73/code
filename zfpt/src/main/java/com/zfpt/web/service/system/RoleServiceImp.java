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
import com.zfpt.web.model.system.Resource;
import com.zfpt.web.model.system.Role;
/**
 * 项目名称：zfpt   
 * 类名称：UserServiceImp   
 * 类描述：系统用户管理业务实现   
 * 创建人：chens
 * 创建时间：2015年11月23日 下午3:44:29   
 * 修改备注：   
 * @version
 */
@Service("roleService")
public class RoleServiceImp extends GenericServiceImpl implements IRoleService {
	@Autowired
	private GenericDao baseDao;
	
	public Pager<List<Role>> queryForPage(Pager<List<Role>> page) throws ServiceException {
        return  super.queryForPage(Role.class, page);
	}

	public int saveRole(Role role) throws ServiceException {
		 return super.insert(role);
	}

	public int updateRole(Role role) throws ServiceException {
		return super.update(role);
	}
    
	public int deleteRoleById(Integer id) throws ServiceException {
		/**删除角色对应的权限信息 **/
		deleteRoleAndRource(id);
		return baseDao.delete(Role.class,id);
	}
 
	/**
	 * 查询用户明细
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @see com.zfpt.web.service.system.IUserService#findUserById(java.lang.Integer)
	 */
	public Role findRoleById(Integer id) throws ServiceException {
		Role role=(Role)baseDao.selectOne(Role.class,id);
		List<Resource> resources=null;
		if(role!=null){
		   resources=findReourceByRoleId(role.getId());  
           if(resources.size()>0){
        	  role.setResources(resources);	
           }
		}
		return  role;
	}
	
	public Role findUserById(Map<String,Object> dataMap) throws ServiceException {
		return super.selectOne(Role.class,dataMap);
	}

 
	/**
	 * 新增角色资源信息
	 * @param id
	 * @param roleIds
	 * @return
	 * @throws ServiceException
	 * @see com.zfpt.web.service.system.IUserService#saveUserAndRoles(java.lang.Integer, java.lang.Integer[])
	 */
	public int saveRoleAndResource(Integer id, Integer[] resourceIds) throws ServiceException {
		List<Map<String,Object>> roleAndResource=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null;
		for(Integer resourceId:resourceIds){
            map=new HashMap<String, Object>();
            map.put("roleid", id);
            map.put("resourceid", resourceId);
            roleAndResource.add(map);
		}
		/**删除之前授权的信息 **/
		deleteRoleAndRource(id);
        return super.getSqlSessionTemplate().insert(Role.class.getName()+".saveRoleAndResource", roleAndResource);
	}
	
    /**
     * 根据角色id获取对应的权限列表
     * @param roleId
     * @return
     * @throws ServiceException
     * @see com.zfpt.web.service.system.IUserService#findRolesByUserId(java.lang.Integer)
     */
	public List<Resource> findReourceByRoleId(Integer roleId) throws ServiceException {
		return getSqlSessionTemplate().selectList(Role.class.getName()+".getRoleAndResourceById",roleId);
	}
	
	/**
	 * 删除角色对应的权限
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @see com.zfpt.web.service.system.IUserService#deleteRoleAndRource(java.lang.Integer)
	 */
	private int deleteRoleAndRource(Integer id) throws ServiceException {
		return super.getSqlSessionTemplate().delete(Role.class.getName()+".deleteRoleAndResource", id);
	}
 

 
}
