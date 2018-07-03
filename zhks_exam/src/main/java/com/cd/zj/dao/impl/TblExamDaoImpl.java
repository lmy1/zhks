package com.cd.zj.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cd.zj.bean.query.ExamQuerys;
import com.cd.zj.dao.TblExamDao;
import com.cd.zj.entity.TblExam;
import com.cd.zj.mapper.TblExamMapper;

@Component
public class TblExamDaoImpl implements TblExamDao {

	@Autowired
	private TblExamMapper tblExamMapper;

	@Override
	public List<TblExam> findExamByUser(ExamQuerys examQuerys) {
		return tblExamMapper.findExamByUser(examQuerys);
	}
}
