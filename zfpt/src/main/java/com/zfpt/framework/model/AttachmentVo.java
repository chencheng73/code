package com.zfpt.framework.model;

/**
 * 项目名称：PPSP   
 * 类名称：AttachmentVo   
 * 类描述：附件上传实体类
 * 创建人：chens
 * 创建时间：2014-8-15 上午10:22:41   
 * 修改备注：   
 * @version
 */
public class AttachmentVo extends BaseObject {

	private static final long serialVersionUID = 1502253372665610337L;
	/**主键 **/    
	private String pkId;
	/*** 业务ID*/
	private String biz_id ;
	/*** 源文件名 */
	private String original_name ;
	/*** 模块名称[区分业务类型]*/
	private String mod_code ; 
	/*** 类型*/
	private String file_type ;
	/**** 文件保存路径 */
	private String path ;
	/*** 文件描述*/
	private String description;

	
	
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getBiz_id() {
		return biz_id;
	}
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}
	 
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

	 
}
