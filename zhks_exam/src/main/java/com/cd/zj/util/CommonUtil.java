package com.cd.zj.util;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.cd.zj.bean.Response;

public class CommonUtil {

	/**
	 * 成功时调用
	 * @param response
	 */
	public static void success(Response response) {
		response.setStatus(0);
		response.setResult("1000", CodeMsg.getMsg("1000"));
	}

	public static String getError(BindingResult bindResult){
		String msg="";
		if(bindResult.hasErrors()) {
			List<ObjectError> mas= bindResult.getAllErrors();
			for (ObjectError o : mas) {
				msg+=CodeMsg.getMsg(o.getDefaultMessage())+" ";
			}
			
		}
		return msg;
	}
}
