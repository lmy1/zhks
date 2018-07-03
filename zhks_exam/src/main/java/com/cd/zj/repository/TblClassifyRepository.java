package com.cd.zj.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import com.cd.zj.entity.TblClassify;

public interface TblClassifyRepository extends CrudRepository<TblClassify, String> {

	TblClassify findByClassifyNameAndLevelsAndIsDelete(String name,String level,String isDelete);

	Page<TblClassify> findAll(Specification<TblClassify> p,Pageable pa);

	List<TblClassify> findByLevelsAndIsDeleteAndIsHidden(String levles,String isDelete,String isHidden);

	List<TblClassify> findByParentKeyAndIsDeleteAndIsHidden(String parentId,String isDelete,String isHidden);

	List<TblClassify> findByParentKeyAndIsDelete(String parseLong, String string);

	List<TblClassify> findByClassifyNameAndIsDeleteAndLevelsAndIsHidden(String classifyName, String string,String levles,String isHidden);

	List<TblClassify> findByParentKeyAndIsDeleteAndIsHiddenAndClassifyName(String parentId,String isDelete,String isHidden,String classifyName);

	List<TblClassify> findByLevelsAndIsDelete(String levles,String isDelete);
}
