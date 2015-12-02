package com.zfpt.web.security;

/**
 * 权限标识配置�? <br>
 * �?permission 权限�?中的 permission_sign 字段 相对�?<br>
 * 使用:
 * 
 * <pre>
 * &#064;RequiresPermissions(value = PermissionConfig.USER_CREATE)
 * public String create() {
 *     return &quot;拥有user:create权限,能访�?quot;;
 * }
 * </pre>
 * 
 * @author StarZou
 * @since 2014�?�?7�?下午3:58:51
 **/
public class PermissionSign {

    /**
     * 用户新增权限 标识
     */
    public static final String USER_CREATE = "user:create";

    /**
     * 用户删除权限 标识
     */
    public static final String USER_DELETE = "user:delete";

    /**
     * 添加更多...
     */

}
