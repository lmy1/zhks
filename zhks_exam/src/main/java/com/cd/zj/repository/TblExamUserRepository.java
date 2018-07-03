package com.cd.zj.repository;

import org.springframework.data.repository.CrudRepository;

import com.cd.zj.entity.TblExam;
import com.cd.zj.entity.TblExamUser;

public interface TblExamUserRepository extends CrudRepository<TblExamUser, Long>{

	TblExamUser findByUserIdAndOrgCodeAndTblExam(Long userId,String orgCode,TblExam tblExam);

	TblExamUser findByUserIdAndTblExam(Long userId,TblExam tblExam);
}
