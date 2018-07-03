package com.cd.zj.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cd.zj.bean.Response;
import com.cd.zj.util.CodeMsg;


@ControllerAdvice
public class ControllerExceptionHandler {
	private Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response handleException(Exception e) {
		e.printStackTrace();
		Response response =new Response();
		response.setStatus(1);
		if(e.getMessage().matches("\\d+")) {//若返回的是数字这配置文件取值
			response.setResult(e.getMessage(),CodeMsg.getMsg(e.getMessage()));
		}else if(e.getMessage().contains("Exception")){//系统抛出异常 显示操作失败
			response.setResult("1001",CodeMsg.getMsg("1001"));
		}else {
			response.setResult("1003",e.getMessage());//主要是spring mvc验证框架里抛出的异常
		}
		log.debug(e.getMessage());
		response.setData("1");
		return response;
	}
}