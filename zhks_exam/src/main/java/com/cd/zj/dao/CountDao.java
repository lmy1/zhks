package com.cd.zj.dao;

import java.util.List;
import java.util.Map;

import com.cd.zj.bean.query.ReportQuery;
import com.cd.zj.bean.query.TrainQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.CountTrain;
import com.cd.zj.bean.result.ReportResult;
import com.cd.zj.bean.result.ScoreResult;
import com.cd.zj.bean.result.TrainResult;

public interface CountDao {

	List<CountResult> findCompanyPx(Long id);

	List<CountResult> personSorce(Map<String, Object> map);

	List<CountTrain> countTrain(Map<String, Object> map);

	List<TrainResult> queryTrain(TrainQuery trainQuery);

	List<ScoreResult> queryScore(TrainQuery trainQuery);

	List<ReportResult> queryReport(ReportQuery reportQuery);

	Long findNum(Long id);

	Long countNum(Long id);

	Long countExamNum(Long id);

}
