package com.cd.zj.bean.result;

import java.util.Date;


public class ExamResult {
	private Long id;
	private String examName;
	private String examType;
	private String examModel;
	private Date startTime;
	private Date endTime;
	private Long total;
	private Double totalScore;
	private String solveType;
	private Long passScore;
	private Long excelScore;
	private Long answerTime;
	private String levelFirst;
	private String isDelete;
	private String isEnable;
	private String firstName;
	private Date createTime;
	private Double singleScore;
	private Double checkBoxScore;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getExamModel() {
		return examModel;
	}
	public void setExamModel(String examModel) {
		this.examModel = examModel;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}
	public String getSolveType() {
		return solveType;
	}
	public void setSolveType(String solveType) {
		this.solveType = solveType;
	}
	public Long getPassScore() {
		return passScore;
	}
	public void setPassScore(Long passScore) {
		this.passScore = passScore;
	}
	public Long getExcelScore() {
		return excelScore;
	}
	public void setExcelScore(Long excelScore) {
		this.excelScore = excelScore;
	}
	public Long getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Long answerTime) {
		this.answerTime = answerTime;
	}
	public String getLevelFirst() {
		return levelFirst;
	}
	public void setLevelFirst(String levelFirst) {
		this.levelFirst = levelFirst;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Double getSingleScore() {
		return singleScore;
	}
	public void setSingleScore(Double singleScore) {
		this.singleScore = singleScore;
	}
	public Double getCheckBoxScore() {
		return checkBoxScore;
	}
	public void setCheckBoxScore(Double checkBoxScore) {
		this.checkBoxScore = checkBoxScore;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
