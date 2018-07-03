package com.cd.zhks.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultCode {
	SUCCESS("1000", "成功"),
	FAILED_VALIDATION("1100", "字段校验失败"),
	FAILED_INSPECTION("1200", "数据检查失败"),
	FAILED_LOGIC("1300", "逻辑判断失败"),
	FAILED_SYSTEM("1900", "系统错误");

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