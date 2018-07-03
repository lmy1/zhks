package com.cd.zhks.bean.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户新增修改数据
 * @author li.mingyang
 *
 */
public class UserDto {
	
	@ApiModelProperty("用户名")
	private String name;
	
	@ApiModelProperty("用户id(账号)")
	private String idCard;
	
	@ApiModelProperty("手机号")
	private String telephone;

	@ApiModelProperty("性别(0女，1男)")
	private Long sex;

	@ApiModelProperty("工作单位ID")
	private Long orgId;

	@ApiModelProperty("角色ID集合")
	private Long[] roleIds;

	@ApiModelProperty("区域信息ID")
	private Long areaInfoId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getAreaInfoId() {
		return areaInfoId;
	}

	public void setAreaInfoId(Long areaInfoId) {
		this.areaInfoId = areaInfoId;
	}
	
	
	
}
