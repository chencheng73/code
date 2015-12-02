package com.zfpt.web.service.system;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.service.GenericServiceImpl;
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.model.system.Department;
/**
 * 项目名称：zfpt   
 * 类名称：DepartmentServiceImp   
 * 类描述：组织部门管理业务实现   
 * 创建人：chens
 * 创建时间：2015年12月1日 下午3:44:29   
 * 修改备注：   
 * @version
 */
@Service("departmentService")
public class DepartmentServiceImp extends GenericServiceImpl implements IDepartmentService {
 
	public Pager<List<Department>> queryForPage(Pager<List<Department>> page) throws ServiceException {
		return super.queryForPage(Department.class, page);
	}

	public int deleteDepartmentById(Integer id) throws ServiceException {
		/**删除子节点的资源信息 **/
		this.deleteDepartmentByParentId(id);
		return super.delete(Department.class,id);
	}

	public int deleteDepartmentByParentId(Integer parentId) throws ServiceException{
		return getSqlSessionTemplate().delete(Department.class.getName()+".deleteDepartmentByParentId", parentId);
	}
	
	
	public Department findDepartmentById(Integer id) throws ServiceException {
		Object object=super.selectOne(Department.class,id);
		return   object==null?null:(Department)object ;
	}

	public List<MenuTreeObject> findDepartmentByParentId(Integer parentId) throws ServiceException {
		List<Department> resources=getSqlSessionTemplate().selectList(Department.class.getName()+".selectDepartmentByParentId", parentId);
		List<MenuTreeObject> menuTreeObjects=new ArrayList<MenuTreeObject>();	
    	if(resources!=null&&resources.size()>0){
           MenuTreeObject menuTreeObject=null;
    		for(Department department : resources) {
    			menuTreeObject=new MenuTreeObject();
        	    menuTreeObject.setId(department.getId());
        	    menuTreeObject.setpId(department.getParentId());
        	    menuTreeObject.setName(department.getName());
        	    menuTreeObject.setOpen(true);/**默认展开 **/
        	    menuTreeObject.setChecked(false);
        	    menuTreeObjects.add(menuTreeObject);
           }
        }
		return menuTreeObjects;
	}

	public int saveDepartment(Department department) throws ServiceException {
		return super.insert(department);
	}

	public int updateDepartment(Department department) throws ServiceException {
		return super.update(department);
	}
 

 
}
