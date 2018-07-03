package com.cd.zj.bean.result;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.cd.zj.entity.TblExamTopicnum;
import com.cd.zj.entity.TblExamUser;

public class TblExamResult {
	private Long id;
	private String examName;
	private String examType;
	private String examModel;
	private Date startTime;
	private Date endTime;
	private Long total;
	private Double totalScore;
	private Double singleScore;
	private Double checkBoxScore;
	private String solveType;
	private Long passScore;
	private Long excelScore;
	private Long answerTime;
	private String levelFirst;
	private String isDelete;
	private String isEnable;
	private Date createTime;
	private String name;
	private Long singleTotal;
	private Long multiTotal;
	private Set<TblExamUser> tblExamUser = new HashSet<TblExamUser>();
	public Long getSingleTotal() {
		return singleTotal;
	}
	public void setSingleTotal(Long singleTotal) {
		this.singleTotal = singleTotal;
	}
	public Long getMultiTotal() {
		return multiTotal;
	}
	public void setMultiTotal(Long multiTotal) {
		this.multiTotal = multiTotal;
	}
	private Set<TblExamTopicnum> tblExamTopicnum = new HashSet<TblExamTopicnum>();
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
	public void setTotalScore(Double score) {
		this.totalScore = score;
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
	public Set<TblExamTopicnum> getTblExamTopicnum() {
		return tblExamTopicnum;
	}
	public void setTblExamTopicnum(Set<TblExamTopicnum> tblExamTopicnum) {
		this.tblExamTopicnum = tblExamTopicnum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSingleScore() {
		return singleScore;
	}
	public Set<TblExamUser> getTblExamUser() {
		return tblExamUser;
	}
	public void setTblExamUser(Set<TblExamUser> tblExamUser) {
		this.tblExamUser = tblExamUser;
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
}
