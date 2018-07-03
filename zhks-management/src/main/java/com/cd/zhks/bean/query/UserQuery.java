package com.cd.zhks.bean.query;

import io.swagger.annotations.ApiModelProperty;

public class UserQuery extends AbstarctQuery implements java.io.Serializable {
	private static final long serialVersionUID = 600424800278578412L;

	// Fields
	@ApiModelProperty("姓名/身份证号 条件查询")
    private String queryCondition;
    
	@ApiModelProperty("角色名")
    private String roleName;
    
	@ApiModelProperty("工作单位编码")
    private String orgCode;

	// Constructors
    public UserQuery() {
		super();
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
    
    
}