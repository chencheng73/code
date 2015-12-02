package com.zfpt.framework.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.zfpt.framework.filter.PageData;
import com.zfpt.framework.filter.Pager;
import com.zfpt.framework.util.StringUtils;

/**
 * 项目名称：zfpt   
 * 类名称：BaseController   
 * 类描述：   
 * 创建人：chens
 * 创建时间：2015年11月23日 上午11:41:18   
 * 修改备注：   
 * @version
 */
public class BaseController {
	
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}
	
	public Pager initPage(){
		Pager pager=new Pager();
		if(StringUtils.isNotEmpty(getRequest().getParameter("pageNo"))){
		   pager.setPageNo(Integer.parseInt(getRequest().getParameter("pageNo")));
		}
		if(StringUtils.isNotEmpty(getRequest().getParameter("pageSize"))){
		   pager.setPageSize(Integer.parseInt(getRequest().getParameter("pageSize")));
		}
		if(StringUtils.isNotEmpty(getRequest().getParameter("orderBy"))){
		   pager.setOrderBy(getRequest().getParameter("orderBy"));
		}
		if(StringUtils.isNotEmpty(getRequest().getParameter("sort"))){
		   pager.setSort(getRequest().getParameter("sort"));
		}
		PageData pd=this.getPageData();
		if(pd!=null){
		   pager.setPageData(pd);	
		}
		return pager;
	}
	
	
}
