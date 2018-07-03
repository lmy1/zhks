package com.cd.zj.bean;

import java.util.List;

/**
 * 分页bean
 */

public class PageBean<T> {

	// 总条数
	private Long totalNum;
	// 分页结果
	private List<T> result;

	public PageBean() {
		super();
	}

	public PageBean(Long totalNum, List<T> result) {
		super();
		this.totalNum = totalNum;
		this.result = result;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
}
