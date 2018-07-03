package com.cd.zj.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import com.cd.zj.entity.TblReport;

public interface TblReportRepository extends CrudRepository<TblReport, Long> {

	Page<TblReport> findAll(Specification<TblReport> sp, Pageable page);

}
