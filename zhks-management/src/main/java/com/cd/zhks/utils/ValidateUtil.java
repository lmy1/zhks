package com.cd.zhks.utils;

import java.text.SimpleDateFormat;

import com.alibaba.druid.util.StringUtils;


public class ValidateUtil {
	//身份证校验用]
	private final static char[] ID_CDS = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
	private final static int[] ID_PATTERNS = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
	
	/**
	 * 身份证号码校验
	 * @param value
	 * @return
	 */
	public static boolean isIdCard01(String value) {
		if(StringUtils.isEmpty(value)) return false;
		if(value.length()==15) return isIdCard01_15(value);
		if(value.length()==18) return isIdCard01_18(value);
		return false;
	}
	
	/**
	 * 身份证号码校验
	 * @param value
	 * @return
	 */
	public static boolean isIdCard01AndNotNull(String value) {
		if(value.length()==15) return isIdCard01_15(value);
		if(value.length()==18) return isIdCard01_18(value);
		return false;
	}
	
	/**
	 * 15位身份证号码校验
	 * @param value
	 * @return
	 */
	public static boolean isIdCard01_15(String value) {
		if(value == null || value.length() != 15) return false;

		//生日检查
		String birth = value.substring(6,12);
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");

		try {
			df.parse(birth);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	/**
	 * 18位身份证号码校验
	 * @param value
	 * @return
	 */
	public static boolean isIdCard01_18(String value) {
		if(value == null || value.length() != 18) return false;
		
		//生日检查
		String birth = value.substring(6,14);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		try {
			df.parse(birth);
		} catch (Exception e) {
			return false;
		}
		
		//验证码
		char cd = getIdCard01_18CD(value);
		
		return (cd == value.charAt(17));
	}

	/**
	 * 计算18位身份证校验码
	 * @param value
	 * @return
	 */
	public static char getIdCard01_18CD(String value) {
		int sum = 0;

		for(int i=0; i<17; i++){
			sum += Integer.parseInt(value.substring(i,i+1))*ID_PATTERNS[i];
		}
		
		int cd = sum % 11;
		return ID_CDS[cd];
	}
}
