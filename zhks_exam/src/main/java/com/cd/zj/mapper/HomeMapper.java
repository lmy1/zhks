package com.cd.zj.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cd.zj.bean.query.ScheduleQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.HomeResult;
import com.cd.zj.bean.result.Schedule;

@Mapper
public interface HomeMapper {

	List<Schedule> viewSchedule(ScheduleQuery type);

	List<CountResult> unitExam();

	List<HomeResult> passRate();

	List<CountResult> historyScore(@Param("userId")Long userId);

	List<Timestamp> findData(@Param("userId")Long userId);

	HomeResult personPass(Map<String, Object> map);

}
