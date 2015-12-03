package com.zfpt.web.model.system;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工实体类
 * @author lzj
 * 2015-上午11:14:34
 */
public class Employee implements Serializable{
	private static final long serialVersionUID = 7219439563350875039L;
	private String name ;
	private String email ;
	private String tele;
	private String address ;
	
	//预留字段 1 2
	private String memo1;
	private String memo2;
	
	
	private Long empployeeId;//员工工号
	private Long deptId;//部门编号
	private Long  birthday;
	private Long  hireDate;
	private Long  leavingDate;
	
	
	private Integer gender ;
	private Integer isDelete ;//是否有效：0无效，1有效，默认为1
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	public String getMemo2() {
		return memo2;
	}
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	public Long getEmpployeeId() {
		return empployeeId;
	}
	public void setEmpployeeId(Long empployeeId) {
		this.empployeeId = empployeeId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	public Long getHireDate() {
		return hireDate;
	}
	public void setHireDate(Long hireDate) {
		this.hireDate = hireDate;
	}
	public Long getLeavingDate() {
		return leavingDate;
	}
	public void setLeavingDate(Long leavingDate) {
		this.leavingDate = leavingDate;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", email=" + email + ", tele=" + tele
				+ ", address=" + address + ", memo1=" + memo1 + ", memo2="
				+ memo2 + ", empployeeId=" + empployeeId + ", deptId=" + deptId
				+ ", birthday=" + birthday + ", hireDate=" + hireDate
				+ ", leavingDate=" + leavingDate + ", gender=" + gender
				+ ", isDelete=" + isDelete + "]";
	}
	
}
