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
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.model.system.Resource;
import com.zfpt.web.service.system.IResourceService;

/**
 * 项目名称：zfpt   
 * 类名称：ResourceController   
 * 类描述：资源管理控制器   
 * 创建人：chens
 * 创建时间：2015年11月30日 上午11:15:49   
 * 修改备注：   
 * @version
 */
@Controller
@RequestMapping("/system/resource")
public class ResourceController extends BaseController {
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
   	public @ResponseBody ResponsePageListResult<List<Resource>>  listPage(){
    	Pager<List<Resource>> page=initPage();
    	page =resourceService.queryForPage(page);
    	ResponsePageListResult<List<Resource>>  responsePageListResult=new ResponsePageListResult<List<Resource>>();
    	responsePageListResult.put(page);
    	return responsePageListResult;
   	}
    
	 /**
     * 方法名称: get
     * 方法描述: 查询资源明细
     * 返回类型: ResponseResult
     * 创建人：chens
     * 创建时间：2015年11月23日 下午5:45:46
     * @throws
     */
    @RequestMapping(value="/get/{id}",method=RequestMethod.GET)
   	public @ResponseBody ResponseResult<Resource>  get(@PathVariable Integer id){
    	ResponseResult<Resource> responseResult=new ResponseResult<Resource>(); 
    	Resource resource=null;
    	try{
    		resource=resourceService.findResourceById(id);
			if(resource!=null){
			   responseResult.setData(resource);	
			}else{
			   responseResult.setErrorMessage("检索不到id"+id+"对应的资源信息!");
			}
		} catch (Exception e) {
		    responseResult.setErrorMessage("获取资源信息失败!"+e);
		}
    	return responseResult;
   	}
    
    /**
     * 方法名称: save
     * 方法描述: 保存系统资源信息
     * 返回类型: ResponseResult<Resource>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  save(Resource resource){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	/**检验资源名称是否重复 **/
    	Map<String,Object> queyMap=new HashMap<String, Object>();
        queyMap.put("propertyKey","name");
        queyMap.put("propertyValue","'"+resource.getResName()+"'");
    	Boolean  isUnique=resourceService.queryIsUnique(Resource.class,queyMap);
    	if(!isUnique){
    	   responseResult.setUpdateCount(-1);
    	   responseResult.setErrorMessage("该资源名称已存在，不能重复添加!"); 
    	}else{
    		resource.setCreateAt(DateUtils.currentDatetime());
    		resource.setStatus(0);
            resourceService.saveResource(resource);
            responseResult.setUpdateCount(1);
    	    responseResult.setInfo("资源新增成功");	
    	}
    	return responseResult;
   	}
    
    /**
     * 方法名称: update
     * 方法描述: 修改系统资源信息
     * 返回类型: ResponseResult<String>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/update",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  update(Resource resource){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
        resourceService.updateResource(resource);
	    responseResult.setUpdateCount(1);
	    responseResult.setInfo("资源信息修改成功");
    	return responseResult;
   	}
    
    /**
     * 方法名称: delete
     * 方法描述: 删除系统资源信息
     * 返回类型: ResponseResult<Role>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  delete(Integer id){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	int result=resourceService.deleteResourceById(id);
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
     * 方法描述: 根据父节点查询对应的菜单列表
     * 返回类型: ResponseResult<String>
     * 创建人：chens
     * 创建时间：2015年11月30日 下午1:58:38
     * @throws
     */
    @RequestMapping(value="/queryResources/{parentId}")
	public @ResponseBody Object  queryForList(@PathVariable String parentId){
    	List<MenuTreeObject> menuTreeObjects=resourceService.findResourcesByParentId(Integer.valueOf(parentId));
    	return JsonUtils.toJSON(menuTreeObjects);
   	}
    
    
    
    
    /***************** 测试菜单Tree ********************/
    
    
    //跳转到测试页面main
    @RequestMapping(value="/to_list")
    public String  to_list(){
    	return "/system/resource/listResource";
   	}
    
    //跳转到菜单tree
    @RequestMapping(value="/tree")
    public String  teee(){
    	return "/system/resource/tree";
   	}
    
    //跳转到right侧表单
    @RequestMapping(value="/to_add")
    public String  to_add(String resId,Model model){
    	Resource resource=null;
    	if(StringUtils.isNotEmpty(resId)){
    	   resource=resourceService.findResourceById(Integer.valueOf(resId));
    	}
    	model.addAttribute("resource", resource);
    	return "/system/resource/main";
   	}
    
    
    
    
    
}
