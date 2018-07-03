package com.cd.zj.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import com.cd.zj.entity.TblDatum;

public interface TblDatumRepository extends CrudRepository<TblDatum, Long> {

	Page<TblDatum> findAll(Specification<TblDatum> p,Pageable pa);
}
