package com.cd.zj.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import com.cd.zj.entity.TblExam;

public interface TblExamRepository extends CrudRepository<TblExam, Long> {

	Page<TblExam> findAll(Specification<TblExam> p,Pageable pa);
}
