package com.cd.zhks.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TBL_ORG")
public class Org implements java.io.Serializable {

    private static final long serialVersionUID = 756074702696996632L;

	// Fields
    // 
    @Size(message = "长度必须在12之内", max = 12)
    private String orgCode;
    
    // 
    @Size(message = "长度必须在100之内", max = 100)
    private String orgName;
    
    // 
    @Size(message = "长度必须在12之内", max = 12)
    private String regionCode;
    
    // 
    @Size(message = "长度必须在50之内", max = 50)
    private String leader;
    
    // 
    private Double isDelete;
    
    // 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ORG")
    @SequenceGenerator(name = "gen_ORG", sequenceName = "SEQ_ORG")
    private Long id;
    
    // 
    @Size(message = "长度必须在1之内", max = 1)
    private String bak;
    
    @OneToMany(mappedBy="org", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties(value = {"org"}, allowSetters = true)
    private java.util.Set<User> users;
        

	// Constructors
    public Org() {
		super();
	}

    // Constructors
    public Org(Long id) {
		super();
        this.id = id;
	}

    // Constructors
    public Org(
            String orgCode,
            String orgName,
            String regionCode,
            String leader,
            Double isDelete,
            Long id,
            String bak) {
		super();
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.regionCode = regionCode;
        this.leader = leader;
        this.isDelete = isDelete;
        this.id = id;
        this.bak = bak;
	}

    
    public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
    
    public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    
    public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
    
    public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
    
    public Double getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Double isDelete) {
		this.isDelete = isDelete;
	}
    
    public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    public String getBak() {
		return this.bak;
	}

	public void setBak(String bak) {
		this.bak = bak;
	}
    

    public java.util.Set<User> getUsers() {
		return users;
	}

	public void setUsers(java.util.Set<User> users) {
		this.users = users;
	}
}