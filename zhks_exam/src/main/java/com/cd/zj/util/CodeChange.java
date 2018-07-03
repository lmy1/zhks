package com.cd.zj.util;


import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="codeChange")
@PropertySource(value="classpath:codeChange.properties",encoding = "utf-8")
public class CodeChange {

	private Map<String, String> classifyType;
	private Map<String, String> isDelete;
	private Map<String, String> level;
	private Map<String, String> videoType;
	private Map<String, String> examType;

	public Map<String, String> getClassifyType() {
		return classifyType;
	}

	public void setClassifyType(Map<String, String> classifyType) {
		this.classifyType = classifyType;
	}

	public Map<String, String> getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Map<String, String> isDelete) {
		this.isDelete = isDelete;
	}

	public Map<String, String> getLevel() {
		return level;
	}

	public void setLevel(Map<String, String> level) {
		this.level = level;
	}

	public Map<String, String> getVideoType() {
		return videoType;
	}

	public void setVideoType(Map<String, String> videoType) {
		this.videoType = videoType;
	}

	public Map<String, String> getExamType() {
		return examType;
	}

	public void setExamType(Map<String, String> examType) {
		this.examType = examType;
	}
}
