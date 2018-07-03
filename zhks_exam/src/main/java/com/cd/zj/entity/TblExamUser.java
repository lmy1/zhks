package com.cd.zj.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.cd.zj.util.group.Validate1;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * TblExamUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_EXAM_USER")

public class TblExamUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotNull(message="1131",groups=Validate1.class)
	private Long userId;
	@NotBlank(message="1132",groups=Validate1.class)
	private String orgCode;
	private TblExam tblExam;

	// Constructors

	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="EXAM_ID")
	public TblExam getTblExam() {
		return tblExam;
	}

	public void setTblExam(TblExam tblExam) {
		this.tblExam = tblExam;
	}

	/** default constructor */
	public TblExamUser() {
	}

	/** minimal constructor */
	public TblExamUser(Long id, Long userId, String orgCode) {
		this.id = id;
		this.userId = userId;
		this.orgCode = orgCode;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblExamUserSeq")
    @SequenceGenerator(name = "TblExamUserSeq", sequenceName = "SEQ_EXAM_USER",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USER_ID", nullable = false,  precision = 20)

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "ORG_CODE", nullable = false, length = 10)

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}