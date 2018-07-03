package com.cd.zj.dao;

import java.util.List;

import com.cd.zj.bean.query.ExamQuerys;
import com.cd.zj.entity.TblExam;

public interface TblExamDao {

	List<TblExam> findExamByUser(ExamQuerys examQuerys);

}
