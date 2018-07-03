package com.cd.uap.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultCode {
	SUCCESS("1000", "成功"),
	FAILED_VALIDATION("1100", "字段校验失败"),
	FAILED_INSPECTION("1200", "数据检查失败"),
	FAILED_LOGIC("1300", "逻辑判断失败"),
	FAILED_LOGIN("1400", "登录失败"),
	FAILED_LOGOUT("1500", "登出失败"),
	FAILED_SYSTEM("1900", "系统错误"),
	LOGIN_OUT_SUCCESS("1600", "退出成功"),

	//验证码错误
	MESSAGE_SEND_FAILED("1901","短信发送失败"),
	REGIST_PHONE_ERROR("1902","手机号不合法或已注册"),
	REGIST_PASSWORD_ERROR("1903","密码格式错误"),
	VALIDATE_CODE_ERROR("1904","短信验证码错误"),
	REGIST_ROLEUSER_ERROR("1905","分配用户角色错误"),
	REGIST_ADMIN_ERROR("1906", "注册管理员registAdmin必须存在"),
	PASSWORD_PHONE_ERROR("1907", "手机号不合法或未注册"),
	MESSAGE_LIMIT_CONTROL("1908","短信发送过于频繁或已达到每日上限"),
	MOBILE_NUMBER_ILLEGAL("1909","手机号不合法"),
	AMOUNT_NOT_ENOUGH("1910","短信平台余额不足"),
	MESSAGE_SYSTEM_ERROR("1911","短信平台系统错误"),	//阿里的错误
	MESSAGE_CLIENT_ERROR("1912","短信平台客户端错误");	//uap调用阿里短信服务时发生的错误

	private final String code;

	private String message;

	private ResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public ResultCode appendMsg(String appendMsg) {
		StringBuilder result = new StringBuilder();
		// result.append(msg);
		// result.append("[");
		result.append(appendMsg);
		// result.append("]");

		this.message = result.toString();

		return this;
	}
}