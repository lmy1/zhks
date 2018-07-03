package com.cd.zj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

/**
 * TblClassify entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_CLASSIFY")

public class TblClassify implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	@NotBlank(message="1101")
	@Size(max=32,message="1110")
	private String classifyName;
	@NotBlank(message="1102")
	private String levels;
	private String parentKey;
	@NotBlank(message="1103")
	private String isHidden;
	private String isDelete;

	// Constructors

	/** default constructor */
	public TblClassify() {
	}

	public TblClassify(String id) {
		this.id = id;
	}

	/** minimal constructor */
	public TblClassify(String id, String classifyName, String levels, String isHidden, String isDelete) {
		this.id = id;
		this.classifyName = classifyName;
		this.levels = levels;
		this.isHidden = isHidden;
		this.isDelete = isDelete;
	}

	/** full constructor */
	public TblClassify(String id, String classifyName, String levels, String parentKey, String isHidden,
			String isDelete) {
		this.id = id;
		this.classifyName = classifyName;
		this.levels = levels;
		this.parentKey = parentKey;
		this.isHidden = isHidden;
		this.isDelete = isDelete;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", unique = true, nullable = false, length = 40, scale = 0)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CLASSIFY_NAME", nullable = false, length = 64)

	public String getClassifyName() {
		return this.classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	@Column(name = "LEVELS", nullable = false, length = 2)

	public String getLevels() {
		return this.levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	@Column(name = "PARENT_KEY", length = 40)
	public String getParentKey() {
		return this.parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	@Column(name = "IS_HIDDEN", nullable = false, length = 1)

	public String getIsHidden() {
		return this.isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}

	@Column(name = "IS_DELETE", nullable = false, length = 1)

	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

}