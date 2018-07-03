package com.cd.uap.entity;

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
import java.util.HashSet;

@Entity
@Table(name = "TBL_AUTHORITY")
public class Authority implements java.io.Serializable {

    private static final long serialVersionUID = 844063086577399351L;

	// Fields
    // 权限ID
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_AUTHORITY")
    @SequenceGenerator(name = "gen_AUTHORITY", sequenceName = "SEQ_AUTHORITY")
    private Long id;
    
    // 权限名称
    private String authorityName;
    
    // 创建时间
    private java.util.Date createdDate;
    
    // 英文标识
    private String authorityIdentification;
    
    // 备注
    private String remark;
    
    // 所属模块
    private String belongModule;
    

    @ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="TBL_ROLE_AUTHORITY",
		joinColumns={@JoinColumn(name="authorityId",referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="roleId",referencedColumnName="id")}
	)
    private java.util.Set<Role> roles = new HashSet<>();
        

	// Constructors
    public Authority() {
		super();
	}

    // Constructors
    public Authority(Long id) {
		super();
        this.id = id;
	}

    // Constructors
    public Authority(
            Long id,
            String authorityName,
            java.util.Date createdDate,
            String authorityIdentification,
            String remark,
            String belongModule) {
		super();
        this.id = id;
        this.authorityName = authorityName;
        this.createdDate = createdDate;
        this.authorityIdentification = authorityIdentification;
        this.remark = remark;
        this.belongModule = belongModule;
	}

    
    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    public String getAuthorityName() {
		return this.authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
    
    public java.util.Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}
    
    public String getAuthorityIdentification() {
		return this.authorityIdentification;
	}

	public void setAuthorityIdentification(String authorityIdentification) {
		this.authorityIdentification = authorityIdentification;
	}
    
    public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    public String getBelongModule() {
		return this.belongModule;
	}

	public void setBelongModule(String belongModule) {
		this.belongModule = belongModule;
	}
    

    public java.util.Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(java.util.Set<Role> roles) {
		this.roles = roles;
	}
}