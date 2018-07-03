package com.cd.zj.entity;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * TblTrain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_TRAIN")

public class TblTrain implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotNull(message="1145")
	private Long times;
	@NotNull(message="1146")
	private Date startTime;
	private Long userId;
	private TblDatum tblDatum;

	// Constructors

	/** default constructor */
	public TblTrain() {
	}

	/** full constructor */
	public TblTrain(Long id, Long times, Date startTime, Long userId) {
		this.id = id;
		this.times = times;
		this.startTime = startTime;
		this.userId = userId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblTrainSeq")
    @SequenceGenerator(name = "TblTrainSeq", sequenceName = "SEQ_TRAIN",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TIMES", nullable = false, precision = 10)

	public Long getTimes() {
		return this.times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	@Column(name = "START_TIME", nullable = false, length = 7)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "USER_ID", nullable = false, precision = 20)

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@OneToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="DATUM_ID")
	public TblDatum getTblDatum() {
		return tblDatum;
	}

	public void setTblDatum(TblDatum tblDatum) {
		this.tblDatum = tblDatum;
	}
}