package com.zfpt.framework.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.zfpt.web.common.AppCons;
/**
 * 项目名称：zfpt   
 * 类名称：WebAppContextListener   
 * 类描述：Servlet监听   
 * 创建人：chens
 * 创建时间：2015年11月24日 下午3:54:16   
 * 修改备注：   
 * @version
 */
public class WebAppContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
	}

	public void contextInitialized(ServletContextEvent event) {
		AppCons.system_path=event.getServletContext().getRealPath("/");
		AppCons.contextPath=event.getServletContext().getContextPath();
	}

}
