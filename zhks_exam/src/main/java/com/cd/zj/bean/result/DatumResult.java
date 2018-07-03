package com.cd.zj.bean.result;

public class DatumResult {
	private String id;
	private String datumName;
	private String classifyKind;
	private String format;
	private String dataName;
	private String isHidden;
	private String datumUrl;
	private String isDelete;
	private String secondName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDatumName() {
		return datumName;
	}
	public void setDatumName(String datumName) {
		this.datumName = datumName;
	}
	public String getClassifyKind() {
		return classifyKind;
	}
	public void setClassifyKind(String classifyKind) {
		this.classifyKind = classifyKind;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	public String getDatumUrl() {
		return datumUrl;
	}
	public void setDatumUrl(String datumUrl) {
		this.datumUrl = datumUrl;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
}
