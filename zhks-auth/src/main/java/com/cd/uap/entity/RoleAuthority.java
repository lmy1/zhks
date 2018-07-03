package com.cd.uap.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_ROLE_AUTHORITY")
public class RoleAuthority implements java.io.Serializable {

    private static final long serialVersionUID = 470645620028851357L;

	// Fields
    // 角色权限中间包唯一标识
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ROLE_AUTHORITY")
    @SequenceGenerator(name = "gen_ROLE_AUTHORITY", sequenceName = "SEQ_ROLE_AUTHORITY")
    private Long id;
    
    // 角色ID
    private Long roleId;
    
    // 权限ID
    private Long authorityId;
    

	// Constructors
    public RoleAuthority() {
		super();
	}

    // Constructors
    public RoleAuthority(Long id) {
		super();
        this.id = id;
	}

    // Constructors
    public RoleAuthority(
            Long id,
            Long roleId,
            Long authorityId) {
		super();
        this.id = id;
        this.roleId = roleId;
        this.authorityId = authorityId;
	}

    
    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
    
    public Long getAuthorityId() {
		return this.authorityId;
	}

	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}
    
}