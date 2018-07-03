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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * TblExam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_EXAM")

public class TblExam implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotBlank(message="1112")
	@Size(max=32,message="1113")
	private String examName;
	@NotBlank(message="1114")
	private String examType;
	private String examModel;
	private Date startTime;
	private Date endTime;
	@NotNull(message="1115")
	@Max(value=99999,message="1116")
	private Long total;
	private Double totalScore;
	private String solveType;
	@NotNull(message="1119")
	@Max(value=100,message="1120")
	private Long passScore;
	@NotNull(message="1121")
	@Max(value=100,message="1122")
	private Long excelScore;
	@NotNull(message="1123")
	@Max(value=300,message="1124")
	private Long answerTime;
	private String levelFirst;
	private String isDelete;
	@NotBlank(message="1125")
	private String isEnable;
	private Date createTime;
	@NotNull(message="1163")
	private Double singleScore;
	@NotNull(message="1164")
	private Double checkBoxScore;
	@Valid
	private Set<TblExamTopicnum> tblExamTopicnum = new HashSet<TblExamTopicnum>();
	@Valid
	private Set<TblExamUser> tblExamUser = new HashSet<TblExamUser>();
	// Constructors

	/** default constructor */
	public TblExam() {
	}

	/** minimal constructor */
	public TblExam(Long id, String examName, String examType, String examModel, Long total, Double totalScore,
			Long passScore, Long excelScore, Long answerTime, String levelFirst, String isDelete, 
			String isEnable, Date createTime) {
		this.id = id;
		this.examName = examName;
		this.examType = examType;
		this.examModel = examModel;
		this.total = total;
		this.totalScore = totalScore;
		this.passScore = passScore;
		this.excelScore = excelScore;
		this.answerTime = answerTime;
		this.levelFirst = levelFirst;
		this.isDelete = isDelete;
		this.isEnable = isEnable;
		this.createTime = createTime;
	}

	/** full constructor */
	public TblExam(Long id, String examName, String examType, String examModel, Date startTime,
			Date endTime, Long total, Double totalScore, String solveType, Long passScore, Long excelScore,
			Long answerTime, String levelFirst, String isDelete, String isEnable,
			Date createTime) {
		this.id = id;
		this.examName = examName;
		this.examType = examType;
		this.examModel = examModel;
		this.startTime = startTime;
		this.endTime = endTime;
		this.total = total;
		this.totalScore = totalScore;
		this.solveType = solveType;
		this.passScore = passScore;
		this.excelScore = excelScore;
		this.answerTime = answerTime;
		this.levelFirst = levelFirst;
		this.isDelete = isDelete;
		this.isEnable = isEnable;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblExamSeq")
    @SequenceGenerator(name = "TblExamSeq", sequenceName = "SEQ_EXAM",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "EXAM_NAME", nullable = false, length = 64)

	public String getExamName() {
		return this.examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	@Column(name = "EXAM_TYPE", nullable = false, length = 2)

	public String getExamType() {
		return this.examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	@Column(name = "EXAM_MODEL", nullable = false, length = 2)

	public String getExamModel() {
		return this.examModel;
	}

	public void setExamModel(String examModel) {
		this.examModel = examModel;
	}

	@Column(name = "START_TIME", length = 7)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 7)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "TOTAL", nullable = false, precision = 10, scale = 0)

	public Long getTotal() {
		return this.total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Column(name = "SOLVE_TYPE", length = 2)

	public String getSolveType() {
		return this.solveType;
	}

	public void setSolveType(String solveType) {
		this.solveType = solveType;
	}

	@Column(name = "PASS_SCORE", nullable = false, precision = 10, scale = 0)

	public Long getPassScore() {
		return this.passScore;
	}

	public void setPassScore(Long passScore) {
		this.passScore = passScore;
	}

	@Column(name = "EXCEL_SCORE", nullable = false, precision = 10, scale = 0)

	public Long getExcelScore() {
		return this.excelScore;
	}

	public void setExcelScore(Long excelScore) {
		this.excelScore = excelScore;
	}

	@Column(name = "ANSWER_TIME", nullable = false, precision = 10, scale = 0)

	public Long getAnswerTime() {
		return this.answerTime;
	}

	public void setAnswerTime(Long answerTime) {
		this.answerTime = answerTime;
	}

	@Column(name = "LEVEL_FIRST", nullable = false, length = 40)

	public String getLevelFirst() {
		return this.levelFirst;
	}

	public void setLevelFirst(String levelFirst) {
		this.levelFirst = levelFirst;
	}

	@Column(name = "IS_DELETE", nullable = false, length = 1)

	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "IS_ENABLE", nullable = false, length = 1)

	public String getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name = "CREATE_TIME", nullable = false, length = 7)

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@OneToMany(cascade=CascadeType.REFRESH,mappedBy="tblExam",fetch=FetchType.EAGER)
	public Set<TblExamTopicnum> getTblExamTopicnum() {
		return tblExamTopicnum;
	}

	public void setTblExamTopicnum(Set<TblExamTopicnum> tblExamTopicnum) {
		this.tblExamTopicnum = tblExamTopicnum;
	}

	@OneToMany(cascade=CascadeType.REFRESH,mappedBy="tblExam",fetch=FetchType.EAGER)
	public Set<TblExamUser> getTblExamUser() {
		return tblExamUser;
	}

	public void setTblExamUser(Set<TblExamUser> tblExamUser) {
		this.tblExamUser = tblExamUser;
	}

	@Column(name = "SINGLE_SCORE", nullable = false, precision = 10, scale = 0)
	public Double getSingleScore() {
		return singleScore;
	}

	public void setSingleScore(Double singleScore) {
		this.singleScore = singleScore;
	}

	@Column(name = "CHECKBOX_SCORE", nullable = false, precision = 10, scale = 0)
	public Double getCheckBoxScore() {
		return checkBoxScore;
	}

	public void setCheckBoxScore(Double checkBoxScore) {
		this.checkBoxScore = checkBoxScore;
	}

	@Column(name = "TOTAL_SCORE", nullable = false, precision = 10, scale = 0)
	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}
}