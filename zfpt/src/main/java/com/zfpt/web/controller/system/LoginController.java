package com.zfpt.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zfpt.framework.context.LoginManger;
import com.zfpt.framework.util.JsonUtils;
import com.zfpt.framework.util.StringUtils;
import com.zfpt.web.model.system.MenuTreeObject;
import com.zfpt.web.model.system.Resource;
import com.zfpt.web.model.system.Role;
import com.zfpt.web.model.system.User;
import com.zfpt.web.service.system.IRoleService;
import com.zfpt.web.service.system.IUserService;
/**      
 * 项目名称：zfpt   
 * 类名称：LoginController   
 * 类描述： 系统登录控制器  
 * 创建人：chens
 * 创建时间：2015年11月23日 上午11:43:21   
 * 修改备注：   
 * @version      
 */
@RestController
@RequestMapping(value="/login")
public class LoginController{
	private Log logger=LogFactory.getLog(LogFactory.class);
	
	@Autowired
    private IUserService userService;
	@Autowired
	private IRoleService roleService;
    
    /**
     * 方法名称: init_login
     * 方法描述: 初始化登录界面
     * 返回类型: String
     * 创建人：chens
     * 创建时间：2015年11月23日 下午5:40:57
     * @throws
     */
    @RequestMapping(value="/index")
	public String init_login(){
    	logger.debug("--进行登录页面 --");
		return "login";
	}
    
    /**
     * 方法名称: login
     * 方法描述: 用户安全登录
     * 返回类型: Map<String,Object>
     * 创建人：chens
     * 创建时间：2015年11月23日 下午5:40:35
     * @throws
     */
    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ResponseBody
    public Object  login(User user, HttpServletRequest request) throws UnknownAccountException {
    	Map<String,Object> returnInfo=new HashMap<String,Object>();
    	returnInfo.put("status",0);/**登录成功状态**/
		try{
			if (!request.getMethod().equals("POST")) {
				returnInfo.put("status",-1);
				returnInfo.put("info","支持POST方法提交！");
			}
			if (StringUtils.isEmpty(user.getLoginCode()) || StringUtils.isEmpty(user.getUserPassword())) {
				returnInfo.put("status",-2);
				returnInfo.put("info","用户名或密码不能为空！");
			}
			Subject subject = SecurityUtils.getSubject();
			/** 用户输入的账号和密码,,存到UsernamePasswordToken对象中..然后由shiro内部认证对比,
			   认证执行者交由ShiroDbRealm中doGetAuthenticationInfo处理,当以上认证成功后会向下执行,认证失败会抛出异常 **/
			UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginCode(), user.getUserPassword());
			try{
				subject.login(token);
			} catch (LockedAccountException lae) {
				token.clear();
				returnInfo.put("status",1);
				returnInfo.put("info","用户已经被锁定不能登录，请与管理员联系！");
			} catch (ExcessiveAttemptsException e) {
				token.clear();
				returnInfo.put("status",2);
				returnInfo.put("info","账号：" + user.getLoginCode() + " 登录失败次数过多,锁定10分钟！");
			} catch (AuthenticationException e) {
				token.clear();
				returnInfo.put("status",3);
				returnInfo.put("info","用户或密码不正确！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.put("status",4);
			returnInfo.put("info","登录异常，请联系管理员！");
		}
		return JsonUtils.toJSON(returnInfo);
	}
    
    
    /**
     * 方法名称: main
     * 方法描述: 登录成功，跳转到主页
     * 返回类型: String
     * 创建人：chens
     * 创建时间：2015年12月3日 上午10:55:48
     * @throws
     */
    @RequestMapping(value="/main")
   	public String main(Model model){
    	logger.info(LoginManger.getLoginName());
    	logger.debug("-- 加载菜单信息 --");
    	Integer uid=LoginManger.getLoginId();
    	/**角色列表 **/
    	List<Role> roles=userService.findRolesByUserId(uid);
    	Set<MenuTreeObject> treeObjects=new HashSet<MenuTreeObject>();
    	if(roles!=null&&roles.size()>0){
    	   for(Role role : roles){
    		   /**资源信息 **/
	 		   List<Resource> resources=roleService.findReourceByRoleId(role.getId());
    		   MenuTreeObject meTreeObject=null;
	 		   for(Resource resource : resources) {
				   meTreeObject=new MenuTreeObject();
				   meTreeObject.setHref(resource.getResUrl());
				   meTreeObject.setId(resource.getId());
				   meTreeObject.setName(resource.getResName());
				   meTreeObject.setpId(resource.getParentId());
    		       treeObjects.add(meTreeObject);
	 		   }
		   }	
    	}
    	model.addAttribute("userInfo",LoginManger.getUserName());
    	model.addAttribute("menus",treeObjects);
    	System.out.println("资源列表:"+treeObjects);
   		return "/system/homepage/main";
   	}
	
    /**
     * 方法名称: logout
     * 方法描述: 用户注销
     * 返回类型: String
     * 创建人：chens
     * 创建时间：2015年12月3日 上午11:45:13
     * @throws
     */
    @RequestMapping(value = "logout")
	public String logout() {
		SecurityUtils.getSubject().logout();  
		return "redirect:login";
	}

}
