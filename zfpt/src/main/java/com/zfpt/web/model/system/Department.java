package com.zfpt.web.model.system;

import com.zfpt.framework.model.BaseObject;
/**
 * 项目名称：zfpt   
 * 类名称：Department   
 * 类描述： 部门信息实体类   
 * 创建人：chens
 * 创建时间：2015年12月1日 下午5:18:06   
 * 修改备注：   
 * @version
 */
public class Department extends BaseObject {
 
	private static final long serialVersionUID = 1L;
	/**部门名称 **/
	private String name;
	/**上级id **/
	private Integer parentId;
	/**部门描述 **/
	private String description;
	/**级别 **/
	private Integer level;
	/**部门编码 **/
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	

}
