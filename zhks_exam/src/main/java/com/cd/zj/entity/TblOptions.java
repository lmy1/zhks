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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * TblOptions entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_OPTIONS")

public class TblOptions implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotBlank(message="1140")
	@Size(max=2,message="1141")
	private String options;
	@NotBlank(message="1142")
	@Size(max=500,message="1143")
	private String content;
	private TblTopic tblTopic;

	// Constructors

	/** default constructor */
	public TblOptions() {
	}

	/** full constructor */
	public TblOptions(Long id, String options, String content) {
		this.id = id;
		this.options = options;
		this.content = content;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblOptionsSeq")
    @SequenceGenerator(name = "TblOptionsSeq", sequenceName = "SEQ_OPTIONS",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OPTIONS", nullable = false, length = 2)

	public String getOptions() {
		return this.options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	@Column(name = "CONTENT", nullable = false, length = 1000)

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="TOPIC_ID")
	public TblTopic getTblTopic() {
		return tblTopic;
	}

	public void setTblTopic(TblTopic tblTopic) {
		this.tblTopic = tblTopic;
	}

}