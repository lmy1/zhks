package com.cd.uap.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_USER_ROLE")
public class UserRole implements java.io.Serializable {

    private static final long serialVersionUID = 675476114852982377L;

	// Fields
    // 用户角色中间表主键
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_USER_ROLE")
    @SequenceGenerator(name = "gen_USER_ROLE", sequenceName = "SEQ_USER_ROLE")
    private Long id;
    
    // 用户ID
    private Long userId;
    
    // 角色ID
    private Long roleId;
    

	// Constructors
    public UserRole() {
		super();
	}

    // Constructors
    public UserRole(Long id) {
		super();
        this.id = id;
	}

    // Constructors
    public UserRole(
            Long id,
            Long userId,
            Long roleId) {
		super();
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
	}

    
    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
    
}