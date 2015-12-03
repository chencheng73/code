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

import com.zfpt.framework.context.LoginManger;
import com.zfpt.framework.controller.BaseController;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.util.DateUtils;
import com.zfpt.framework.util.MD5Utils;
import com.zfpt.framework.util.PasswordHelper;
import com.zfpt.framework.util.StringUtils;
import com.zfpt.web.common.ResponsePageListResult;
import com.zfpt.web.common.ResponseResult;
import com.zfpt.web.model.system.Role;
import com.zfpt.web.model.system.User;
import com.zfpt.web.service.system.IUserService;
/**
 * 项目名称：zfpt   
 * 类名称：UserController   
 * 类描述：系统用户管理控制器   
 * 创建人：chens
 * 创建时间：2015年11月23日 下午5:36:20   
 * 修改备注：   
 * @version
 */
//@RestController
@Controller
@RequestMapping("/system/user")
public class UserController  extends BaseController{
	@Autowired
    private IUserService userService;
	
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
   	public @ResponseBody ResponsePageListResult<List<User>>  listPage(){
    	ResponsePageListResult<List<User>>  responsePageListResult=new ResponsePageListResult<List<User>>();
		Pager<List<User>> page=initPage();
    	page =userService.queryForPage(page);
    	responsePageListResult.put(page);
    	return responsePageListResult;
   	}
    
	 /**
     * 方法名称: get
     * 方法描述: 查询用户明细(包含角色信息)
     * 返回类型: ResponseResult
     * 创建人：chens
     * 创建时间：2015年11月23日 下午5:45:46
     * @throws
     */
    @RequestMapping(value="/get/{id}",method=RequestMethod.GET)
   	public @ResponseBody ResponseResult<User>  get(@PathVariable Integer id){
    	ResponseResult<User> responseResult=new ResponseResult<User>(); 
    	User user=null;
    	try{
    		user=userService.findUserById(id);
			if(user!=null){
	    	   user.setUserPassword(MD5Utils.getMD5Digest(""));
			   responseResult.setData(user);	
			}else{
			   responseResult.setErrorMessage("检索不到id"+id+"对应的用户信息!");
			}
		} catch (Exception e) {
		    responseResult.setErrorMessage("获取用户信息失败!"+e);
		}
    	return responseResult;
   	}
    
    /**
     * 方法名称: save
     * 方法描述: 保存系统用户信息
     * 返回类型: ResponsePageListResult<List<User>>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  save(User user){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	/**检验登录账号是否重复 **/
    	Map<String,Object> queyMap=new HashMap<String, Object>();
        queyMap.put("propertyKey","loginCode");
        queyMap.put("propertyValue","'"+user.getLoginCode()+"'");
    	Boolean  isUnique=userService.queryIsUnique(User.class,queyMap);
    	if(!isUnique){
    	   responseResult.setUpdateCount(-1);
    	   responseResult.setErrorMessage("该登录账号已存在，不能重复添加!"); 
    	}else{
		   PasswordHelper passwordHelper = new PasswordHelper();
    	   user.setCreateAt(DateUtils.currentDatetime());
           user.setStatus(0);
           /**将原始密码进行加密操作 **/
           passwordHelper.encryptPassword(user);
           userService.saveUser(user);
    	   responseResult.setInfo("用户新增成功");	
    	}
    	return responseResult;
   	}
    
    /**
     * 方法名称: update
     * 方法描述: 修改系统用户信息
     * 返回类型: ResponseResult<String>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/update",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  update(User user){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	 /**将原始密码进行加密操作 **/
    	if(user.getUserPassword()!=null&&(!user.getUserPassword().equals(MD5Utils.getMD5Digest("")))){
    	   PasswordHelper passwordHelper = new PasswordHelper();
    	   passwordHelper.encryptPassword(user);
    	}else{
    	   user.setUserPassword(null);
    	}
    	int updateCount=0;
    	try{
    		 updateCount=userService.updateUser(user);
    		 responseResult.setUpdateCount(updateCount);
    		 responseResult.setInfo("用户更新成功!");
		} catch (Exception e) {
			responseResult.setInfo("用户更新失败!"+e); 
		}
    	return responseResult;
   	}
    
    /**
     * 方法名称: delete
     * 方法描述: 删除系统用户信息
     * 返回类型: ResponsePageListResult<List<User>>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  delete(Integer id){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	int result=userService.deleteUserById(id);
    	if(result>0){
    	   responseResult.setUpdateCount(result);
    	   responseResult.setInfo("操作成功!");	
    	}else{
		   responseResult.setInfo("操作失败!");	
    	}
    	return responseResult;
   	}
    
    
    /**
     * 方法名称: disUsercRole
     * 方法描述: 授权用户角色
     * 返回类型: ResponseResult<String>
     * 创建人：chens
     * 创建时间：2015年11月26日 下午1:48:57
     * @throws
     */
    @RequestMapping(value="/discRole",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  disUsercRole(Integer id,String   rIds){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
	    if(StringUtils.isEmpty(rIds)){
    	   responseResult.setUpdateCount(-1);
    	   responseResult.setErrorMessage("授权角色信息不能为空");	
    	}else{
    		/**将接收的请求数据进行处理 **/
    	   Integer [] roleArr=StringUtils.String2IntegerArray(rIds);
    	   try{
    		   userService.saveUserAndRoles(id,roleArr);
    		   responseResult.setInfo("授权成功!"); 
    	   } catch (Exception e) {
    		   responseResult.setUpdateCount(-1);
    		   responseResult.setErrorMessage("保存失败!"+e);
    	   }
    	}
    	return responseResult;
   	}
    
    /**
     * 方法名称: to_updatePwd
     * 方法描述: 更新密码
     * 返回类型: String
     * 创建人：chens
     * 创建时间：2015年12月4日 下午4:32:27
     * @throws
     */
    @RequestMapping(value="/to_updatePwd")
    public String  to_updatePwd(){
    	return "/system/user/updatePwd";
   	}
    
    /**
     * 方法名称: updatePwd
     * 方法描述: 更新用户密码
     * 返回类型: ResponseResult<String>
     * 创建人：chens
     * 创建时间：2015年12月4日 下午4:16:58
     * @throws
     */
    @RequestMapping(value="/updatePwd",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  updatePwd(String orgiPasswd,String newPassword){
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	User user=LoginManger.getLoginUser();
    	 /**将原始密码进行加密操作 **/
    	int updateCount=0;
    	if(StringUtils.isNotEmpty(newPassword)&&StringUtils.isNotEmpty(newPassword)){
    	   PasswordHelper passwordHelper = new PasswordHelper();
    	   String orgPwd=passwordHelper.getEncryptPassword(user.getCredentialsSalt(),user.getLoginCode(), orgiPasswd);
    	   if(StringUtils.isNotEmpty(orgPwd)&&orgPwd.equals(user.getUserPassword())){
    		  user.setUserPassword(newPassword); 
    		  passwordHelper.encryptPassword(user);   
    		  updateCount=userService.updateUser(user);
    		  responseResult.setUpdateCount(updateCount);
     		  responseResult.setInfo("用户更新成功!");
    	   }else{
    		  responseResult.setUpdateCount(updateCount);
      		  responseResult.setInfo("原密码输入有误!");  
    	   }
    	} 
    	return responseResult;
   	}
    
    
    
    
    
    //跳转到用户列表、新增用户页面
    @RequestMapping(value="/to_list")
    public String  to_add(){
    	return "/system/user/testUser";
   	}
    //跳转到角色列表
    @RequestMapping(value="/to_roleList")
    public String  to_roleList(String userid,Model model){
    	User user=userService.findUserById(Integer.valueOf(userid));
    	StringBuffer roleIds=new StringBuffer();
    	/**获取已授权的用户信息 **/
    	List<Role> roles=user.getRoles();
     	if(roles!=null){
    	  for(int i = 0; i < roles.size(); i++) {
    		  if((i+1)==roles.size()){
    			  roleIds.append(roles.get(i).getId()); 
    		  }else{
    			  roleIds.append(roles.get(i).getId()).append(",");
    		  }
		  }
    	}
    	model.addAttribute("userid",userid);
    	model.addAttribute("userRoles",roleIds.toString());
    	return "/system/user/listRoles";
   	}
}
