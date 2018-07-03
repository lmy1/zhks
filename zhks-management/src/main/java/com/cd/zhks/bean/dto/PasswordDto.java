package com.cd.zhks.bean.dto;

import io.swagger.annotations.ApiModelProperty;

public class PasswordDto {
	
	@ApiModelProperty("旧用户密码")
	private String oldPassword;
	
	@ApiModelProperty("新用户密码")
	private String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	
}

