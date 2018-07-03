package com.cd.zhks.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TBL_USER")
public class User implements java.io.Serializable {

    private static final long serialVersionUID = 154411050769720369L;

	// Fields
    // 用户唯一标识
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_USER")
    @SequenceGenerator(name = "gen_USER", sequenceName = "SEQ_USER",allocationSize=1)
    private Long id;
    
    // 用户姓名
    @NotBlank(message = "用户姓名不能为空")
    @Size(message = "用户姓名长度必须在20之内", max = 20)
    private String name;
    
    // 手机号
    @NotBlank(message = "手机号不能为空")
    @Pattern(message = "手机号格式错误", regexp = "^(1[356789]{1})\\d{9}$")
    private String telephone;
    
    // 身份证号(登录账号)
    @NotBlank(message = "身份证号(登录账号)不能为空")
    private String idCard;
    
    // 性别(0女，1男)
    @NotNull(message = "性别不能为空")
    private Long sex;
    
    // 工作单位关联ID
    
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
    

    @ManyToMany(targetEntity=Role.class, mappedBy="users")
    @JsonIgnoreProperties(value = {"users"}, allowSetters = true)
    private java.util.Set<Role> roles;
        

    @ManyToOne
	@JoinColumn(name="orgId",referencedColumnName="id")
    @JsonIgnoreProperties(value = {"users"}, allowSetters = true)
	private Org org;
        

	// Constructors
    public User() {
		super();
	}

    // Constructors
    public User(Long id) {
		super();
        this.id = id;
	}

    // Constructors
    public User(
            Long id,
            String name,
            String telephone,
            String idCard,
            Long sex,
            java.util.Date createdDate,
            Long reviewStatus,
            String password,
            Long createdUserId,
            String unreviewReason) {
		super();
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.idCard = idCard;
        this.sex = sex;
        this.createdDate = createdDate;
        this.reviewStatus = reviewStatus;
        this.password = password;
        this.createdUserId = createdUserId;
        this.unreviewReason = unreviewReason;
	}

    
    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
    
    public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
    
    public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}
    
    
    public java.util.Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
    
    public Long getReviewStatus() {
		return this.reviewStatus;
	}

	public void setReviewStatus(Long reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
    
    public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    public Long getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}
    
    public String getUnreviewReason() {
		return this.unreviewReason;
	}

	public void setUnreviewReason(String unreviewReason) {
		this.unreviewReason = unreviewReason;
	}
    

    public java.util.Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(java.util.Set<Role> roles) {
		this.roles = roles;
	}

    public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
}