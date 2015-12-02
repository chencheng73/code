package com.zfpt.web.security;

/**
 * 角色标识配置�? <br>
 * �?role_info 角色表中�?role_sign 字段 相对�?<br>
 * 使用:
 * 
 * <pre>
 * &#064;RequiresRoles(value = RoleSign.ADMIN)
 * public String admin() {
 *     return &quot;拥有admin角色,能访�?quot;;
 * }
 * </pre>
 * 
 * @author StarZou
 * @since 2014�?�?7�?下午3:58:51
 **/
public class RoleSign {

    /**
     * 普�?后台管理�?标识
     */
    public static final String ADMIN = "admin";

    /**
     * 客户经理 标识
     */
    public static final String CONSULTANT = "consultant";

    /**
     * VIP客户 标识
     */
    public static final String VIP_USER = "vip_user";

    /**
     * 商家 标识
     */
    public static final String MERCHANT = "merchant";

    /**
     * 添加更多...
     */

}
