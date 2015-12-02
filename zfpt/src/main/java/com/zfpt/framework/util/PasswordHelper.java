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
}
