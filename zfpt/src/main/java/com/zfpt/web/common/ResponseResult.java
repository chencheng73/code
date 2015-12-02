package com.zfpt.web.common;

import java.io.Serializable;

/**
 * 项目名称：zfpt   
 * 类名称：ResponseResult   
 * 类描述：响应客户端   
 * 创建人：chens
 * 创建时间：2015年11月24日 上午9:44:17   
 * 修改备注：   
 * @version
 */
public class ResponseResult<T> implements  Serializable{

	private static final long serialVersionUID = 1L;
    /**默认为0，小于0,标记为失败 **/
	private int updateCount=0;
	
	private String info;
	
	private String errorMessage;
	
	private T data;

	public int getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	
 
	
}
