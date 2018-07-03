package com.cd.zj.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.query.DatumQuery;
import com.cd.zj.bean.query.GetDatum;
import com.cd.zj.bean.result.DatumResult;
import com.cd.zj.entity.TblDatum;
import com.cd.zj.entity.TblTopic;
import com.cd.zj.entity.TblTrain;
import com.cd.zj.repository.TblClassifyRepository;
import com.cd.zj.repository.TblDatumRepository;
import com.cd.zj.repository.TblTrainRepository;
import com.cd.zj.util.CodeChange;
import com.cd.zj.util.CommonUtil;
import com.cd.zj.util.ftp.FTPclient;

@Service
public class DatumServer {

	@Autowired
	private FTPclient fTPclient;

	@Value("${WordBriefBasePath}")
	private String WordBriefBasePath;

	@Autowired
	private CodeChange codeChange;

	@Autowired
	private TblDatumRepository tblDatumRepository;

	@Autowired
	private TblClassifyRepository tblClassifyRepository;

	@Autowired
	private TblTrainRepository tblTrainRepository;

	/**
	 * 新增资料
	 * @param tblDatum
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long addDatum(GetDatum tblDatum, BindingResult bindResult) throws Exception {
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		String name =tblDatum.getDataName();
		if(StringUtils.isBlank(codeChange.getVideoType().get(tblDatum.getFormat().toUpperCase()))){
			throw new Exception("1004");
		}
		if(!tblDatum.getFormat().equalsIgnoreCase(name.split("\\.")[name.split("\\.").length-1])){
			throw new Exception("1004");
		}
		String uuid = UUID.randomUUID().toString();
		//将资料放入ftp服务器，地址保存进数据库
		fTPclient.uploadFile(WordBriefBasePath, new String(uuid.getBytes("UTF-8"), "UTF-8")+"."+tblDatum.getFormat(), tblDatum.getFile().getInputStream());
		tblDatum.setIsDelete(codeChange.getIsDelete().get("notDelete"));
		tblDatum.setDatumUrl(uuid+"."+tblDatum.getFormat());
		TblDatum tbl =new TblDatum();
		BeanUtils.copyProperties(tbl, tblDatum);
		tbl.setDataName(name);
		tblDatumRepository.save(tbl);
		return tbl.getId();
	}

	/**
	 * 修改资料
	 * @param tblDatum
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long editDatum(GetDatum tblDatum, BindingResult bindResult) throws Exception {
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotEmpty(msg)&&!("null".equals(msg.trim()))){
			throw new Exception(msg);
		}
		if(StringUtils.isBlank(codeChange.getVideoType().get(tblDatum.getFormat()))){
			throw new Exception("1004");
		}
		TblDatum findOne = tblDatumRepository.findOne(tblDatum.getId());
		//若资料关联的有题目 判断
		TblDatum tbl =new TblDatum();
		if(tblDatum.getFile()!=null){
			String name =tblDatum.getDataName();
			if(!tblDatum.getFormat().equalsIgnoreCase(name.split("\\.")[name.split("\\.").length-1])){
				throw new Exception("1004");
			}
			//将以前ftp上资料删除
			fTPclient.delete(new String((WordBriefBasePath+findOne.getDatumUrl()).getBytes("UTF-8"), "UTF-8"));
			String uuid = UUID.randomUUID().toString();
			//将资料放入ftp服务器，地址保存进数据库
			fTPclient.uploadFile(WordBriefBasePath, new String(uuid.getBytes("UTF-8"), "UTF-8")+"."+tblDatum.getFormat(), tblDatum.getFile().getInputStream());
			tblDatum.setDatumUrl(uuid+"."+tblDatum.getFormat());
			tblDatum.setIsDelete(codeChange.getIsDelete().get("notDelete"));
			BeanUtils.copyProperties(tbl, tblDatum);
			tbl.setDataName(name);
		}else{
			if(!tblDatum.getFormat().equalsIgnoreCase(findOne.getDataName().split("\\.")[findOne.getDataName().split("\\.").length-1])){
				throw new Exception("1004");
			}
			BeanUtils.copyProperties(tbl, tblDatum);
			tbl.setIsDelete(findOne.getIsDelete());
			tbl.setDatumUrl(findOne.getDatumUrl());
			tbl.setDataName(findOne.getDataName());
		}
		if(!tblDatum.getClassifyKind().equals(findOne.getClassifyKind())){
			tbl.setTblTopic(null);
		}else{
			tbl.setTblTopic(findOne.getTblTopic());
		}
		tblDatumRepository.save(tbl);
		return tbl.getId();
	}

	/**
	 * 查看资料
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TblDatum viewDatum(Long id) throws Exception{
		return tblDatumRepository.findOne(id);
	}

	/**
	 * 删除资料
	 * @param id
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteDatum(Long id) throws Exception{
		TblDatum findOne = tblDatumRepository.findOne(id);
		findOne.setIsDelete(codeChange.getIsDelete().get("delete"));
		tblDatumRepository.save(findOne);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteDatums(String ids) throws Exception{
		String id[]=ids.split(",");
		for(String i:id){
			TblDatum findOne = tblDatumRepository.findOne(Long.parseLong(i));
			findOne.setIsDelete(codeChange.getIsDelete().get("delete"));
			tblDatumRepository.save(findOne);
		}
	}

	/**
	 * 查询资料列表
	 * @param datumQuery
	 * @return
	 */
	public PageBean<DatumResult> queryDatum(DatumQuery datumQuery) throws Exception{
		PageBean<DatumResult> pageBean=null;
		//查询条件
		Specification<TblDatum> specification = new Specification<TblDatum>() {
			
			@Override
			public Predicate toPredicate(Root<TblDatum> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(datumQuery.getKind())){
					Predicate p = cb.like(root.get("classifyKind"), "%"+datumQuery.getKind()+"%");
					predicate.add(p);
				}
				if(StringUtils.isNotBlank(datumQuery.getName())){
					Predicate p = cb.like(root.get("datumName"), "%"+datumQuery.getName()+"%");
					predicate.add(p);
				}
				if(StringUtils.isNotBlank(datumQuery.getIsHidden())){
					Predicate p = cb.equal(root.get("isHidden"),datumQuery.getIsHidden());
					predicate.add(p);
				}
				Predicate p = cb.equal(root.get("isDelete"), codeChange.getIsDelete().get("notDelete"));
				predicate.add(p);
				// 将条件集合转换为数组并返回
				Predicate[] pre = new Predicate[predicate.size()];
				return cb.and(predicate.toArray(pre));
			}
		};
		Sort sort =new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = new PageRequest(datumQuery.getPage()-1, datumQuery.getSize(), sort);
		Page<TblDatum> findAll = tblDatumRepository.findAll(specification, pageable);
		List<TblDatum> content = findAll.getContent();
		List<DatumResult> result = new ArrayList<DatumResult>();
		if(content.size()>0&&content!=null){
			for(int x=0;x<content.size();x++){
				DatumResult dat = new DatumResult();
				BeanUtils.copyProperties(dat, content.get(x));
				String[] split = content.get(x).getClassifyKind().split(",");
				dat.setSecondName(tblClassifyRepository.findOne(split[split.length-1]).getClassifyName());
				result.add(dat);
			}
		}
		pageBean = new PageBean<DatumResult>(findAll.getTotalElements(), result);
		return pageBean;
	}

	/**
	 * 启用或禁用资料
	 * @param id
	 * @param type
	 */
	@Transactional(rollbackFor=Exception.class)
	public void startClassify(Long id, String type) throws Exception{
		TblDatum findOne = tblDatumRepository.findOne(id);
		findOne.setIsHidden(type);
		tblDatumRepository.save(findOne);
	}

	/**
	 * 保存联系
	 * @param tblDatum
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addDatumTopic(TblDatum tblDatum) throws Exception{
		Set<TblTopic> tblTopic = tblDatum.getTblTopic();
		if(tblTopic.size()!=5){
			throw new Exception("1155");
		}
		TblDatum findOne = tblDatumRepository.findOne(tblDatum.getId());
		findOne.setTblTopic(tblTopic);
		tblDatumRepository.save(findOne);
	}

	/**
	 * 查看题目
	 * @param id
	 * @return
	 */
	public TblDatum viewDatumTopic(Long id) {
		return tblDatumRepository.findOne(id);
	}

	/**
	 * 记录培训情况
	 * @param tblTrain
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long addTrain(TblTrain tblTrain) {
		tblTrainRepository.save(tblTrain);
		return tblTrain.getId();
	}

	/**
	 * 下载资料
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	public void downDatum(Long id, HttpServletResponse response) throws Exception {
		TblDatum findOne = tblDatumRepository.findOne(id);
		response.setContentType("multipart/form-data;charset=UTF-8");
		System.out.println(findOne.getDatumName()+"."+findOne.getFormat());
		response.addHeader("Content-Disposition","attachment; filename="+ new String((findOne.getDataName()).getBytes(),"ISO-8859-1"));
		fTPclient.downloadFiles(WordBriefBasePath+findOne.getDatumUrl(), response.getOutputStream());
	}

	/**
	 * 开始培训 如果是视频 查询是否看过 并且返回给前台
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> doDatum(Long id, Long userId) throws Exception{
		Map<String, Object> map =new HashMap<String, Object>();
		TblDatum findOne = tblDatumRepository.findOne(id);
		TblTrain findByTblDatumAndUserId = tblTrainRepository.findByTblDatumAndUserId(findOne, userId);
		if(findByTblDatumAndUserId==null){
			map.put("tblDatum", findOne);
			map.put("train", 0);
		}else{
			map.put("tblDatum", findOne);
			map.put("train", findByTblDatumAndUserId);
		}
		return map;
	}
}
