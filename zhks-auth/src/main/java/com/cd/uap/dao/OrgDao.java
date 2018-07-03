package com.cd.uap.dao;

import com.cd.uap.entity.Org;
import com.cd.uap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface OrgDao extends JpaRepository<Org, Long>,JpaSpecificationExecutor<Org> {
	
	public Org findByOrgCode(String orgCode);

}
