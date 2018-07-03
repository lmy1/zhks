package com.cd.uap.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * 密码加密类
 * @author li.mingyang
 *
 */
@Component
public class MyPasswordEncoder{
	
	private static Logger logger = LoggerFactory.getLogger(MyPasswordEncoder.class);
	
	/**
	 * 密码加密，在密码存入数据库时需要****人为****调用
	 */
//	@Override
	public static String encode(String frontPassword) throws Exception {
		String encodeMD5;

		//先解前端密
		String rawPassword = AESUtils.desEncrypt(frontPassword);
		//再MD5加密
		encodeMD5 = MD5Util.md5Digest(rawPassword);

		return encodeMD5;
	}

	/**
	 * 匹配登录和数据库中的密码是否一致，是****springsecurity****自动调用的,调用时机就是在UserDetailsService实现类返回User的时候将返回值中的密码和传入的密码进行比对
	 */
	/*@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		try {
			// TODO 可能报错
			String password = encodedPassword;
			if (password != null && password.matches("[0-9]{6}")) {
				if (null == rawPassword) {
					return false;
				} else if (rawPassword.equals(encodedPassword)) {
					return true;
				} else {
					return false;
				}
			}
			AESUtils.desEncrypt()
			String encodeMD5 = MD5Util.md5Digest((String)rawPassword);
			if (null != encodeMD5 && encodeMD5.equals(encodedPassword)) {
				return true;
			}
		} catch (Exception e) {
			logger.error("MD5加密错误");
		}
		return false;
	}*/

}
