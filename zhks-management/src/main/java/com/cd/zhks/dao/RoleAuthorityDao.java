package com.cd.zhks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cd.zhks.entity.RoleAuthority;

public interface RoleAuthorityDao extends JpaRepository<RoleAuthority, Long>,JpaSpecificationExecutor<RoleAuthority> {
    java.util.List<RoleAuthority> findByRoleId(Long roleId);
            
    java.util.List<RoleAuthority> findByAuthorityId(Long authorityId);
            
}