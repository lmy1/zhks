package com.cd.zj.bean.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TopicQuery {

	private String name;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private Date startTime;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	private Date endTime;
	private String kind;
	private String person;
	private String topicType;
	private Integer page;
	private Integer size;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getPerson() {
		return person;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setPerson(String person) {
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
	public String getTopicType() {
		return topicType;
	}
	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}
}
