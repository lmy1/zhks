package com.cd.zj.util;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;






public class MapUtils {
	/** 
	 * 将map装换为javabean对象 
	 * @param map 
	 * @param bean 
	 * @return 
	 */  
	 public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
	        if (map == null)  
	            return null;  
	  
	        Object obj = beanClass.newInstance();  
	  
	        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
	  
	        return obj;  
	    }   
	
	 /** 
		 * 将javabean装换为map对象 
		 * @param map 
		 * @param bean 
		 * @return 
		 */  
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T>List<Map<String,Object>> objectToMap(List<T> list){
		 List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
			for(T t:list) {
				try {
					Map map = PropertyUtils.describe(t);
					resultList.add(map);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		 return resultList;
	 }
	 
	 /**
	  * 把bean的字段明按字段的顺序组成数组
	  * @param t
	  * @return
	  */
	 public static <T> String[] getHeads(Class<T> t) {
		 Field[] fields = t.getDeclaredFields();
		String[] heads= new String[fields.length];
		int index=0;
		for(Field f:fields) {
			heads[index]=f.getName();
			index++;
		}
		 return heads;
	 }
}
