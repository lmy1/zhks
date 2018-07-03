package com.cd.zhks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cd.zhks.entity.Areainfo;

public interface AreainfoDao extends JpaRepository<Areainfo, Long>,JpaSpecificationExecutor<Areainfo> {
}