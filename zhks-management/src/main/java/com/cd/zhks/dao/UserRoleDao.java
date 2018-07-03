package com.cd.zhks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cd.zhks.entity.UserRole;

public interface UserRoleDao extends JpaRepository<UserRole, Long>,JpaSpecificationExecutor<UserRole> {
    java.util.List<UserRole> findByUserId(Long userId);
            
    java.util.List<UserRole> findByRoleId(Long roleId);
            
}