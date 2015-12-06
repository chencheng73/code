package com.zfpt.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.zfpt.framework.context.LoginManger;
import com.zfpt.web.common.AppCons;
import com.zfpt.web.model.system.User;
/**
 * 项目名称：zfpt   
 * 类名称：LoginHandlerInterceptor   
 * 类描述： 自定义权限拦截器  
 * 创建人：chens
 * 创建时间：2015年12月4日 上午9:24:08   
 * 修改备注：   
 * @version
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	private   Set<String>  ignore_uris;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean flag = false;
		/** 获取当前登录用户信息 **/
		User user = LoginManger.getLoginUser();
		String requestUrl = request.getRequestURI();
		/**防止url含有// **/
		requestUrl = requestUrl.replace("//", "/");
		if (user != null) {
			/** 1.不处理的url **/
			for (String url : ignore_uris) {
				if (requestUrl.contains(url) || requestUrl.indexOf(url) > 0) {
					return true;
				}
			}

			/** 2.判斷url是否为静态资源或不在资源范围内 **/
			Set<String> systemMenuSet = AppCons.menuSet;
			for (String url : systemMenuSet) {
				String _tempUrl = AppCons.contextPath + url;
				if (requestUrl.equals(_tempUrl)) {
					flag = true;
					break;
				}
			}
			/** 静态资源直接通过，否则将进行权限判断 **/
			if (!flag) {
				return true;
			} else {
				flag = false;/** 重置状态 **/
			}
			
			
			/** 3.判斷url是否为用户所属授权菜单 **/
			Set<String> menuTreeObjects = LoginManger.getResource();
			for (String resUrl : menuTreeObjects) {
				String _tempUrl = AppCons.contextPath + resUrl;
				if (requestUrl.equals(_tempUrl)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				this.print(response, "<script>alert('抱歉,该资源您无权限访问,请联系系统管理员!');" + "window.location.href='"
						+ AppCons.contextPath + "/index.html'</script>");
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 方法名称: print 
	 * 方法描述: 输出数据 
	 * 返回类型: void 
	 * 创建人：chens 
	 * 创建时间：2015年12月4日
	 * 上午9:44:49 @throws
	 */
	protected void print(HttpServletResponse response, String htmlInfo) {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(htmlInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	public void setIgnore_uris(Set<String> ignore_uris) {
		this.ignore_uris = ignore_uris;
	}
	
	
}
