package com.cd.zhks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cd.zhks.entity.Role;

import java.util.List;


public interface RoleDao extends JpaRepository<Role, Long>,JpaSpecificationExecutor<Role> {

    List<Role> findByRoleName(String roleName);

    List<Role> findByCreatedUserId(Long createUserId);
}