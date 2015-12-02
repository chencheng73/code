package com.zfpt.framework.model;

import java.io.Serializable;

/**      
 * 项目名称：zfpt   
 * 类名称：BaseModel   
 * 类描述： 实体类基类 
 * 创建人：chens
 * 创建时间：2015年11月23日 上午11:56:09   
 * 修改备注：   
 * @version      
 */

public class BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;
    /**主键 **/
	private Integer id;
	/**创建人　**/
	private String createdby;
	/**创建时间 **/
	private String createAt;
	/**状态**/
	private Integer status;
    /**是否删除 **/
	private Integer isDelete;
	
	
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createAt == null) ? 0 : createAt.hashCode());
		result = prime * result + ((createdby == null) ? 0 : createdby.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseObject other = (BaseObject) obj;
		if (createAt == null) {
			if (other.createAt != null)
				return false;
		} else if (!createAt.equals(other.createAt))
			return false;
		if (createdby == null) {
			if (other.createdby != null)
				return false;
		} else if (!createdby.equals(other.createdby))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	 
	

	 
	
	
}
