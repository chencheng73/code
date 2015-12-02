package com.zfpt.web.service.system;

import java.util.List;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.service.GenericService;
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.model.system.Resource;

public interface IResourceService extends GenericService {
 
	public Pager<List<Resource>> queryForPage(Pager<List<Resource>> page)throws ServiceException;
	
	public int deleteResourceById(Integer id)throws ServiceException;
	
	/**
	 * 方法名称: findResourceById
	 * 方法描述: 查询资源明细
	 * 返回类型: Resource
	 * 创建人：chens
	 * 创建时间：2015年11月30日 上午11:17:20
	 * @throws
	 */
	public Resource findResourceById(Integer id)throws ServiceException;
	
	/**
	 * 方法名称: findResourcesByParentId
	 * 方法描述: 根据父类id获取资源明细
	 * 返回类型: List<MenuTreeObject>
	 * 创建人：chens
	 * 创建时间：2015年11月30日 上午11:18:05
	 * @throws
	 */
	public List<MenuTreeObject> findResourcesByParentId(Integer parentId)throws ServiceException;
	
	
    /**
     * 方法名称: saveResource
     * 方法描述: 保存资源信息
     * 返回类型: int
     * 创建人：chens
     * 创建时间：2015年11月30日 上午11:19:00
     * @throws
     */
	public int saveResource(Resource resource)throws ServiceException;
	
	/**
	 * 方法名称: updateResource
	 * 方法描述: 修改资源信息
	 * 返回类型: int
	 * 创建人：chens
	 * 创建时间：2015年11月30日 上午11:21:09
	 * @throws
	 */
	public int updateResource(Resource resource)throws ServiceException;
	
	
}
