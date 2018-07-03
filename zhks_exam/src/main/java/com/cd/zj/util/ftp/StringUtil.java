package com.cd.zj.util.ftp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtil {

	/**
	 * 字符串首字母大写
	 * 
	 * @param name
	 * @return
	 */
	public static String firstToUpperCase(String name) {
		// 普通写法
		// name = name.substring(0, 1).toUpperCase() + name.substring(1);
		// return name;
		// 大神写法 效率高
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);

	}
	
	/**
	 * 判断字符串是否为空
	 * 
	 * eg:
	 * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
	
	
	/**
	 * 判断字符串是否为不空
	 * 
	 * eg:
	 * StringUtil.isNotBlank(null)      = true
     * StringUtil.isNotBlank("")        = true
     * StringUtil.isNotBlank(" ")       = true
     * StringUtil.isNotBlank("bob")     = false
     * StringUtil.isNotBlank("  bob  ") = false
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isNotBlank(final CharSequence cs) {
       return !isBlank(cs);
    }
	
	/**
	 * 字符串按长日期格式转为日期
	 * 
	 * @param qvalue
	 * @return
	 * @throws ParseException
	 */
	public static Date StringToLongDate(String qvalue) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateFormat.parse(qvalue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	

	
	/**
	 * 字符串按短日期格式转为日期
	 * 
	 * @param qvalue
	 * @return
	 * @throws ParseException
	 */
	public static Date StringToShortDate(String qvalue){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(qvalue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 根据日期格式自动将字符串转为相应的Date类型
	 * @param qvalue
	 * @param response
	 * @return
	 */
	public static Date StringToDate(String qvalue){
		Date date = null;
		if (qvalue.length() <= 10) {
			date = StringUtil.StringToShortDate(qvalue);
		} else if (qvalue.length() > 10) {
			date = StringUtil.StringToLongDate(qvalue);
		}
		return date;
		
	}
	
	 /** 
     * 删除起始字符 
     * @param s 
     * @return 
     *  @author xxj 2017年4月27日 
     */  
    public static String trimStart(String str,String trim){  
        if(str=="")  
            return "";  
        return str.replaceAll("^("+trim+")+", "");  
    }  
    /** 
     * 删除末尾字符 
     * @param s 
     * @return 
     *  @author xxj 2017年4月27日 
     */  
    public static String trimEnd(String str,String trim){  
        if(str=="")  
            return "";  
        return str.replaceAll("("+trim+")+$", "");  
    }  
    
    /** 
     * 字符串是否为空 
     * @param str 
     * @return 
     */  
    public static boolean isNullOrEmpty(String str){  
        return str==""|| str.trim().isEmpty();  
    }  
	
}
