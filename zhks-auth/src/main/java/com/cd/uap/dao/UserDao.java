package com.cd.uap.dao;

import com.cd.uap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserDao extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
	
	public User findByIdCard(String idCard);

}
