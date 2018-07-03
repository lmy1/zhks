package com.cd.zj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cd.zj.bean.query.ExamQuerys;
import com.cd.zj.entity.TblExam;

@Mapper
public interface TblExamMapper {

	List<TblExam> findExamByUser(ExamQuerys examQuerys);

}
