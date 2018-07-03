package com.cd.zj.bean.result;


public class ClassifyResult {
	private String id;
	private String classifyName;
	private String levels;
	private Long parentKey;
	private String isHidden;
	private String isDelete;
	private String parentName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassifyName() {
		return classifyName;
	}
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	public Long getParentKey() {
		return parentKey;
	}
	public void setParentKey(Long parentKey) {
		this.parentKey = parentKey;
	}
	public String getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
