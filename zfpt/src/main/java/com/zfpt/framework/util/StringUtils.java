package com.zfpt.framework.util;

import java.math.BigDecimal;

/**
 * 项目名称：ppsp_eonline   
 * 类名称：StringUtils   
 * 类描述：字符串工具类   
 * 创建人：chens
 * 创建时间：2014-9-10 下午3:08:59   
 * 修改备注：   
 * @version
 */
public abstract class StringUtils {
	private static final String CHARSET_UTF8 = "UTF-8";
	private StringUtils() {
	}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>StringUtils.isEmpty(null) = true</li>
	 * <li>StringUtils.isEmpty("") = true</li>
	 * <li>StringUtils.isEmpty("   ") = true</li>
	 * <li>StringUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value
	 *            待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * 检查对象是否为数字型字符串,包含负数开头的。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		char[] chars = obj.toString().toCharArray();
		int length = chars.length;
		if (length < 1)
			return false;

		int i = 0;
		if (length > 1 && chars[0] == '-')
			i = 1;

		for (; i < length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	public static String toUnderlineStyle(String name) {
		StringBuilder newName = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i > 0) {
					newName.append("_");
				}
				newName.append(Character.toLowerCase(c));
			} else {
				newName.append(c);
			}
		}
		return newName.toString();
	}

	public static String convertString(byte[] data, int offset, int length) {
		if (data == null) {
			return null;
		} else {
			try {
				return new String(data, offset, length, CHARSET_UTF8);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static byte[] convertBytes(String data) {
		if (data == null) {
			return null;
		} else {
			try {
				return data.getBytes(CHARSET_UTF8);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
    public static BigDecimal convetrBigDecima2String(String str,int chat){
    	return StringUtils.isNotEmpty(str)?new BigDecimal(str).setScale(chat):null;
    }
	

    /**
     * 方法名称: Array2String 
     * 方法描述: 数组转换String   
     * 返回类型: String 
     * @throws
     */
    public static String Array2String(String [] array){
    	StringBuffer stringBuffer=new StringBuffer();
    	for(int i = 0; i < array.length; i++) {
    		if(i+1==array.length){
    		   stringBuffer.append(array[i]);
    		}else{
    		   stringBuffer.append(array[i]+",");
    		}
		}
    	return stringBuffer.toString();
    }
	
    public static Integer [] String2IntegerArray(String string){
    	if(isNotEmpty(string)){
    	   String [] arr=string.split(",");
    	   Integer [] newArray=new Integer[arr.length];
    	   for(int i = 0; i < arr.length; i++) {
			   newArray[i]=Integer.valueOf(arr[i]);
    	   }
    	   return newArray;
    	} 
    	return  new Integer[0];
    }
}
