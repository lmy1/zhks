package com.cd.zuul.ywdp.dao;


import com.cd.zuul.ywdp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {


}
