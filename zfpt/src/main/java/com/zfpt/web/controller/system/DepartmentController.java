/**
 * 
 */
package com.zfpt.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zfpt.framework.controller.BaseController;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.util.DateUtils;
import com.zfpt.framework.util.JsonUtils;
import com.zfpt.framework.util.StringUtils;
import com.zfpt.web.common.ResponsePageListResult;
import com.zfpt.web.common.ResponseResult;
import com.zfpt.web.model.system.Department;
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.service.system.IDepartmentService;

/**
 * 项目名称：zfpt   
 * 类名称：DepartmentController   
 * 类描述：部门管理控制器      
 * 创建人：chens
 * 创建时间：2015年12月1日 下午5:15:06   
 * 修改备注：   
 * @version
 */
@Controller
@RequestMapping("/system/department")
public class DepartmentController extends BaseController {
  @Autowired
  private IDepartmentService departmentService;
  
	 /**
     * 方法名称: listPage
     * 方法描述: 分页查询
     * 返回类型: ResponsePageListResult
     * 创建人：chens
     * 创建时间：2015年11月23日 下午5:45:46
     * @throws
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/listPage",method=RequestMethod.GET)
   	public @ResponseBody ResponsePageListResult<List<Department>>  listPage(){
    	Pager<List<Department>> page=initPage();
    	page =departmentService.queryForPage(page);
    	ResponsePageListResult<List<Department>>  responsePageListResult=new ResponsePageListResult<List<Department>>();
    	responsePageListResult.put(page);
    	return responsePageListResult;
   	}
    
	 /**
     * 方法名称: get
     * 方法描述: 查询部门明细
     * 返回类型: ResponseResult
     * 创建人：chens
     * 创建时间：2015年11月23日 下午5:45:46
     * @throws
     */
    @RequestMapping(value="/get/{id}",method=RequestMethod.GET)
   	public @ResponseBody ResponseResult<Department>  get(@PathVariable Integer id){
    	ResponseResult<Department> responseResult=new ResponseResult<Department>(); 
    	Department department=null;
    	try{
    		department=departmentService.findDepartmentById(id);
			if(department!=null){
			   responseResult.setData(department);	
			}else{
			   responseResult.setErrorMessage("检索不到id"+id+"对应的部门信息!");
			}
		} catch (Exception e) {
		    responseResult.setErrorMessage("获取部门信息失败!"+e);
		}
    	return responseResult;
   	}
    
    /**
     * 方法名称: save
     * 方法描述: 保存系统部门信息
     * 返回类型: ResponseResult<Department>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  save(Department department){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	/**检验部门名称是否重复 **/
    	Map<String,Object> queyMap=new HashMap<String, Object>();
        queyMap.put("propertyKey","name");
        queyMap.put("propertyValue","'"+department.getName()+"'");
    	Boolean  isUnique=departmentService.queryIsUnique(Department.class,queyMap);
    	if(!isUnique){
    	   responseResult.setUpdateCount(-1);
    	   responseResult.setErrorMessage("该部门名称已存在，不能重复添加!"); 
    	}else{
    		department.setCreateAt(DateUtils.currentDatetime());
    		department.setStatus(0);
            departmentService.saveDepartment(department);
            responseResult.setUpdateCount(1);
    	    responseResult.setInfo("部门新增成功");	
    	}
    	return responseResult;
   	}
    
    /**
     * 方法名称: update
     * 方法描述: 修改系统部门信息
     * 返回类型: ResponseResult<String>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/update",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  update(Department department){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
        departmentService.updateDepartment(department);
	    responseResult.setUpdateCount(1);
	    responseResult.setInfo("部门信息修改成功");
    	return responseResult;
   	}
    
    /**
     * 方法名称: delete
     * 方法描述: 删除系统部门信息
     * 返回类型: ResponseResult<Department>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  delete(Integer id){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	int result=departmentService.deleteDepartmentById(id);
    	if(result>0){
    	   responseResult.setUpdateCount(result);
    	   responseResult.setInfo("操作成功!");	
    	}else{
		   responseResult.setErrorMessage("操作失败!");	
    	}
    	return responseResult;
   	}
    
    
    
    /**
     * 方法名称: list
     * 方法描述: 根据父节点查询对应的部门列表
     * 返回类型: ResponseResult<String>
     * 创建人：chens
     * 创建时间：2015年11月30日 下午1:58:38
     * @throws
     */
    @RequestMapping(value="/queryDepartment/{parentId}")
	public @ResponseBody Object  queryForList(@PathVariable String parentId,String inputType){
    	List<MenuTreeObject> menuTreeObjects=departmentService.findDepartmentByParentId(Integer.valueOf(parentId));
    	return JsonUtils.toJSON(menuTreeObjects);
   	}
    
    /**
     * 方法名称: referDepartment
     * 方法描述: 组织部门tree参照
     * 级别：一级/二级
     * 返回类型: Object
     * 创建人：chens
     * 创建时间：2015年12月2日 下午3:25:57
     * @throws
     */
    @RequestMapping(value="/referDepartment")
  	public @ResponseBody Object  referDepartment(String inputType,String level){
      	List<MenuTreeObject> menuTreeObjects=departmentService.findDepartmentByParentId(0);
      	return JsonUtils.toJSON(menuTreeObjects);
 	}
    
    
    /***************** 测试部门Tree ********************/
    
    
    //跳转到测试页面main
    @RequestMapping(value="/to_list")
    public String  to_list(){
    	return "/system/department/listDepartment";
   	}
    
    //跳转到菜单tree
    @RequestMapping(value="/tree")
    public String  teee(){
    	return "/system/department/tree";
   	}
    
    //跳转到right侧表单
    @RequestMapping(value="/to_add")
    public String  to_add(String deptId,Model model){
    	Department department=null;
    	if(StringUtils.isNotEmpty(deptId)){
    	   department=departmentService.findDepartmentById(Integer.valueOf(deptId));
    	}
    	model.addAttribute("department", department);
    	return "/system/department/main";
   	}
    
    //跳转到部门参照页面  ； 类型：单选/多选;
    @RequestMapping(value="/to_referDept")
    public String  to_referDept(Model model,String inputType){
    	model.addAttribute("inputType",inputType);
    	return "/system/department/referDepartment";
    
    }
    
    
}
