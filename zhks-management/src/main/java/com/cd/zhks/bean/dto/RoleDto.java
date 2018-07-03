package com.cd.zhks.bean.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;


public class RoleDto {
	
	@ApiModelProperty("角色名")
	// 角色名称
	@NotBlank(message = "角色名称不能为空")
	@Size(message = "角色名称长度必须在50之内", max = 50)
    private String roleName;
    
	@ApiModelProperty("角色排序")
	// 排序(默认为0)
	@Max(message = "排序(默认为0)最大为99999", value = 99999)
    private Long sort;

	public RoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoleDto(String roleName, Long sort) {
		super();
		this.roleName = roleName;
		this.sort = sort;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	
}
