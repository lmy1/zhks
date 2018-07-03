package com.cd.zhks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cd.zhks.entity.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
    boolean existsByTelephone(String Telephone);

    boolean existsByTelephoneAndIdNot(String Telephone, Long id);
    boolean existsByIdCard(String IdCard);

    boolean existsByIdCardAndIdNot(String IdCard, Long id);

    //List<User> findByOrgAndId(Long id);
    List<User> findAllByOrg_Id(Long id);
}