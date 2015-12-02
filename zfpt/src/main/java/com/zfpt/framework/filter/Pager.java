package com.zfpt.framework.filter;

import net.sf.cglib.core.EmitUtils;

/**
 * 项目名称：zfpt   
 * 类名称：Page   
 * 类描述： Mybatis分页参数及查询结果封装 
 * 创建人：chens
 * 创建时间：2015年11月23日 下午2:27:03   
 * 修改备注：   
 * @version
 */
public class Pager<T>{
    /**
     * 页编号 : 第几页
     */
    protected int pageNo = 1;
    /**
     * 页大小 : 每页的数量
     */
    protected int pageSize = 15;
    /**
     * 偏移量 : 第一条数据在表中的位置
     */
    protected int offset;
    /**
     * 限定数 : 每页的数量
     */
    protected int limit;
    
    /**封装排序条件 **/
    protected String orderBy;
    
    /**默认倒序 **/
    private String sort="desc";
    
    /**
     * 查询结果
     */
    protected T result;
    
    /**
     * 封装查询条件
     */
    private PageData pageData=new PageData();

    /**
     * 总条数
     */
    protected int totalCount;

    /**
     * 总页数
     */
    protected int totalPages;

    /**
     * 计算偏移量
     */
    private void calcOffset() {
        this.offset = ((pageNo - 1) * pageSize);
    }

    /**
     * 计算限定数
     */
    private void calcLimit() {
        this.limit = pageSize;
    }

    // -- 构造函数 --//
    public Pager() {
        this.calcOffset();
        this.calcLimit();
    }

    public Pager(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.calcOffset();
        this.calcLimit();
    }

    // -- 访问查询参数函数 --//
    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 获得每页的记录数量,默认为1.
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
     */
    public int getOffset() {
        return ((pageNo - 1) * pageSize);
    }

    public int getLimit() {
        return limit;
    }

    public void setOffset(int offset) {
		this.offset = offset;
	}

	// -- 访问查询结果函数 --//
    /**
     * 取得页内的记录列表.
     */
    public T getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setResult(final T result) {
        this.result = result;
    }

    /**
     * 取得总记录数, 默认值为-1.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final int totalCount) {
        this.totalCount = totalCount;
        this.totalPages = this.getTotalPages();
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public int getTotalPages() {
        if (totalCount < 0) {
            return -1;
        }
        int pages = totalCount / pageSize;
        return totalCount % pageSize > 0 ? ++pages : pages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

	public PageData getPageData() {
		return pageData;
	}

	public void setPageData(PageData pageData) {
		this.pageData = pageData;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
    
}
