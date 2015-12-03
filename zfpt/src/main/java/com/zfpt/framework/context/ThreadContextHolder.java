package com.zfpt.framework.context;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.zfpt.web.model.system.MenuTreeObject;

/**
 * 项目名称：zfpt   
 * 类名称：ThreadContextHolder   
 * 类描述： 用ThreadLocal来存储Session,以便实现Session any where   
 * 创建人：chens
 * 创建时间：2015年11月23日 下午5:03:23   
 * 修改备注：   
 * @version
 */
public class ThreadContextHolder  {
	protected static final Logger logger = Logger.getLogger(ThreadContextHolder.class);
	private static ThreadLocal<HttpServletRequest> HttpRequestThreadLocalHolder = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> HttpResponseThreadLocalHolder = new ThreadLocal<HttpServletResponse>();
	private static ThreadLocal<Set<String>> ResourceThreadLocalHolder = new ThreadLocal<Set<String>>();
	
	public static void setHttpRequest(HttpServletRequest request){
		HttpRequestThreadLocalHolder.set(request);
	}
	
	public static HttpServletRequest getHttpRequest(){
		return  HttpRequestThreadLocalHolder.get();
	}
	
	public static void setHttpResponse(HttpServletResponse response){
		HttpResponseThreadLocalHolder.set(response);	
	}
	
	public static HttpServletResponse getHttpResponse(){
		
		return HttpResponseThreadLocalHolder.get();
	}
	
	public static void setResourceContext(Set<String> menuTreeObjects){
		ResourceThreadLocalHolder.set(menuTreeObjects);
	}
	
	public static Set<String> getResourceContext(){
		return ResourceThreadLocalHolder.get();
	}
	
	
	
	
	
	
}
