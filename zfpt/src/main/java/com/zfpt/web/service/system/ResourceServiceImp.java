package com.zfpt.web.service.system;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.service.GenericServiceImpl;
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.model.system.Resource;
/**
 * 项目名称：zfpt   
 * 类名称：ResourceServiceImp   
 * 类描述：系统资源管理业务实现   
 * 创建人：chens
 * 创建时间：2015年11月23日 下午3:44:29   
 * 修改备注：   
 * @version
 */
@Service("resourceService")
public class ResourceServiceImp extends GenericServiceImpl implements IResourceService {
 
	public Pager<List<Resource>> queryForPage(Pager<List<Resource>> page) throws ServiceException {
		return super.queryForPage(Resource.class, page);
	}

	public int deleteResourceById(Integer id) throws ServiceException {
		/**删除子节点的资源信息 **/
		this.deleteResourceByParentId(id);
		return super.delete(Resource.class,id);
	}

	public int deleteResourceByParentId(Integer parentId) throws ServiceException{
		return getSqlSessionTemplate().delete(Resource.class.getName()+".deleteResourceByParentId", parentId);
	}
	
	
	public Resource findResourceById(Integer id) throws ServiceException {
		Object object=super.selectOne(Resource.class,id);
		return   object==null?null:(Resource)object ;
	}

	public List<MenuTreeObject> findResourcesByParentId(Integer parentId) throws ServiceException {
		List<Resource> resources=getSqlSessionTemplate().selectList(Resource.class.getName()+".selectResourceByParentId", parentId);
		List<MenuTreeObject> menuTreeObjects=new ArrayList<MenuTreeObject>();	
    	if(resources!=null&&resources.size()>0){
           MenuTreeObject menuTreeObject=null;
    		for(Resource resource : resources) {
    			menuTreeObject=new MenuTreeObject();
        	    menuTreeObject.setId(resource.getId());
        	    menuTreeObject.setpId(resource.getParentId());
        	    menuTreeObject.setName(resource.getResName());
        	     menuTreeObject.setHref(resource.getResUrl());
        	    menuTreeObject.setOpen(true);/**默认展开 **/
        	    menuTreeObject.setChecked(false);
        	    menuTreeObjects.add(menuTreeObject);
           }
        }
		return menuTreeObjects;
	}

	public int saveResource(Resource resource) throws ServiceException {
		return super.insert(resource);
	}

	public int updateResource(Resource resource) throws ServiceException {
		return super.update(resource);
	}
	
	 

 
}
