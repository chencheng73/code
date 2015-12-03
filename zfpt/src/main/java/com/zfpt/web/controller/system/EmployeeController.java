package com.zfpt.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zfpt.framework.controller.BaseController;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.util.PasswordHelper;
import com.zfpt.web.common.ResponsePageListResult;
import com.zfpt.web.common.ResponseResult;
import com.zfpt.web.model.system.Employee;
import com.zfpt.web.model.system.User;
import com.zfpt.web.service.system.IEmployeeService;

/**      
 * 项目名称：zfpt   
 * 类名称：EmployeeController   
 * 类描述：   
 * 创建人：lzj
 * 创建时间：2015年12月1日 下午4:22:25   
 * 修改备注：   
 * @version      
 */
  
@Controller
@RequestMapping("/system/emp")
public class EmployeeController extends BaseController{
	
	@Autowired
	private IEmployeeService employeeService;
	//跳转到员工列表页面
	@RequestMapping(value="/list")
    public String  list(){
    	return "/system/emp/list";
   	}
	
	/**
	 * <P> 方法名称: listPage</P> 
	 * <P> 方法描述:  分页查询员工集合信息</P> 
	 * <P> 返回类型: ResponsePageListResult<List<Employee>></P> 
	 * <P> 创建人：lzj</P> 
	 * <P> 创建时间：2015年12月2日 下午2:03:06</P> 
	 * <P>@throws</P> 
	 */ 
	@ResponseBody
	@RequestMapping(value="/listPage",method=RequestMethod.GET)
	public ResponsePageListResult<List<Employee>>  listPage(){
    	ResponsePageListResult<List<Employee>>  responsePageListResult=new ResponsePageListResult<List<Employee>>();
		Pager<List<Employee>> page=initPage();
    	page =employeeService.queryForPage(page);
    	responsePageListResult.put(page);
    	return responsePageListResult;
   	}
	
	@ResponseBody
	@RequestMapping(value="/get/{id}",method=RequestMethod.GET)
   	public  ResponseResult<Employee>  get(Long id){
    	ResponseResult<Employee> responseResult=new ResponseResult<Employee>(); 
    	Employee emp=null;
    	try{
    		emp = employeeService.findEmployeeByEmpId(id);
			if(emp!=null){
			   responseResult.setData(emp);	
			}else{
			   responseResult.setErrorMessage("检索不到id"+id+"对应的员工信息!");
			}
		} catch (Exception e) {
		    responseResult.setErrorMessage("获取员工信息失败!"+e);
		}
    	return responseResult;
   	}
	
	/**
	 * <P> 方法名称: save</P> 
	 * <P> 方法描述:  添加用户</P> 
	 * <P> 返回类型: ResponseResult<String></P> 
	 * <P> 创建人：lzj</P> 
	 * <P> 创建时间：2015年12月2日 下午2:49:01</P> 
	 * <P>@throws</P> 
	 */ 
		
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public ResponseResult<String>  save(Employee emp){
		ResponseResult<String> responseResult=new ResponseResult<String>();
		/**检验登录账号是否重复 **/
	    Map<String,Object> queryMap = new HashMap<String,Object>();
	    queryMap.put("propertyKey", "name");
	    queryMap.put("propertyValue","'"+emp.getName()+"'");
	    Boolean result = employeeService.queryIsUnique(Employee.class, queryMap);
	    if(!result){
	    	   responseResult.setUpdateCount(-1);
	    	   responseResult.setErrorMessage("该员工已存在，不能重复添加!"); 
	    	}else{
	    		employeeService.saveEmployee(emp);
	    		responseResult.setInfo("添加员工成功！");
	    	}
		return responseResult;
		
	}
	
	/**
	 * <P> 方法名称: delete</P> 
	 * <P> 方法描述:  删除员工信息，目前只做逻辑删除，将员工状态置为0</P> 
	 * <P> 返回类型: ResponseResult<String></P> 
	 * <P> 创建人：lzj</P> 
	 * <P> 创建时间：2015年12月2日 下午2:54:19</P> 
	 * <P>@throws</P> 
	 */ 
		
	@ResponseBody 
	@RequestMapping(value="/delete",method=RequestMethod.POST)
   	public ResponseResult<String>  delete(Long id){
    	ResponseResult<String> responseResult=new ResponseResult<String>();
    	int result = employeeService.deleteEmployeeByEmpId(id);
    	if(result>0){
    		responseResult.setUpdateCount(result);
    		responseResult.setInfo("删除员工成功！");
    	}else{
    		responseResult.setInfo("删除员工失败！");
    	}
		return responseResult;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST)
   	public  ResponseResult<String>  update(Employee emp){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
        int result = employeeService.updateEmployee(emp);
        if(result>0){
        	responseResult.setInfo("修改成功");	
        }else{
        	responseResult.setInfo("修改失败");
        }
    	return responseResult;
   	}
}
