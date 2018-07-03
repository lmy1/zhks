package com.cd.zj.repository;

import org.springframework.data.repository.CrudRepository;

import com.cd.zj.entity.TblDatum;
import com.cd.zj.entity.TblTrain;

public interface TblTrainRepository extends CrudRepository<TblTrain, Long>{

	TblTrain findByTblDatumAndUserId(TblDatum id, Long userId);
}
