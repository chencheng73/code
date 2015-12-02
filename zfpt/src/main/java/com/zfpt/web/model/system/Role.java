package com.zfpt.web.model.system;

import java.util.List;

import com.zfpt.framework.model.BaseObject;

/**      
 * 项目名称：zfpt   
 * 类名称：  Role   
 * 类描述： 系统角色实体类
 * 创建人：chens
 * 创建时间：2015年11月23日 上午11:45:34   
 * 修改备注：   
 * @version      
 */

public class Role extends BaseObject {
 
	private static final long serialVersionUID = 1L;
	/**名称 **/
    private String roleName;
    /**编号**/
    private String roleCode;
    /**职责描述 **/
    private String description;
    /**角色对应的菜单列表 **/
    private List<Resource> resources;
    
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
  
    
}
