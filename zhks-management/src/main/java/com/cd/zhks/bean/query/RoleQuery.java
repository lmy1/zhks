package com.cd.zhks.bean.query;

import io.swagger.annotations.ApiModelProperty;

public class RoleQuery extends AbstarctQuery implements java.io.Serializable {
	private static final long serialVersionUID = 301029391819217447L;

	// Fields
	@ApiModelProperty("角色名")
    private String roleName;

	// Constructors
    public RoleQuery() {
		super();
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
    
    
}