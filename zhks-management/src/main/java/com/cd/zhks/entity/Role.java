package com.cd.zhks.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TBL_ROLE")
public class Role implements java.io.Serializable {

    private static final long serialVersionUID = 785902921777077024L;

	// Fields
    // 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ROLE")
    @SequenceGenerator(name = "gen_ROLE", sequenceName = "SEQ_ROLE")
    private Long id;
    
    // 角色名称
    @NotBlank(message = "角色名称不能为空")
    @Size(message = "角色名称长度必须在50之内", max = 50)
    private String roleName;
    
    // 排序(默认为0)
    @Max(message = "排序(默认为0)最大为99999", value = 99999)
    private Long sort;
    
    // 创建时间
    private java.util.Date createdDate;
    
    // 创建者ID
    private Long createdUserId;
    

    @ManyToMany(targetEntity=Authority.class, mappedBy="roles", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties(value = {"roles"}, allowSetters = true)
    private java.util.Set<Authority> authoritys;
        

    @ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="TBL_USER_ROLE",
		joinColumns={@JoinColumn(name="roleId",referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="userId",referencedColumnName="id")}
	)
    private java.util.Set<User> users;
        

	// Constructors
    public Role() {
		super();
	}

    // Constructors
    public Role(Long id) {
		super();
        this.id = id;
	}

    // Constructors
    public Role(
            Long id,
            String roleName,
            Long sort,
            java.util.Date createdDate,
            Long createdUserId) {
		super();
        this.id = id;
        this.roleName = roleName;
        this.sort = sort;
        this.createdDate = createdDate;
        this.createdUserId = createdUserId;
	}

    
    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
    
    public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
    
    public java.util.Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
    
    public Long getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}
    

    public java.util.Set<Authority> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(java.util.Set<Authority> authoritys) {
		this.authoritys = authoritys;
	}

    public java.util.Set<User> getUsers() {
		return users;
	}

	public void setUsers(java.util.Set<User> users) {
		this.users = users;
	}
}