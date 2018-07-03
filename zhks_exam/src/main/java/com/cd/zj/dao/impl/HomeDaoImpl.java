package com.cd.zj.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cd.zj.bean.query.ScheduleQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.HomeResult;
import com.cd.zj.bean.result.Schedule;
import com.cd.zj.dao.HomeDao;
import com.cd.zj.mapper.HomeMapper;
@Component
public class HomeDaoImpl implements HomeDao {

	@Autowired
	private HomeMapper homeMapper;

	@Override
	public List<Schedule> viewSchedule(ScheduleQuery type) {
		return homeMapper.viewSchedule(type);
	}

	@Override
	public List<CountResult> unitExam() {
		return homeMapper.unitExam();
	}

	@Override
	public List<HomeResult> passRate() {
		return homeMapper.passRate();
	}

	@Override
	public List<CountResult> historyScore(Long userId) {
		return homeMapper.historyScore(userId);
	}

	@Override
	public List<Timestamp> findData(Long userId) {
		return homeMapper.findData(userId);
	}

	@Override
	public HomeResult personPass(Map<String, Object> map) {
		return homeMapper.personPass(map);
	}

}
