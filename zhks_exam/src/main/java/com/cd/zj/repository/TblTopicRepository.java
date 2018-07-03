package com.cd.zj.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import com.cd.zj.entity.TblTopic;

public interface TblTopicRepository extends CrudRepository<TblTopic, Long>{

	Page<TblTopic> findAll(Specification<TblTopic> s,Pageable p);

	List<TblTopic> findByClassifyKindAndIsDeleteAndTopicType(String classifyKind,String isDelete,String topicType);
}
