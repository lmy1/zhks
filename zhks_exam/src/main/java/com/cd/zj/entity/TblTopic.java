package com.cd.zj.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * TblTopic entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_TOPIC")

public class TblTopic implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotBlank(message="1105")
	private String classifyKind;
	@NotBlank(message="1134")
	@Size(max=500,message="1135")
	private String topics;
	@NotBlank(message="1136")
	private String answer;
	@NotBlank(message="1137")
	@Size(max=500,message="1139")
	private String resolve;
	private Date createTime;
	@NotBlank(message="1138")
	private String createPerson;
	private String isDelete;
	@NotBlank(message="1162")
	private String topicType;//0单选1多选
	private Set<TblDatum> tblDatum = new HashSet<TblDatum>();
	private Set<TblExamTopicnum> tblExamTopicnum = new HashSet<TblExamTopicnum>();
	@Valid
	private Set<TblOptions> tblOptions = new HashSet<TblOptions>();
	// Constructors

	/** default constructor */
	public TblTopic() {
	}

	/** minimal constructor */
	public TblTopic(Long id, String classifyKind, String topics, String answer,
			String createPerson, String isDelete) {
		this.id = id;
		this.classifyKind = classifyKind;
		this.topics = topics;
		this.answer = answer;
		this.createPerson = createPerson;
		this.isDelete = isDelete;
	}

	/** full constructor */
	public TblTopic(Long id, String classifyKind, String topics, String answer, String resolve,
			Date createTime, String createPerson, String isDelete) {
		this.id = id;
		this.classifyKind = classifyKind;
		this.topics = topics;
		this.answer = answer;
		this.resolve = resolve;
		this.createTime = createTime;
		this.createPerson = createPerson;
		this.isDelete = isDelete;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblTopicSeq")
    @SequenceGenerator(name = "TblTopicSeq", sequenceName = "SEQ_TOPIC",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CLASSIFY_KIND", nullable = false, length = 80)

	public String getClassifyKind() {
		return this.classifyKind;
	}

	public void setClassifyKind(String classifyKind) {
		this.classifyKind = classifyKind;
	}

	@Column(name = "TOPICS", nullable = false, length = 1000)

	public String getTopics() {
		return this.topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	@Column(name = "ANSWER", nullable = false, length = 200)

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "RESOLVE", length = 1000)

	public String getResolve() {
		return this.resolve;
	}

	public void setResolve(String resolve) {
		this.resolve = resolve;
	}

	@Column(name = "CREATE_TIME", length = 7)

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_PERSON", nullable = false, length = 32)

	public String getCreatePerson() {
		return this.createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	@Column(name = "IS_DELETE", nullable = false, length = 1)

	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@JsonIgnore
	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="tblTopic")
	public Set<TblDatum> getTblDatum() {
		return tblDatum;
	}

	public void setTblDatum(Set<TblDatum> tblDatum) {
		this.tblDatum = tblDatum;
	}

	@JsonIgnore
	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="tblTopic")
	public Set<TblExamTopicnum> getTblExamTopicnum() {
		return tblExamTopicnum;
	}

	public void setTblExamTopicnum(Set<TblExamTopicnum> tblExamTopicnum) {
		this.tblExamTopicnum = tblExamTopicnum;
	}

	@OneToMany(cascade=CascadeType.REFRESH,mappedBy="tblTopic",fetch=FetchType.EAGER)
	public Set<TblOptions> getTblOptions() {
		return tblOptions;
	}

	public void setTblOptions(Set<TblOptions> tblOptions) {
		this.tblOptions = tblOptions;
	}
	@Column(name = "TOPIC_TYPE", nullable = false, length = 1)
	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}
}