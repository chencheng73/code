package com.zfpt.web.common;


import com.zfpt.framework.filter.Pager;
/**
 * 项目名称：zfpt   
 * 类名称：ResponsePageListResult   
 * 类描述： 响应分页列表数据  
 * 创建人：chens
 * 创建时间：2015年11月24日 上午10:07:32   
 * 修改备注：   
 * @version
 */
public class ResponsePageListResult<T> extends ResponseResult<T> {
    
	private static final long serialVersionUID = 1L;
	/**当前页码 **/
	private int currentNo;
	/**显示数量 **/
	private int pageSize;
	/**总记录数 **/
	private int totalCount;
	/**总页数 **/
	private int totalPages;
	
	public int getCurrentNo() {
		return currentNo;
	}
	public void setCurrentNo(int currentNo) {
		this.currentNo = currentNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public void put(Pager<T> page){
	    setData(page.getResult());
	    this.currentNo=page.getPageNo();
	    this.pageSize=page.getPageSize();
	    this.totalCount=page.getTotalCount();
	    this.totalPages=page.getTotalPages();
	}
	
	public void put(Pager<T> page,String errorMsg){
	    setData(page.getResult());
	    this.currentNo=page.getPageNo();
	    this.pageSize=page.getPageSize();
	    this.totalCount=page.getTotalCount();
	    this.totalPages=page.getTotalPages();
	    setErrorMessage(errorMsg);
	}
}
