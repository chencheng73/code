package com.zfpt.web.security;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfpt.web.model.system.Resource;
import com.zfpt.web.model.system.Role;
import com.zfpt.web.model.system.User;
import com.zfpt.web.service.system.IRoleService;
import com.zfpt.web.service.system.IUserService;
/**
 * 项目名称：zfpt   
 * 类名称：SecurityRealm   
 * 类描述：自定义Realm,进行数据源配置   
 * 创建人：chens
 * 创建时间：2015年12月3日 上午11:10:09   
 * 修改备注：   
 * @version
 */
public class SecurityRealm extends AuthorizingRealm {
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;

	/**
	 * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.在配有缓存的情况下，只加载一次.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
	    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		String loginName = SecurityUtils.getSubject().getPrincipal().toString();
		if (loginName != null) {
			String userId = SecurityUtils.getSubject().getSession().getAttribute("userSessionId").toString();
			final List<Role> roleInfos = userService.findRolesByUserId(Integer.valueOf(userId));
			if(roleInfos!=null&&roleInfos.size()>0){
			   for(Role role : roleInfos) {
				   List<Resource> resources=roleService.findReourceByRoleId(role.getId());
				   authorizationInfo.addRole(role.getRoleName());
				   for(Resource resource:resources){
					   authorizationInfo.addStringPermission(resource.getResUrl()+"");
				   }
			   }	
			}
		}
		return authorizationInfo;
	}

	/**
	 * 认证回调函数,登录时调用，首先根据传入的用户名获取User信息，分几种情况:
	 * 1.如果user为空，那么抛出没找到帐号异常UnknownAccountException；
	 * 2.如果user找到但锁定了抛出锁定异常LockedAccountException；最后生成AuthenticationInfo信息，
	 * 3.交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配，
	 * 4.如果不匹配将抛出密码错误异常IncorrectCredentialsException；
	 * 5.另外如果密码重试此处太多将抛出超出重试次数异常ExcessiveAttemptsException；
	 * 在组装SimpleAuthenticationInfo信息时， 需要传入：身份信息（用户名）、凭据（密文密码）、盐（username+salt），
	 * CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		User user=userService.loginUser(username);
		if(user!=null){
			if ("2".equals(user.getStatus())) {
				throw new LockedAccountException(); //帐号锁定
			}
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,user.getUserPassword(), // 密码
			ByteSource.Util.bytes(username + "" + user.getCredentialsSalt()),getName());
			// 当验证都通过后，把用户信息放在session里
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute("userSession", user);
			return authenticationInfo;   
		}else{
			throw new UnknownAccountException();// 没找到帐号
		}
	}
	
	/**
     * 更新用户授权信息缓存.
     */
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}
	
	/**
     * 更新用户信息缓存.
     */
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	/**
	 * 清除用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	/**
	 * 清除用户信息缓存.
	 */
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}
	
	/**
	 * 清空所有缓存
	 */
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}


	/**
	 * 清空所有认证缓存
	 */
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}