package com.zfpt.framework.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.zfpt.web.model.system.User;

public class PasswordHelper {
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private int hashIterations = 2;

	public void encryptPassword(User user) {
		String salt=randomNumberGenerator.nextBytes().toHex();
		user.setCredentialsSalt(salt);
		String newPassword = new SimpleHash(algorithmName, user.getUserPassword(), ByteSource.Util.bytes(user.getLoginCode()+salt), hashIterations).toHex();
		user.setUserPassword(newPassword);
	}
	/**
	 * 方法名称: getEncryptPassword
	 * 方法描述: 返回加密后密码
	 * 返回类型: String
	 * 创建人：chens
	 * 创建时间：2015年12月4日 下午4:25:54
	 * @throws
	 */
	public  String getEncryptPassword(String salt,String userName,String userPassword){
		return new SimpleHash(algorithmName, userPassword, ByteSource.Util.bytes(userName+salt), hashIterations).toHex();
	}
	
}
