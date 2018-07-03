package com.cd.zhks.bean.query;

public abstract class AbstarctQuery {
	//当前页数,第一页则传入1
	private int page;

	//每页展示数
	private int size;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}