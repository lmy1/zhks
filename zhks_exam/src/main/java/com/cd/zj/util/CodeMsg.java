package com.cd.zj.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class CodeMsg {
	//封装code和message
	static Properties prop = new Properties();
	
	static {
		InputStream resourceAsStream = null;
		try {
			resourceAsStream = CodeMsg.class.getClassLoader().getResourceAsStream("codeMsg.properties");
			prop.load(new InputStreamReader(resourceAsStream,"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace(); 
			throw new RuntimeException("application.properties读取失败");
		} finally {
			try {
				resourceAsStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据码表查询相应的信息
	 * @return
	 */
	public static String getMsg(String code) {
		String msg = prop.getProperty(code);
		return msg;
	}
}
