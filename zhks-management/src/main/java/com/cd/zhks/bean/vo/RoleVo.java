package com.cd.zhks.bean.vo;

import io.swagger.annotations.ApiModelProperty;

public class RoleVo {

	@ApiModelProperty("角色ID")
	private Long id;

	@ApiModelProperty("角色名")
	private String roleName;

	@ApiModelProperty("角色排序")
	private Long sort;

	@ApiModelProperty("角色绑定的权限id数组")
	private Long[] authoritys;

	public RoleVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoleVo(Long id, String roleName, Long sort, Long[] authoritys) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.sort = sort;
		this.authoritys = authoritys;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long[] getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(Long[] authoritys) {
		this.authoritys = authoritys;
	}




}
