package com.cd.zhks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cd.zhks.entity.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Long>,JpaSpecificationExecutor<Authority> {
}