package com.zfpt.framework.util;

import java.util.UUID;

/**
 * 项目名称：sg_purchase 
 * 类名称：UUIDUtils 
 * 类描述： UUID工具类 
 * 创建人：chens 
 * 创建时间：2013-9-17
 * 下午10:00:58 修改备注：
 * @version
 */
public class UUIDUtils {
    
	public static String getUUID(){
		
		return UUID.randomUUID().toString();
	}
	
	public static String getUUID2String() {
		StringBuffer uuIdBuffer = new StringBuffer();
		String _uuid = getUUID();
		uuIdBuffer.append(_uuid.substring(0, 8));
		uuIdBuffer.append(_uuid.substring(9, 13));
		uuIdBuffer.append(_uuid.substring(14, 18));
		uuIdBuffer.append(_uuid.substring(19, 23));
		uuIdBuffer.append(_uuid.substring(24));
		return uuIdBuffer.toString();
	}

}
