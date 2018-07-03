package com.cd.zj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cd.zj.bean.query.ReportQuery;
import com.cd.zj.bean.query.TrainQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.CountTrain;
import com.cd.zj.bean.result.ReportResult;
import com.cd.zj.bean.result.ScoreResult;
import com.cd.zj.bean.result.TrainResult;

@Mapper
public interface CountMapper {

	List<CountResult> findCompanyPx(@Param("id")Long id);

	List<CountResult> personSorce(Map<String, Object> map);

	List<CountTrain> countTrain(Map<String, Object> map);

	List<TrainResult> queryTrain(TrainQuery trainQuery);

	List<ScoreResult> queryScore(TrainQuery trainQuery);

	List<ReportResult> queryReport(ReportQuery reportQuery);

	Long findNum(@Param("id")Long id);

	Long countNum(@Param("id")Long id);

	Long countExamNum(@Param("id") Long id);

}
