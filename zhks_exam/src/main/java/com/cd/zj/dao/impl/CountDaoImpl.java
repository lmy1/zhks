package com.cd.zj.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cd.zj.bean.query.ReportQuery;
import com.cd.zj.bean.query.TrainQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.CountTrain;
import com.cd.zj.bean.result.ReportResult;
import com.cd.zj.bean.result.ScoreResult;
import com.cd.zj.bean.result.TrainResult;
import com.cd.zj.dao.CountDao;
import com.cd.zj.mapper.CountMapper;
@Component
public class CountDaoImpl implements CountDao {

	@Autowired
	private CountMapper countMapper;

	@Override
	public List<CountResult> findCompanyPx(Long id) {
		return countMapper.findCompanyPx(id);
	}

	@Override
	public List<CountResult> personSorce(Map<String, Object> map) {
		return countMapper.personSorce(map);
	}

	@Override
	public List<CountTrain> countTrain(Map<String, Object> map) {
		return countMapper.countTrain(map);
	}

	@Override
	public List<TrainResult> queryTrain(TrainQuery trainQuery) {
		return countMapper.queryTrain(trainQuery);
	}

	@Override
	public List<ScoreResult> queryScore(TrainQuery trainQuery) {
		return countMapper.queryScore(trainQuery);
	}

	@Override
	public List<ReportResult> queryReport(ReportQuery reportQuery) {
		return countMapper.queryReport(reportQuery);
	}

	@Override
	public Long findNum(Long id) {
		return countMapper.findNum(id);
	}

	@Override
	public Long countNum(Long id) {
		return countMapper.countNum(id);
	}

	@Override
	public Long countExamNum(Long id) {
		return countMapper.countExamNum(id);
	}

}
