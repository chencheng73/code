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
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.model.system.Resource;
import com.zfpt.web.model.system.Role;
import com.zfpt.web.service.system.IResourceService;
import com.zfpt.web.service.system.IRoleService;

/**      
 * 项目名称：zfpt   
 * 类名称：RoleController   
 * 类描述：角色管理控制器   
 * 创建人：chens
 * 创建时间：2015年11月23日 上午11:42:23   
 * 修改备注：   
 * @version      
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
	@Autowired
    private IRoleService roleService;
	@Autowired
	private IResourceService resourceService;
	
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
   	public @ResponseBody ResponsePageListResult<List<Role>>  listPage(){
    	Pager<List<Role>> page=initPage();
    	page =roleService.queryForPage(page);
    	ResponsePageListResult<List<Role>>  responsePageListResult=new ResponsePageListResult<List<Role>>();
    	responsePageListResult.put(page);
    	return responsePageListResult;
   	}
    
	 /**
     * 方法名称: get
     * 方法描述: 查询角色明细(包含权限信息)
     * 返回类型: ResponseResult
     * 创建人：chens
     * 创建时间：2015年11月23日 下午5:45:46
     * @throws
     */
    @RequestMapping(value="/get/{id}",method=RequestMethod.GET)
   	public @ResponseBody ResponseResult<Role>  get(@PathVariable Integer id){
    	ResponseResult<Role> responseResult=new ResponseResult<Role>(); 
    	Role role=null;
    	try{
    		role=roleService.findRoleById(id);
			if(role!=null){
			   responseResult.setData(role);	
			}else{
			   responseResult.setErrorMessage("检索不到id"+id+"对应的角色信息!");
			}
		} catch (Exception e) {
		    responseResult.setErrorMessage("获取角色信息失败!"+e);
		}
    	return responseResult;
   	}
    
    
    /**
     * 方法名称: save
     * 方法描述: 保存系统角色信息
     * 返回类型: ResponseResult<Role>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  save(Role role){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	/**检验角色名称是否重复 **/
    	Map<String,Object> queyMap=new HashMap<String, Object>();
        queyMap.put("propertyKey","roleName");
        queyMap.put("propertyValue","'"+role.getRoleName()+"'");
    	Boolean  isUnique=roleService.queryIsUnique(Role.class,queyMap);
    	if(!isUnique){
    	   responseResult.setUpdateCount(-1);
    	   responseResult.setErrorMessage("该角色已存在，不能重复添加!"); 
    	}else{
    		role.setCreateAt(DateUtils.currentDatetime());
    		role.setStatus(0);
            roleService.saveRole(role);
    	    responseResult.setInfo("角色新增成功");	
    	}
    	return responseResult;
   	}
    
    /**
     * 方法名称: update
     * 方法描述: 修改系统角色信息
     * 返回类型: ResponseResult<String>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/update",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  update(Role role){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
        roleService.updateRole(role);
        responseResult.setUpdateCount(1);
	    responseResult.setInfo("角色信息修改成功");	
    	return responseResult;
   	}
    
    /**
     * 方法名称: delete
     * 方法描述: 删除系统角色信息
     * 返回类型: ResponseResult<Role>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  delete(Integer id){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	int result=roleService.deleteRoleById(id);
    	if(result>0){
    	   responseResult.setUpdateCount(result);
    	   responseResult.setInfo("操作成功!");	
    	}else{
		   responseResult.setInfo("操作失败!");	
    	}
    	return responseResult;
   	}
    
    
    /**
     * 方法名称: disRoleResource
     * 方法描述: 授权资源权限
     * 返回类型: ResponseResult<String>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/discResource",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  disRoleResource(Integer id,String resIds){
    	/**接收授权的资源id集合**/
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
	    if(StringUtils.isEmpty(resIds)){
    	   responseResult.setUpdateCount(-1);
    	   responseResult.setErrorMessage("授权信息不能为空");	
    	}else{
    		/**将接收的请求数据进行处理 **/
    	   Integer [] roleArr=StringUtils.String2IntegerArray(resIds);
    	   try{
    		   roleService.saveRoleAndResource(id,roleArr);
    		   responseResult.setInfo("授权成功!"); 
    	   } catch (Exception e) {
    		   responseResult.setUpdateCount(-1);
    		   responseResult.setErrorMessage("保存失败!"+e);
    	   }
    	}
    	return responseResult;
   	}
    
    
    /**
     * 方法名称: queryForList
     * 方法描述: 查询角色对应的资源列表
     * 返回类型: Object
     * 创建人：chens
     * 创建时间：2015年11月30日 下午5:39:08
     * @throws
     */
    @RequestMapping(value="/queryResources/{roleid}")
	public @ResponseBody Object  queryForList(@PathVariable String roleid){
    	Role role=roleService.findRoleById(Integer.valueOf(roleid));
    	/**获取已授权的资源信息 **/
    	List<Resource> resources=role.getResources();
    	/**获取菜单列表 **/ 
    	List<MenuTreeObject>  menuTreeObjects=resourceService.findResourcesByParentId(0);
     	if(menuTreeObjects!=null&&menuTreeObjects.size()>0&&resources!=null){
    	   for(MenuTreeObject menuTreeObject : menuTreeObjects) {
    		   for(Resource resource:resources){
    			   /**如果之前已被授权，则选中 **/
    			   if(resource.getId().equals(menuTreeObject.getId())){
    				  menuTreeObject.setChecked(true); 
    			   }
    		   }
		   }
    	}
    	return JsonUtils.toJSON(menuTreeObjects);
   	}
    
    
    
    //跳转到用户列表、新增用户页面
    @RequestMapping(value="/to_list")
    public String  to_add(){
    	return "/system/role/testRole";
   	}
    
    //跳转到资源列表
    @RequestMapping(value="/to_ResourceList")
    public String  to_roleList(String roleid,Model model){
    	model.addAttribute("roleid",roleid);
    	return "/system/role/listResource";
   	}
    
    
    
    
    
    
    
    
    
    
}
