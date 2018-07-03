package com.cd.zj.bean.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TrainQuery {

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private Date startTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private Date endTime;
	private Long person;
	private Integer page;
	private Integer size;
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Long getPerson() {
		return person;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setPerson(Long person) {
		this.person = person;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}
