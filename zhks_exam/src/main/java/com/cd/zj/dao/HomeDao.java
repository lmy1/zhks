package com.cd.zj.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.cd.zj.bean.query.ScheduleQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.HomeResult;
import com.cd.zj.bean.result.Schedule;

public interface HomeDao {

	List<Schedule> viewSchedule(ScheduleQuery type);

	List<CountResult> unitExam();

	List<HomeResult> passRate();

	List<CountResult> historyScore(Long userId);

	List<Timestamp> findData(Long userId);

	HomeResult personPass(Map<String, Object> map);

}
