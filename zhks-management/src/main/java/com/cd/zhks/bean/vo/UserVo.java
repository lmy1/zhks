package com.cd.zhks.bean.vo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class UserVo {

	@ApiModelProperty("用户ID")
	private Long id;

	@ApiModelProperty("用户名")
	private String name;

	@ApiModelProperty("用户身份证号")
	private String idCard;

	@ApiModelProperty("手机号")
	private String telephone;

	@ApiModelProperty("创建日期")
	private Date createdDate;

	@ApiModelProperty("性别ID(0女，1男)")
	private Long sex;
	
	@ApiModelProperty("审核状态(0未审核，1通过，2未通过)")
	private Long reviewStatus;
	
	@ApiModelProperty("审核未通过原因")
    private String unreviewReason;
	
	//转化显示
	@ApiModelProperty("角色ID数组")
	private Long[] roleIds;

	@ApiModelProperty("角色名称数组")
	private String[] roleNames;
	
	//转化显示

	@ApiModelProperty("性别名称")
	private String sexName;
	
	//转化显示
	@ApiModelProperty("工作单位编码")
	private String orgCode;

	@ApiModelProperty("工作单位名称")
	private String orgName;
	
	//转化显示

	
	@ApiModelProperty("审核状态")
	private String reviewStatusName;
	
	//待定
	@ApiModelProperty("区域ID")
	private String areaInfo;
	
	public UserVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public UserVo(Long id, String name, String idCard, String telephone, Date createdDate, Long sex, Long reviewStatus,
			String unreviewReason, Long[] roleIds, String[] roleNames, String sexName, String orgCode, String orgName,
			String reviewStatusName, String areaInfo) {
		super();
		this.id = id;
		this.name = name;
		this.idCard = idCard;
		this.telephone = telephone;
		this.createdDate = createdDate;
		this.sex = sex;
		this.reviewStatus = reviewStatus;
		this.unreviewReason = unreviewReason;
		this.roleIds = roleIds;
		this.roleNames = roleNames;
		this.sexName = sexName;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.reviewStatusName = reviewStatusName;
		this.areaInfo = areaInfo;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public String[] getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Long reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getReviewStatusName() {
		return reviewStatusName;
	}

	public void setReviewStatusName(String reviewStatusName) {
		this.reviewStatusName = reviewStatusName;
	}

	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}

	public String getUnreviewReason() {
		return unreviewReason;
	}

	public void setUnreviewReason(String unreviewReason) {
		this.unreviewReason = unreviewReason;
	}

	
	
	
	
}
