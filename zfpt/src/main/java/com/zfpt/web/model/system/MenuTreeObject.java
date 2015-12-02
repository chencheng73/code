package com.zfpt.web.model.system;

import java.io.Serializable;
/**
 * 项目名称：zfpt   
 * 类名称：MenuTreeObject   
 * 类描述：封装树形结构tree   
 * 创建人：chens
 * 创建时间：2015年11月30日 下午2:09:48   
 * 修改备注：   
 * @version
 */
public class MenuTreeObject implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private Integer pId;
	
	private Boolean open;
	
	private Boolean checked;
	
	private String href;

	private Boolean  click;
	
	
	public Boolean getClick() {
		return click;
	}

	public void setClick(Boolean click) {
		this.click = click;
	}


	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	
}
