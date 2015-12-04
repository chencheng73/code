package com.zfpt.web.filter;

<<<<<<< HEAD
import java.io.IOException;
=======
>>>>>>> f59d01742d927199f244f8570e425f92f5f8b5d7
import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.zfpt.framework.context.LoginManger;
import com.zfpt.web.common.AppCons;
import com.zfpt.web.model.system.User;
/**
<<<<<<< HEAD
 * 项目名称：zfpt   
 * 类名称：LoginHandlerInterceptor   
 * 类描述： 自定义权限拦截器  
 * 创建人：chens
 * 创建时间：2015年12月4日 上午9:24:08   
 * 修改备注：   
 * @version
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{
	
	private static final String[] IGNORE_URI = {"/login","/init"};
=======
 * @author admin
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{
	
	private static final String[] IGNORE_URI = {"/login/login","/login/index","/login/main"};
>>>>>>> f59d01742d927199f244f8570e425f92f5f8b5d7
	
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	        boolean flag = true;
	        /**获取当前登录用户信息 **/
	        User user=LoginManger.getLoginUser();
	        String requestUrl=request.getRequestURI();
	        if(user!=null){
        	   flag=false;
        	   
        	   /**1.不处理的url **/
        	  for(String url:IGNORE_URI){
    			  if(requestUrl.contains(url)||requestUrl.indexOf(url)>0){
    				 return true;
    			  }
    		  } 	
        	  
        	  /**2.判斷url是否为静态资源或不在资源范围内 **/
        	  Set<String> systemMenuSet=AppCons.menuSet;
        	  for(String url : systemMenuSet) {
        		  String _tempUrl=AppCons.contextPath+url;
 				  if(requestUrl.equals(_tempUrl)){
 				     flag=true;
 				     break;
 				  }
			   }
        	   if(!flag){
        		  return true; 
        	   }
        	   /**3.判斷url是否为用户所属授权菜单 **/
        	   Set<String> menuTreeObjects=LoginManger.getResource();
        	   flag=false;
 	           for(String resUrl : menuTreeObjects) {
 	        	  String _tempUrl=AppCons.contextPath+resUrl;
 				  if(requestUrl.equals(_tempUrl)){
 				     flag=true;
 				     break;
 				  }
 			  }
        	  if(!flag){
<<<<<<< HEAD
        		  this.print(response, "<script>alert('抱歉,该资源您无权限访问,请联系系统管理员!');"
        		  		+ "window.location.href='"+AppCons.contextPath+"/index.html'</script>");
=======
        		  PrintWriter out = response.getWriter();
        		  out.print("<script>alert('非法请求URL!')</script>");
        		  out.flush();
        		  out.close();
>>>>>>> f59d01742d927199f244f8570e425f92f5f8b5d7
        		  return false;
        	  }
	        }
	        return flag;
	    }
	 
	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	        super.postHandle(request, response, handler, modelAndView);
	    }
	 
<<<<<<< HEAD
	    /**
	     * 方法名称: print
	     * 方法描述: 输出数据
	     * 返回类型: void
	     * 创建人：chens
	     * 创建时间：2015年12月4日 上午9:44:49
	     * @throws
	     */
	    protected void print(HttpServletResponse response,String htmlInfo) {
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
=======
>>>>>>> f59d01742d927199f244f8570e425f92f5f8b5d7
}
