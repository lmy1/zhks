package com.cd.uap.dao;

import com.cd.uap.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by li.mingyang on 2018/4/17.
 */
public interface RoleDao extends JpaRepository<Role, Long>,JpaSpecificationExecutor<Role> {
}
