package com.zfpt.framework.util; 
import java.security.MessageDigest;

/**
 * 项目名称：travel_ip   
 * 类名称：MD5Utils   
 * 类描述：MD5工具类   
 * 创建人：chens
 * 创建时间：2015-3-31 上午11:15:09   
 * 修改备注：   
 * @version
 */
public class MD5Utils {
      
	 /**  
     * 获得MD5加密密码的方法  
     */  
    public static String getMD5ofStr(String origString) {   
        String origMD5 = null;   
        try {   
            MessageDigest md5 = MessageDigest.getInstance("MD5");   
            byte[] result = md5.digest(origString.getBytes());   
            origMD5 = byteArray2HexStr(result);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return origMD5;   
    }   
    /**  
     * 处理字节数组得到MD5密码的方法  
     */  
    private static String byteArray2HexStr(byte[] bs) {   
        StringBuffer sb = new StringBuffer();   
        for (byte b : bs) {   
            sb.append(byte2HexStr(b));   
        }   
        return sb.toString();   
    }   
    /**  
     * 字节标准移位转十六进制方法  
     */  
    private static String byte2HexStr(byte b) {   
        String hexStr = null;   
        int n = b;   
        if (n < 0) {   
            // 若需要自定义加密,请修改这个移位算法即可   
            n = b & 0x7F + 128;   
        }   
        hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);   
        return hexStr.toUpperCase();   
    }   
    /**  
     * 提供一个MD5多次加密方法  
     */  
    public static String getMD5ofStr(String origString, int times) {   
        String md5 = getMD5ofStr(origString);   
        for (int i = 0; i < times - 1; i++) {   
            md5 = getMD5ofStr(md5);   
        }   
        return getMD5ofStr(md5);   
    }   
    /**  
     * 密码验证方法  
     */  
    public static boolean verifyPassword(String inputStr, String MD5Code) {   
        return getMD5ofStr(inputStr).equals(MD5Code);   
    }   
    /**  
     * 多次加密时的密码验证方法  
     */  
    public static boolean verifyPassword(String inputStr, String MD5Code,   
            int times) {   
        return getMD5ofStr(inputStr, times).equals(MD5Code);   
    }   
    
    /**
     * 返回一个MD5加密后的字符串.
     * 
     * @param src
     *            需要加密的字符串
     * @return 加密后的字符串
     */
    public  static String getMD5Digest(String src) {

        char[] ha = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            byte[] ba = md.digest();
            int j = ba.length;
            char[] ca = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; i++) {
                byte b = ba[i];
                ca[k++] = ha[b >>> 4 & 0xf];
                ca[k++] = ha[b & 0xf];
            }
            return new String(ca);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static void main(String[] args) {
		System.out.println(getMD5Digest(""));
	}
 
}  
