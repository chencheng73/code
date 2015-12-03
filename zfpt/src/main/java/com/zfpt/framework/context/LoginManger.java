package com.zfpt.framework.context;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import com.zfpt.web.model.system.User;
/**
 * 项目名称：zfpt   
 * 类名称：LoginManger   
 * 类描述： 获取用户登录信息 
 * 创建人：chens
 * 创建时间：2015年12月3日 下午3:45:14   
 * 修改备注：   
 * @version
 */
public class LoginManger {
	
	private static String userSessionKey="userSession";
	
	protected static Session getSeesion(){
		return SecurityUtils.getSubject().getSession();
	}
	
	/**
	 * 方法名称: getLoginUser
	 * 方法描述: 返回登录用户信息
	 * 返回类型: User
	 * 创建人：chens
	 * 创建时间：2015年12月3日 下午3:42:05
	 * @throws
	 */
	public static User getLoginUser(){
		Session session=getSeesion();
		User user=null;
		if(session!=null){
		   user=(User) session.getAttribute(userSessionKey);	
		}
		return user;
	}
	
	
	
	/**
	 * 方法名称: getLoginName
	 * 方法描述: 返回登录名
	 * 返回类型: String
	 * 创建人：chens
	 * 创建时间：2015年12月3日 下午3:43:32
	 * @throws
	 */
	public static String getLoginName(){
		User user=getLoginUser();
		return user==null?null:user.getLoginCode();
	}
	
	/**
	 * 方法名称: getUserName
	 * 方法描述: 返回用户名
	 * 返回类型: String
	 * 创建人：chens
	 * 创建时间：2015年12月3日 下午3:43:32
	 * @throws
	 */
	public static String getUserName(){
		User user=getLoginUser();
		return user==null?null:user.getUserName();
	}
	
	/**
	 * 方法名称: getLoginId
	 * 方法描述: 返回用户id
	 * 返回类型: String
	 * 创建人：chens
	 * 创建时间：2015年12月3日 下午3:43:32
	 * @throws
	 */
	public static Integer getLoginId(){
		User user=getLoginUser();
		return user==null?null:user.getId();
	}
	
	/**
	 * 方法名称: getDepartmentId
	 * 方法描述: 获取当前登录的部门id
	 * 返回类型: String
	 * 创建人：chens
	 * 创建时间：2015年12月3日 下午5:21:57
	 * @throws
	 */
	public static String getDepartmentId(){
		User user=getLoginUser();
		return user==null?null:user.getDeptmentId();
	}
}
