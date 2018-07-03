package com.cd.zhks.bean.dto;

import io.swagger.annotations.ApiModelProperty;

public class ReviewStatusDto {
	
	@ApiModelProperty("是否审核(0未审核，1通过，2未通过)")
	 private Long reviewStatus;
	
	@ApiModelProperty("未通过原因")
	private String unreviewReason;

	public Long getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Long reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getUnreviewReason() {
		return unreviewReason;
	}

	public void setUnreviewReason(String unreviewReason) {
		this.unreviewReason = unreviewReason;
	}

	
	
}
