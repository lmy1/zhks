package com.cd.uap.validation;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
public class UserValidation {
	/**
	 * 校验密码
	 * 
	 * @param mm
	 * @throws ConstraintViolationException
	 */
	public void checkMM(
			@NotEmpty(message = "密码不能为空") @Size(min = 8, max = 32, message = "密码必须大于8位小于32位") @Pattern(regexp = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？])+$", message = "密码格式错误") String mm)
			throws ConstraintViolationException {
	}

	public void checkSjh(@NotBlank(message = "手机号不能为空") @Pattern(regexp="^(1[356789]{1})\\d{9}$",message="手机号格式错误") String sjh ) throws ConstraintViolationException {
	}

	public void checkXm(@NotBlank(message = "姓名不能为空") String xm) throws ConstraintViolationException {

	}

	public void checkValidateCode(@NotBlank(message = "验证码不能为空") String validateCode) throws ConstraintViolationException {

	}
}
