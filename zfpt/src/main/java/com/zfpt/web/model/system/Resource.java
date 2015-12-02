package com.zfpt.web.model.system;

import com.zfpt.framework.model.BaseObject;

/**
 * 项目名称：zfpt   
 * 类名称：Resource   
 * 类描述：系统资源表(菜单)  
 * 创建人：chens
 * 创建时间：2015年11月26日 上午10:31:29   
 * 修改备注：   
 * @version
 */
public class Resource extends BaseObject {

	private static final long serialVersionUID = 1L;
	/**资源标识 **/
	private String resKey;
	/**资源名称**/
	private String resName;
	/**访问url,如:/system/user/listPage **/
	private String resUrl;
	/**资源类型：功能/按钮 **/
	private String type;
	/**父类id **/
	private Integer parentId;
	/**描述 **/
	private String description;
	/**级别 **/
	private Integer level;
	/**排序 **/
	private Integer resOrder;
	
	public Integer getResOrder() {
		return resOrder;
	}
	public void setResOrder(Integer resOrder) {
		this.resOrder = resOrder;
	}
	public String getResKey() {
		return resKey;
	}
	public void setResKey(String resKey) {
		this.resKey = resKey;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResUrl() {
		return resUrl;
	}
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
