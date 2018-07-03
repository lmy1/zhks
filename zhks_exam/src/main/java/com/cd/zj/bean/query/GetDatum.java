package com.cd.zj.bean.query;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class GetDatum {

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
	private String isDelete;
	private String dataName;
	private MultipartFile file;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getDatumName() {
		return this.datumName;
	}

	public void setDatumName(String datumName) {
		this.datumName = datumName;
	}
	public String getClassifyKind() {
		return this.classifyKind;
	}

	public void setClassifyKind(String classifyKind) {
		this.classifyKind = classifyKind;
	}
	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	public String getIsHidden() {
		return this.isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	public String getDatumUrl() {
		return this.datumUrl;
	}

	public void setDatumUrl(String datumUrl) {
		this.datumUrl = datumUrl;
	}
	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
}
