package com.cd.uap.dao;

import com.cd.uap.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by li.mingyang on 2018/6/20.
 */
public interface UserRoleDao extends JpaRepository<UserRole, Long>,JpaSpecificationExecutor<UserRole> {
}
