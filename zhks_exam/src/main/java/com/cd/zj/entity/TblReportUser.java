package com.cd.zj.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TBL_REPORT_USER")
public class TblReportUser implements java.io.Serializable{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotBlank(message="1156")
	@Size(max=500,message="1157")
	private String context;
	private Long userid;
	private Long createPerson;
	private Date createtime;

	// Constructors

	/** default constructor */
	public TblReportUser() {
	}

	/** minimal constructor */
	public TblReportUser(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TblReportUser(Long id, String context, Long userid, Date createtime) {
		this.id = id;
		this.context = context;
		this.userid = userid;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblReportSeq")
	@SequenceGenerator(name = "TblReportSeq", sequenceName = "SEQ_REPORT_USER",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CONTEXT", length = 500)

	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Column(name = "USERID", precision = 10)

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "CREATETIME", length = 11)

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "CREATEPERSON", length = 10)
	public Long getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(Long createPerson) {
		this.createPerson = createPerson;
	}
}
