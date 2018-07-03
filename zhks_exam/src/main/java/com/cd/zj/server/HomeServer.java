package com.cd.zj.server;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cd.zj.bean.query.ScheduleQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.HomeResult;
import com.cd.zj.bean.result.Schedule;
import com.cd.zj.dao.HomeDao;

@Service
public class HomeServer {

	@Autowired
	private HomeDao homeDao;

	public List<Schedule> viewSchedule(ScheduleQuery type) {
		return homeDao.viewSchedule(type);
	}

	public List<CountResult> unitExam() {
		return homeDao.unitExam();
	}

	public List<HomeResult> passRate() {
		return homeDao.passRate();
	}

	public List<CountResult> historyScore(Long userId) {
		return homeDao.historyScore(userId);
	}

	public List<HomeResult> personPass(Long userId) {
		List<Timestamp> list = homeDao.findData(userId);
		List<HomeResult> count = new LinkedList<HomeResult>();
		for(Timestamp time:list){
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("time", time);
			HomeResult result=homeDao.personPass(map);
			count.add(result);
		}
		return count;
	}

}
