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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * TblDatum entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TBL_DATUM")

public class TblDatum implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotBlank(message="1104")
	@Size(max=32,message="1111")
	private String datumName;
	@NotBlank(message="1105")
	private String classifyKind;
	@NotBlank(message="1106")
	private String format;
	@NotBlank(message="1103")
	private String isHidden;
	private String datumUrl;
	private String dataName;
	private String isDelete;
	private Set<TblTopic> tblTopic = new HashSet<TblTopic>();
	private TblTrain tblTrain;

	// Constructors

	/** default constructor */
	public TblDatum() {
	}

	/** full constructor */
	public TblDatum(Long id, String datumName, String classifyKind, String format, String isHidden, String datumUrl,
			 String isDelete) {
		this.id = id;
		this.datumName = datumName;
		this.classifyKind = classifyKind;
		this.format = format;
		this.isHidden = isHidden;
		this.datumUrl = datumUrl;
		this.isDelete = isDelete;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TblDatumSeq")
    @SequenceGenerator(name = "TblDatumSeq", sequenceName = "SEQ_DATUM",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DATUM_NAME", nullable = false, length = 64)

	public String getDatumName() {
		return this.datumName;
	}

	public void setDatumName(String datumName) {
		this.datumName = datumName;
	}

	@Column(name = "CLASSIFY_KIND", nullable = false, length = 80)

	public String getClassifyKind() {
		return this.classifyKind;
	}

	public void setClassifyKind(String classifyKind) {
		this.classifyKind = classifyKind;
	}

	@Column(name = "FORMAT", nullable = false, length = 32)

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Column(name = "IS_HIDDEN", nullable = false, length = 1)

	public String getIsHidden() {
		return this.isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}

	@Column(name = "DATUM_URL", nullable = false, length = 200)

	public String getDatumUrl() {
		return this.datumUrl;
	}

	public void setDatumUrl(String datumUrl) {
		this.datumUrl = datumUrl;
	}

	@Column(name = "IS_DELETE", nullable = false, length = 1)

	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@ManyToMany(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinTable(name="TBL_DATUM_TOPIC",inverseJoinColumns=@JoinColumn(name="TOPIC_ID"),joinColumns=@JoinColumn(name="DATUM_ID"))
	public Set<TblTopic> getTblTopic() {
		return tblTopic;
	}

	public void setTblTopic(Set<TblTopic> tblTopic) {
		this.tblTopic = tblTopic;
	}

	@JsonIgnore
	@OneToOne(cascade=CascadeType.REFRESH,mappedBy="tblDatum")
	public TblTrain getTblTrain() {
		return tblTrain;
	}

	public void setTblTrain(TblTrain tblTrain) {
		this.tblTrain = tblTrain;
	}

	@Column(name = "DATA_NAME", nullable = false, length = 500)
	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
}