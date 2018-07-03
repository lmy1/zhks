package com.cd.zj.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.query.ClassIfyQuery;
import com.cd.zj.bean.result.ClassifyResult;
import com.cd.zj.dao.TblClassifyDao;
import com.cd.zj.entity.TblClassify;
import com.cd.zj.repository.TblClassifyRepository;
import com.cd.zj.util.CodeChange;
import com.cd.zj.util.CommonUtil;

@Service
public class ClassifyServer {

	@Autowired
	private TblClassifyRepository tblClassifyRepository;

	@Autowired
	private CodeChange codeChange;

	@Autowired
	private TblClassifyDao tblClassifyDao;

	/**
	 * 医科分类新增
	 * @param tblClassify
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public String addClassify(TblClassify tblClassify, BindingResult bindResult) throws Exception {
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		//查看是否已存在该医科分类
		TblClassify findByClassifyNameAndLevelsAndIsDelete = tblClassifyRepository.findByClassifyNameAndLevelsAndIsDelete(tblClassify.getClassifyName(), tblClassify.getLevels(),codeChange.getIsDelete().get("notDelete"));
		if(findByClassifyNameAndLevelsAndIsDelete!=null){
			throw new Exception("2001");
		}
		//设为未删除
		tblClassify.setIsDelete(codeChange.getIsDelete().get("notDelete"));
		tblClassifyRepository.save(tblClassify);
		return tblClassify.getId();
	}

	/**
	 * 医科分类修改
	 * @param tblClassify
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public String editClassify(TblClassify tblClassify, BindingResult bindResult) throws Exception {
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		int usered = isUsered(tblClassify.getId());
		if(usered>0){
			throw new Exception("1160");
		}
		tblClassify.setIsDelete(codeChange.getIsDelete().get("notDelete"));
		tblClassifyRepository.save(tblClassify);
		return tblClassify.getId();
	}

	/**
	 * 医科分类删除
	 * @param id
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteClassify(String id) throws Exception{
		List<TblClassify> list=tblClassifyRepository.findByParentKeyAndIsDelete(id, codeChange.getIsDelete().get("notDelete"));
		if(list!=null&&list.size()>0){
			throw new Exception("1005");
		}
		int usered = isUsered(id);
		if(usered>0){
			throw new Exception("1153");
		}
		TblClassify findOne = tblClassifyRepository.findOne(id);
		findOne.setIsDelete(codeChange.getIsDelete().get("delete"));
		tblClassifyRepository.save(findOne);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteClassifys(String ids) throws Exception{
		String id[]=ids.split(",");
		for(String i:id){
			int usered = isUsered(i);
			if(usered>0){
				throw new Exception("1153");
			}
			List<TblClassify> list=tblClassifyRepository.findByParentKeyAndIsDelete(i, codeChange.getIsDelete().get("notDelete"));
			if(list!=null&&list.size()>0){
				throw new Exception("1005");
			}
			TblClassify findOne = tblClassifyRepository.findOne(i);
			findOne.setIsDelete(codeChange.getIsDelete().get("delete"));
			tblClassifyRepository.save(findOne);
		}
	}

	/**
	 * 查看医科分类
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TblClassify viewClassify(String id) throws Exception{
		TblClassify findOne = tblClassifyRepository.findOne(id);
		return findOne;
	}

	/**
	 * 列表查询
	 * @param classIfyQuery
	 * @return
	 */
	public PageBean<ClassifyResult> queryClassify(ClassIfyQuery classIfyQuery) throws Exception{
		PageBean<ClassifyResult> pageBean = new PageBean<>();

		// 创建条件查询对象
		Specification<TblClassify> specification = new Specification<TblClassify>() {
			@Override
			public Predicate toPredicate(Root<TblClassify> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(classIfyQuery.getName())){
					Predicate predicate=cb.like(root.get("classifyName"), "%"+classIfyQuery.getName()+"%");
					predicateList.add(predicate);
				}
				if(StringUtils.isNotBlank(classIfyQuery.getLevel())){
					Predicate predicate=cb.equal(root.get("levels"), classIfyQuery.getLevel());
					predicateList.add(predicate);
				}
				if(StringUtils.isNotBlank(classIfyQuery.getParentKey())){
					Predicate predicate=cb.equal(root.get("parentKey"), classIfyQuery.getParentKey());
					predicateList.add(predicate);
				}
				Predicate predicate=cb.equal(root.get("isDelete"), codeChange.getIsDelete().get("notDelete"));
				predicateList.add(predicate);
				// 将条件集合转换为数组并返回
				Predicate[] p = new Predicate[predicateList.size()];
				return cb.and(predicateList.toArray(p));
			}
		};
		//排序
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		//分页
		PageRequest pageRequest = null;
		if(classIfyQuery.getPage()!=null && classIfyQuery.getSize()!=null){
			pageRequest = new PageRequest(classIfyQuery.getPage()-1, classIfyQuery.getSize(), sort);
		}else{
			throw new Exception("2002");
		}
		Page<TblClassify> findAll = tblClassifyRepository.findAll(specification, pageRequest);
		//转化其中值域代码
		List<ClassifyResult> content = new ArrayList<ClassifyResult>();
		if(findAll.getContent()!=null&&findAll.getContent().size()>0){
			for(TblClassify t:findAll.getContent()){
				ClassifyResult classify = new ClassifyResult();
				BeanUtils.copyProperties(classify, t);
				classify.setLevels(codeChange.getLevel().get(t.getLevels()));
				classify.setParentName(t.getParentKey()==null?"":tblClassifyRepository.findOne(t.getParentKey()).getClassifyName());
				content.add(classify);
			}
		}
		pageBean=new PageBean<ClassifyResult>(findAll.getTotalElements(), content);
		return pageBean;
	}

	/**
	 * 医科分类级联下拉
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> relateClassify() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("delete", codeChange.getIsDelete().get("notDelete"));
		map.put("hidden", "0");
		List<Map<String, Object>> mapper = tblClassifyDao.firstLevelClassify(map);
		for(int x=0;x<mapper.size();x++){
			map.clear();
			map.put("delete", codeChange.getIsDelete().get("notDelete"));
			map.put("hidden", "0");
			map.put("parentId", mapper.get(x).get("value")+"");
			List<Map<String, Object>> mappe = tblClassifyDao.secondLevelClassify(map);
			mapper.get(x).put("children", mappe);
		}
		return mapper;
	}

	/**
	 * 查询一级分类列表
	 * @return
	 * @throws Exception
	 */
	public List<TblClassify> firstClassify() throws Exception{
		List<TblClassify> findByLevelsAndIsDeleteAndIsHidden = tblClassifyRepository.findByLevelsAndIsDeleteAndIsHidden("1", codeChange.getIsDelete().get("notDelete"),"0");
		return findByLevelsAndIsDeleteAndIsHidden;
	}

	/**
	 * 查询二级分类列表
	 * @param id
	 * @return
	 */
	public List<TblClassify> secondClassify(String id) throws Exception{
		List<TblClassify> findByParentIdAndIsDeleteAndIsHidden = tblClassifyRepository.findByParentKeyAndIsDeleteAndIsHidden(id, codeChange.getIsDelete().get("notDelete"), "0");
		return findByParentIdAndIsDeleteAndIsHidden;
	}

	/**
	 * 显示隐藏医科分类
	 * @param id
	 * @param type
	 */
	@Transactional(rollbackFor=Exception.class)
	public void startClassify(String id, String type) throws Exception{
		int usered = isUsered(id);
		if(usered>0){
			throw new Exception("1154");
		}
		TblClassify findOne = tblClassifyRepository.findOne(id);
		findOne.setIsHidden(type);
		tblClassifyRepository.save(findOne);
	}

	/**
	 * 校验改医科分类是否被使用过
	 */
	private int isUsered(String id){
		return tblClassifyDao.isUsed(id);
	}

	public List<TblClassify> firstClassifys() {
		List<TblClassify> findByLevelsAndIsDeleteAndIsHidden = tblClassifyRepository.findByLevelsAndIsDelete("1", codeChange.getIsDelete().get("notDelete"));
		return findByLevelsAndIsDeleteAndIsHidden;
	}
}
