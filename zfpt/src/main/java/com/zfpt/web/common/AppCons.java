package com.zfpt.web.common;

import java.util.Set;

/**      
 * 项目名称：zfpt   
 * 类名称：AppCons   
 * 类描述：系统常量类   
 * 创建人：chens
 * 创建时间：2015年11月23日 上午11:40:44   
 * 修改备注：   
 * @version      
 */

public class AppCons {
	/**上下文路径 **/
	public static String contextPath;
	/**系统部署目录**/
	public static String system_path;
    /**附件上传根目录(后期可考虑数据字典) **/
	public final static String file_root="d:/zfpt_root";
	/**文档上传根目录 **/
	public final static String file_upload_path=file_root+"/upload/";
	/**不同的业务模块存储在不同的路径code,默认common,同时便于用于数据库区分 **/
	public final static String file_mod_code="common";
	/**存储系统菜单 **/
	public static Set<String> menuSet;
	
}
