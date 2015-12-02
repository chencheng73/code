package com.zfpt.web.model.system;

import java.util.List;

import com.zfpt.framework.model.BaseObject;

/**
 * 项目名称：zfpt   
 * 类名称：User   
 * 类描述： 系统用户实体类 
 * 创建人：chens
 * 创建时间：2015年11月23日 下午3:46:16   
 * 修改备注：   
 * @version
 */
public class User extends BaseObject {

	private static final long serialVersionUID = 1L;
	/**用户姓名 **/
	private String userName;
	/**登录账号 **/
    private String loginCode;
	/**登录密码 **/
	private String userPassword;
	/**加密盐值 **/
	private String credentialsSalt;
	/**电子邮箱 **/
	private String email;
	/**所属部门 **/
	private String deptmentId;
	/**手机号码 **/
	private String mobile;
	/**电话号码 **/
	private String phone;
	/**备注 **/
	private String remark;
	/**系统角色 **/
    private List<Role> roles;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getCredentialsSalt() {
		return credentialsSalt;
	}

	public void setCredentialsSalt(String credentialsSalt) {
		this.credentialsSalt = credentialsSalt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeptmentId() {
		return deptmentId;
	}

	public void setDeptmentId(String deptmentId) {
		this.deptmentId = deptmentId;
	}
   
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
    

}
