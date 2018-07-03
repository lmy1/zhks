package com.cd.zj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * TblExamTopicnum entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_EXAM_TOPICNUM")

public class TblExamTopicnum implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String levelSecond;
	@Max(value=500,message="1126")
	private Long topicNum;
	@NotBlank(message="1162")
	private String topicType;//0单选1多选
	private TblExam tblExam;
	private Set<TblTopic> tblTopic = new HashSet<TblTopic>();

	// Constructors

	/** default constructor */
	public TblExamTopicnum() {
	}

	/** full constructor */
	public TblExamTopicnum(Long id, String levelSecond, Long topicNum) {
		this.id = id;
		this.levelSecond = levelSecond;
		this.topicNum = topicNum;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblExamTopicnumNUMSeq")
    @SequenceGenerator(name = "TblExamTopicnumNUMSeq", sequenceName = "SEQ_EXAM_TOPICNUM",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "LEVEL_SECOND", nullable = false, length = 40)

	public String getLevelSecond() {
		return this.levelSecond;
	}

	public void setLevelSecond(String levelSecond) {
		this.levelSecond = levelSecond;
	}

	@Column(name = "TOPIC_NUM", nullable = false, precision = 10, scale = 0)

	public Long getTopicNum() {
		return this.topicNum;
	}

	public void setTopicNum(Long topicNum) {
		this.topicNum = topicNum;
	}

	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="EXAM_ID")
	public TblExam getTblExam() {
		return tblExam;
	}

	public void setTblExam(TblExam tblExam) {
		this.tblExam = tblExam;
	}

	@ManyToMany(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinTable(name="TBL_EXAM_TOPIC",inverseJoinColumns=@JoinColumn(name="TOPIC_ID"),joinColumns=@JoinColumn(name="TOPICNUM_ID"))
	public Set<TblTopic> getTblTopic() {
		return tblTopic;
	}

	public void setTblTopic(Set<TblTopic> tblTopic) {
		this.tblTopic = tblTopic;
	}

	@Column(name = "TOPIC_TYPE", nullable = false, length = 1)
	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}

}