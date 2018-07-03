package com.cd.zhks.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TBL_AREAINFO")
public class Areainfo implements java.io.Serializable {

    private static final long serialVersionUID = 995531059846989888L;

	// Fields
    // 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_AREAINFO")
    @SequenceGenerator(name = "gen_AREAINFO", sequenceName = "SEQ_AREAINFO")
    private String id;
    
    // 
    @Size(message = "长度必须在100之内", max = 100)
    private String regionname;
    
    // 
    @Size(message = "长度必须在12之内", max = 12)
    private String regioncode;
    
    // 
    @Size(message = "长度必须在32之内", max = 32)
    private String parentid;
    
    // 
    @Size(message = "长度必须在8之内", max = 8)
    private String sort;
    
    // 
    @Size(message = "长度必须在32之内", max = 32)
    private String userid;
    
    // 
    @Size(message = "长度必须在20之内", max = 20)
    private String createtime;
    
    // 
    @Size(message = "长度必须在1之内", max = 1)
    private String isdeleted;
    
    // 
    @Size(message = "长度必须在20之内", max = 20)
    private String updatetime;
    
    // 
    @Size(message = "长度必须在20之内", max = 20)
    private String deletetime;
    
    // 
    @Size(message = "长度必须在10之内", max = 10)
    private String regionlevel;
    
    // 
    @Size(message = "长度必须在200之内", max = 200)
    private String fullname;
    

	// Constructors
    public Areainfo() {
		super();
	}

    // Constructors
    public Areainfo(String id) {
		super();
        this.id = id;
	}

    // Constructors
    public Areainfo(
            String id,
            String regionname,
            String regioncode,
            String parentid,
            String sort,
            String userid,
            String createtime,
            String isdeleted,
            String updatetime,
            String deletetime,
            String regionlevel,
            String fullname) {
		super();
        this.id = id;
        this.regionname = regionname;
        this.regioncode = regioncode;
        this.parentid = parentid;
        this.sort = sort;
        this.userid = userid;
        this.createtime = createtime;
        this.isdeleted = isdeleted;
        this.updatetime = updatetime;
        this.deletetime = deletetime;
        this.regionlevel = regionlevel;
        this.fullname = fullname;
	}

    
    public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
    public String getRegionname() {
		return this.regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
    
    public String getRegioncode() {
		return this.regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
    
    public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
    
    public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
    
    public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
    
    public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
    
    public String getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
    
    public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
    
    public String getDeletetime() {
		return this.deletetime;
	}

	public void setDeletetime(String deletetime) {
		this.deletetime = deletetime;
	}
    
    public String getRegionlevel() {
		return this.regionlevel;
	}

	public void setRegionlevel(String regionlevel) {
		this.regionlevel = regionlevel;
	}
    
    public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
    
}