package com.cd.zj.entity;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * TblScpre entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_SCORE")

public class TblScore implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotNull(message="1147")
	private Double score;
	@NotNull(message="1146")
	private Date startTime;
	@NotNull(message="1148")
	private Date endTime;
	@NotNull(message="1149")
	private Long times;
	@NotBlank(message="1114")
	private String examType;
	private Long examId;
	private Long userId;

	// Constructors

	/** default constructor */
	public TblScore() {
	}

	/** minimal constructor */
	public TblScore(Long id, Double score) {
		this.id = id;
		this.score = score;
	}

	/** full constructor */
	public TblScore(Long id, Double score, Timestamp startTime, Timestamp endTime, Long times, Long exmUserId) {
		this.id = id;
		this.score = score;
		this.startTime = startTime;
		this.endTime = endTime;
		this.times = times;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblScoreSeq")
    @SequenceGenerator(name = "TblScoreSeq", sequenceName = "SEQ_SCORE",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SCORE", nullable = false, precision = 10, scale = 0)

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "START_TIME", length = 11)

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", length = 11)

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "TIMES", precision = 10, scale = 0)

	public Long getTimes() {
		return this.times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	@Column(name = "EXAM_TYPE", length = 3)
	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	@Column(name = "exam_id", precision = 10, scale = 0)
	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	@Column(name = "user_id", precision = 10, scale = 0)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}