package com.zfpt.web.filter;

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
 * @author admin
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{
	
	private static final String[] IGNORE_URI = {"/login/login","/login/index","/login/main"};
	
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
        		  PrintWriter out = response.getWriter();
        		  out.print("<script>alert('非法请求URL!')</script>");
        		  out.flush();
        		  out.close();
        		  return false;
        	  }
	        }
	        return flag;
	    }
	 
	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	        super.postHandle(request, response, handler, modelAndView);
	    }
	 
}
