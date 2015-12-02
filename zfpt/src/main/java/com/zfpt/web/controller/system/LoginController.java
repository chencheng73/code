package com.zfpt.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@Controller
@RequestMapping(value="/login")
public class LoginController{
	@Autowired
    private IUserService userService;
    
    /**
     * 方法名称: init_login
     * 方法描述: 初始化登录界面
     * 返回类型: String
     * 创建人：chens
     * 创建时间：2015年11月23日 下午5:40:57
     * @throws
     */
    @RequestMapping(value="/init",method=RequestMethod.GET)
	public String init_login(){
		return "index";
	}
    
    /**
     * 方法名称: login
     * 方法描述: 用户安全登录
     * 返回类型: String
     * 创建人：chens
     * 创建时间：2015年11月23日 下午5:40:35
     * @throws
     */
    @RequestMapping(value="/login",method=RequestMethod.POST)
   	public String login(){
   		return "login";
   	}
	
}
