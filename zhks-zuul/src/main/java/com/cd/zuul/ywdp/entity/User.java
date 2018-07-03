package com.cd.zuul.ywdp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name="TBL_USER")
public class User {

	@Id
	private Long id;

	// 用户姓名
	private String name;

	// 手机号
	private String telephone;

	// 身份证号(登录账号)
	private String idCard;

	// 性别(0女，1男)
	private Long sex;

	// 工作单位关联ID
	private Long orgId;

	// 创建时间
	private java.util.Date createdDate;

	// 审核状态(0审核，1通过，2未通过)
	private Long reviewStatus;

	// 密码
	private String password;

	// 创建者ID
	private Long createdUserId;

	// 审核未通过原因
	private String unreviewReason;

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Long reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getUnreviewReason() {
		return unreviewReason;
	}

	public void setUnreviewReason(String unreviewReason) {
		this.unreviewReason = unreviewReason;
	}
}