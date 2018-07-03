package com.cd.zj.bean.query;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.cd.zj.entity.TblScore;
import com.cd.zj.entity.TblTopic;

public class ScoreQuery {
	@NotNull(message="1151")
	private Long id;
	@NotNull(message="1152")
	private Long userId;
	private Set<TblTopic> topic = new HashSet<TblTopic>();
	@Valid
	private TblScore tblScore;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public TblScore getTblScore() {
		return tblScore;
	}
	public void setTblScore(TblScore tblScore) {
		this.tblScore = tblScore;
	}
	public Set<TblTopic> getTopic() {
		return topic;
	}
	public void setTopic(Set<TblTopic> topic) {
		this.topic = topic;
	}
}
