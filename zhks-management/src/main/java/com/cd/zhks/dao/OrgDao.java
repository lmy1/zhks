package com.cd.zhks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cd.zhks.entity.Org;

public interface OrgDao extends JpaRepository<Org, Long>,JpaSpecificationExecutor<Org> {

	Org findByOrgCode(String orgCode) throws Exception;
}