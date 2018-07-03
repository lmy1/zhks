package com.cd.zj.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.query.ExamQuery;
import com.cd.zj.bean.query.ExamQuerys;
import com.cd.zj.bean.query.ScoreQuery;
import com.cd.zj.bean.result.ExamResult;
import com.cd.zj.bean.result.TblExamResult;
import com.cd.zj.dao.TblExamDao;
import com.cd.zj.dao.TblTopicDao;
import com.cd.zj.entity.TblExam;
import com.cd.zj.entity.TblExamTopicnum;
import com.cd.zj.entity.TblExamUser;
import com.cd.zj.entity.TblScore;
import com.cd.zj.entity.TblTopic;
import com.cd.zj.repository.TblClassifyRepository;
import com.cd.zj.repository.TblExamRepository;
import com.cd.zj.repository.TblExamTopicRepository;
import com.cd.zj.repository.TblExamUserRepository;
import com.cd.zj.repository.TblScoreRepository;
import com.cd.zj.repository.TblTopicRepository;
import com.cd.zj.util.CodeChange;
import com.cd.zj.util.CommonUtil;
import com.github.pagehelper.PageHelper;

@Service
public class ExamServer {

	@Autowired
	private TblExamRepository tblExamRepository;

	@Autowired
	private TblExamTopicRepository tblExamTopicRepository;

	@Autowired
	private CodeChange codeChange;

	@Autowired
	private TblTopicDao tblTopicDao;

	@Autowired
	private TblExamUserRepository tblExamUserRepository;

	@Autowired
	private TblClassifyRepository tblClassifyRepository;

	@Autowired
	private TblExamDao tblExamDao;

	@Autowired
	private TblTopicRepository tblTopicRepository;

	@Autowired
	private TblScoreRepository tblScoreRepository;

	@Autowired
	private TopicServer topicServer;

	/**
	 * 新增试卷
	 * @param tblExam
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long addExam(TblExam tblExam, BindingResult bindResult) throws Exception {
		Set<TblExamTopicnum> tblExamTopicnum = tblExam.getTblExamTopicnum();
		checkExam(tblExam, bindResult, tblExamTopicnum);
		tblExam.setIsDelete(codeChange.getIsDelete().get("notDelete"));
		tblExam.setCreateTime(new Date());
		tblExam.setTblExamTopicnum(null);
		tblExam.setTblExamUser(null);
		saveExam(tblExam, tblExamTopicnum);
		return tblExam.getId();
	}

	/**
	 * 修改 试卷
	 * @param tblExam
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long aditExam(TblExam tblExam, BindingResult bindResult) throws Exception {
		Set<TblExamTopicnum> tblExamTopicnum = tblExam.getTblExamTopicnum();
		tblExam.setIsDelete(codeChange.getIsDelete().get("notDelete"));
		checkExam(tblExam, bindResult, tblExamTopicnum);
		tblExam.setCreateTime(new Date());
		tblExam.setTblExamTopicnum(null);
		//删除旧的 题目数量
		TblExam findOne = tblExamRepository.findOne(tblExam.getId());
		tblExam.setTblExamUser(findOne.getTblExamUser());
		for(TblExamTopicnum top:findOne.getTblExamTopicnum()){
			tblExamTopicRepository.delete(top);
		}
		saveExam(tblExam, tblExamTopicnum);
		return tblExam.getId();
	}

	/**
	 * 校验试卷
	 * @param tblExam
	 * @param bindResult
	 * @param tblExamTopicnum
	 * @throws Exception
	 */
	private void checkExam(TblExam tblExam, BindingResult bindResult,Set<TblExamTopicnum> tblExamTopicnum) throws Exception{
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		//各个题目数量不能为空
		if(tblExamTopicnum==null||tblExamTopicnum.size()==0){
			throw new Exception("1127");
		}
		int total=0;
		double score=0;
		//题目总量相等
		for(TblExamTopicnum t:tblExamTopicnum){
			total+=t.getTopicNum();
			if("1".equalsIgnoreCase(t.getTopicType())){
				score+=tblExam.getCheckBoxScore()*t.getTopicNum();
			}else{
				score+=tblExam.getSingleScore()*t.getTopicNum();
			}
		}
		tblExam.setTotalScore(score);
		if(tblExam.getExcelScore()>=score){
			throw new Exception("试卷总分为"+score+"优秀成绩应小于总分");
		}
		if(tblExam.getTotal()!=total){
			throw new Exception("1129");
		}
	}

	/**
	 * 保存试卷
	 * @param tblExam
	 * @param tblExamTopicnum
	 */
	private void saveExam(TblExam tblExam, Set<TblExamTopicnum> tblExamTopicnum)throws Exception{
		//随机题
		if("0".equals(tblExam.getExamType())){
			tblExamRepository.save(tblExam);
			for(TblExamTopicnum t:tblExamTopicnum){
				t.setTblExam(tblExam);
				t.setTblTopic(null);
				t.setId(null);
				tblExamTopicRepository.save(t);
			}
			// 固定体
		}else if("1".equals(tblExam.getExamType())){
			tblExamRepository.save(tblExam);
			for(TblExamTopicnum t:tblExamTopicnum){
				t.setTblExam(tblExam);
				t.setId(null);
				//手动抽题方式
				if("1".equals(tblExam.getSolveType())){
					tblExamTopicRepository.save(t);
				}else{//自动抽题方式
					Map<String, Object> map = new HashMap<String, Object>();
					if(tblExam.getLevelFirst().equals(t.getLevelSecond())){
						map.put("kind", t.getLevelSecond());
						map.put("topicType", t.getTopicType());
					}else{
						map.put("kind", tblExam.getLevelFirst()+","+t.getLevelSecond());
						map.put("topicType", t.getTopicType());
					}
					map.put("number", t.getTopicNum());
					map.put("isDelete", codeChange.getIsDelete().get("notDelete"));
					List<TblTopic> list = tblTopicDao.findTopic(map);
					Set<TblTopic> result = new HashSet<TblTopic>(list);
					t.setTblTopic(result);
					tblExamTopicRepository.save(t);
				}
			}
		}
	}

	/**
	 * 查看试卷
	 * @param id
	 * @return
	 */
	public TblExamResult viewExam(Long id)throws Exception {
		TblExam findOne = tblExamRepository.findOne(id);
		Long setNum_01 = setNum_01(findOne);
		findOne.setTotal(setNum_01);
		TblExamResult result = new TblExamResult();
		BeanUtils.copyProperties(result, findOne);
		Long singleTotal=new Long(0);
		Long multiTotal=new Long(0);
		if(findOne.getTblExamTopicnum().size()>0){
			for(TblExamTopicnum i:findOne.getTblExamTopicnum()){
				if(i.getLevelSecond().equals(findOne.getLevelFirst())){
					result.setName(tblClassifyRepository.findOne(findOne.getLevelFirst()).getClassifyName());
				}
				if(StringUtils.equals(i.getTopicType(), "0")){
					singleTotal=singleTotal+i.getTopicNum();
				}else{
					multiTotal=multiTotal+i.getTopicNum();
				}
			}
		}
		result.setSingleTotal(singleTotal);
		result.setMultiTotal(multiTotal);
		return result;
	}

	/**
	 * 启用或隐藏试卷
	 * @param id
	 * @param examQuery
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long startExam(Long id, ExamQuery examQuery) throws Exception{
		TblExam findOne = tblExamRepository.findOne(id);
		Date date = new Date();
		if(StringUtils.equalsIgnoreCase(findOne.getExamModel(), "1")&&StringUtils.equalsIgnoreCase(examQuery.getType(), "1")){
			if(date.after(findOne.getStartTime())&&date.before(findOne.getEndTime())){
				throw new Exception("该试卷考试中不允许修改状态");
			}
		}
		findOne.setIsEnable(examQuery.getType());
		tblExamRepository.save(findOne);
		return findOne.getId();
	}

	/**
	 * 查询列表
	 * @param examQuery
	 * @return
	 */
	public PageBean<ExamResult> queryExam(ExamQuery examQuery) throws Exception{
		PageBean<ExamResult> page=null;
		Specification<TblExam> specification = new Specification<TblExam>() {
			
			@Override
			public Predicate toPredicate(Root<TblExam> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(examQuery.getName())){
					Predicate pre = cb.like(root.get("examName"), "%"+examQuery.getName()+"%");
					predicateList.add(pre);
				}
				Predicate pre = cb.equal(root.get("isDelete"), codeChange.getIsDelete().get("notDelete"));
				predicateList.add(pre);
				Predicate[] cate = new Predicate[predicateList.size()];
				return cb.and(predicateList.toArray(cate));
			}
		};
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable able = new PageRequest(examQuery.getPage()-1, examQuery.getSize(), sort);
		Page<TblExam> findAll = tblExamRepository.findAll(specification, able);
		List<ExamResult> list = new ArrayList<ExamResult>();
		if(findAll.getContent()!=null&&findAll.getContent().size()>0){
			List<TblExam> content = findAll.getContent();
			for(TblExam t:content){
				ExamResult ex = new ExamResult();
				BeanUtils.copyProperties(ex, t);
				ex.setFirstName(tblClassifyRepository.findOne(t.getLevelFirst()).getClassifyName());
				t.setExamType(codeChange.getExamType().get(t.getExamType()));
				list.add(ex);
			}
		}
		page = new PageBean<ExamResult>(findAll.getTotalElements(), list);
		return page;
	}

	/**
	 * 试卷下拉列表
	 * @param examQuery
	 * @return
	 */
	public PageBean<ExamResult> queryExamXL(ExamQuery examQuery) throws Exception{
		PageBean<ExamResult> page=null;
		Specification<TblExam> specification = new Specification<TblExam>() {
			
			@Override
			public Predicate toPredicate(Root<TblExam> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(examQuery.getName())){
					Predicate pre = cb.like(root.get("examName"), "%"+examQuery.getName()+"%");
					predicateList.add(pre);
				}
				Predicate[] cate = new Predicate[predicateList.size()];
				return cb.and(predicateList.toArray(cate));
			}
		};
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable able = new PageRequest(examQuery.getPage()-1, examQuery.getSize(), sort);
		Page<TblExam> findAll = tblExamRepository.findAll(specification, able);
		List<ExamResult> list = new ArrayList<ExamResult>();
		if(findAll.getContent()!=null&&findAll.getContent().size()>0){
			List<TblExam> content = findAll.getContent();
			for(TblExam t:content){
				ExamResult ex = new ExamResult();
				BeanUtils.copyProperties(ex, t);
				ex.setFirstName(tblClassifyRepository.findOne(t.getLevelFirst()).getClassifyName());
				t.setExamType(codeChange.getExamType().get(t.getExamType()));
				list.add(ex);
			}
		}
		page = new PageBean<ExamResult>(findAll.getTotalElements(), list);
		return page;
	}

	/**
	 * 删除试卷
	 * @param id
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteExam(Long id) throws Exception{
		TblExam findOne = tblExamRepository.findOne(id);
		Date date = new Date();
		if(StringUtils.equalsIgnoreCase(findOne.getExamModel(), "1")){
			if(date.after(findOne.getStartTime())&&date.before(findOne.getEndTime())){
				throw new Exception("该试卷考试中不允许删除");
			}
		}
		findOne.setIsDelete(codeChange.getIsDelete().get("delete"));
		tblExamRepository.save(findOne);
	}

	/**
	 * 批量删除试卷
	 * @param id
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteExams(String id) throws Exception{
		String[] split = id.split(",");
		for(String i:split){
			TblExam findOne = tblExamRepository.findOne(Long.parseLong(i));
			Date date = new Date();
			if(StringUtils.equalsIgnoreCase(findOne.getExamModel(), "1")){
				if(date.after(findOne.getStartTime())&&date.before(findOne.getEndTime())){
					throw new Exception("该试卷考试中不允许删除");
				}
			}
			findOne.setIsDelete(codeChange.getIsDelete().get("delete"));
			tblExamRepository.save(findOne);
		}
	}

	/**
	 * 添加考试人员
	 * @param tblExam
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long addExamUser(TblExam tblExam, BindingResult bindResult) throws Exception {
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		Set<TblExamUser> tblExamUser = tblExam.getTblExamUser();
		tblExam.setTblExamUser(null);
		TblExam findOne = tblExamRepository.findOne(tblExam.getId());
		tblExamUserRepository.delete(findOne.getTblExamUser());
		//将人员放入数据库
		for(TblExamUser t:tblExamUser){
			//查看该人员是否存在
			TblExamUser findByUserIdAndOrgCodeAndTblExam = tblExamUserRepository.findByUserIdAndOrgCodeAndTblExam(t.getUserId(), t.getOrgCode(), tblExam);
			if(findByUserIdAndOrgCodeAndTblExam!=null){
				continue;
			}
			t.setTblExam(tblExam);
			tblExamUserRepository.save(t);
		}
		//另一种
		/*for(TblExamUser t:tblExamUser){
			//查看该人员是否存在
			TblExamUser findByUserIdAndOrgCodeAndTblExam = tblExamUserRepository.findByUserIdAndOrgCodeAndTblExam(t.getUserId(), t.getOrgCode(), tblExam);
			if(findByUserIdAndOrgCodeAndTblExam==null){
				t.setTblExam(tblExam);
				tblExamUserRepository.save(t);
			}else{
				t.setTblExam(tblExam);
				TblExamUser save = tblExamUserRepository.save(t);
				List<TblScore> findByTblExamUser = tblScoreRepository.findByTblExamUser(t);
				for(TblScore sc:findByTblExamUser){
					sc.setTblExamUser(save);
					tblScoreRepository.save(sc);
				}
			}
		}
		tblExamUserRepository.delete(tblExamUser);*/
		
		return tblExam.getId();
	}

	/**
	 * 查看考试试卷
	 * @param examQuerys
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public PageBean<TblExam> queryExams(ExamQuerys examQuerys) throws Exception {
		if(examQuerys.getUserId()==null){
			throw new Exception("1131");
		}
		if(StringUtils.isBlank(examQuerys.getType())){
			throw new Exception("1144");
		}
		com.github.pagehelper.Page<TblExam> p = PageHelper.startPage(examQuerys.getPage() == null ? 0 : examQuerys.getPage(),examQuerys.getSize() == null ? 0 : examQuerys.getSize());
		List<TblExam> list=tblExamDao.findExamByUser(examQuerys);
		Long count = p.getTotal();
		PageBean<TblExam> pageBean = new PageBean<TblExam>(count, list);
		return pageBean;
	}

	/**
	 * 开始考试
	 * @param id
	 * @return
	 */
	public TblExam doExam(Long id) throws Exception{
		TblExam findOne = tblExamRepository.findOne(id);
		if("1".equals(findOne.getExamType())){
			setNum(findOne);
			return findOne;
		}
		Set<TblExamTopicnum> tblExamTopicnum = findOne.getTblExamTopicnum();
		findOne.setTblExamTopicnum(new HashSet<TblExamTopicnum>());
		//冲数据库中随机抽取试题
		for(TblExamTopicnum t:tblExamTopicnum){
			Map<String, Object> map = new HashMap<String, Object>();
			if(findOne.getLevelFirst().equals(t.getLevelSecond())){
				map.put("kind", findOne.getLevelFirst());
				map.put("topicType", t.getTopicType());
			}else{
				map.put("kind", findOne.getLevelFirst()+","+t.getLevelSecond());
				map.put("topicType", t.getTopicType());
			}
			map.put("number", t.getTopicNum());
			map.put("isDelete", codeChange.getIsDelete().get("notDelete"));
			List<TblTopic> list = tblTopicDao.findTopic(map);
			List<TblTopic> list2 = new ArrayList<TblTopic>();
			for(TblTopic tt:list){
				TblTopic findOne2 = tblTopicRepository.findOne(tt.getId());
				list2.add(findOne2);
			}
			Set<TblTopic> result = new HashSet<TblTopic>(list2);
			t.setTblTopic(result);
			tblExamTopicnum.add(t);
		}
		findOne.setTblExamTopicnum(tblExamTopicnum);
		setNum(findOne);
		return findOne;
	}

	/**
	 * 提交试卷
	 * @param examScore
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long editScore(TblScore tblScore, BindingResult bindResult) throws Exception {
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		tblScoreRepository.save(tblScore);
		return tblScore.getId();
	}

	/**
	 *  正式考试时获取试卷
	 * @param id
	 * @return
	 */
	public TblExam doRealExam(Long id) {
		TblExam findOne = tblExamRepository.findOne(id);
		if("1".equals(findOne.getExamType())){
			Set<TblExamTopicnum> tblExamTopicnum = findOne.getTblExamTopicnum();
			Set<TblExamTopicnum> set = new HashSet<TblExamTopicnum>();
			for(TblExamTopicnum t:tblExamTopicnum){
				Set<TblTopic> tblTopic = t.getTblTopic();
				Set<TblTopic> top = new HashSet<TblTopic>();
				for(TblTopic topic:tblTopic){
					topic.setAnswer(null);
					topic.setResolve(null);
					top.add(topic);
				}
				t.setTblTopic(top);
				set.add(t);
			}
			findOne.setTblExamTopicnum(set);
			setNum(findOne);
			return findOne;
		}
		Set<TblExamTopicnum> tblExamTopicnum = findOne.getTblExamTopicnum();
		findOne.setTblExamTopicnum(new HashSet<TblExamTopicnum>());
		//冲数据库中随机抽取试题
		for(TblExamTopicnum t:tblExamTopicnum){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("kind", findOne.getLevelFirst()+","+t.getLevelSecond());
			map.put("topicType", t.getTopicType());
			map.put("number", t.getTopicNum());
			map.put("isDelete", codeChange.getIsDelete().get("notDelete"));
			List<TblTopic> list = tblTopicDao.findTopic(map);
			List<TblTopic> list2 = new ArrayList<TblTopic>();
			for(TblTopic tt:list){
				TblTopic findOne2 = tblTopicRepository.findOne(tt.getId());
				findOne2.setAnswer(null);
				findOne2.setResolve(null);
				list2.add(findOne2);
			}
			Set<TblTopic> result = new HashSet<TblTopic>(list2);
			t.setTblTopic(result);
			tblExamTopicnum.add(t);
		}
		findOne.setTblExamTopicnum(tblExamTopicnum);
		setNum(findOne);
		return findOne;
	}

	/**
	 * 计算正式考试成绩并存入数据库
	 * @param tblScore
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> editRealScore(ScoreQuery tblScore, BindingResult bindResult) throws Exception {
		Map<String,Object> map =new HashMap<String, Object>();
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		TblExam findOne = tblExamRepository.findOne(tblScore.getId());
		findOne.setTblExamUser(null);
		TblScore tblScore2 = tblScore.getTblScore();
		tblScore2.setExamId(tblScore.getId());
		tblScore2.setUserId(tblScore.getUserId());
		//计算分数
		double totalScore=0;
		long right = 0l;
		for(TblTopic t:tblScore.getTopic()){
			TblTopic findOne2 = tblTopicRepository.findOne(t.getId());
			String answer = findOne2.getAnswer();//数据库
			String[] answer2 = t.getAnswer().split("");//选择
			if((answer.split("").length)!=answer2.length){
				continue;
			}
			boolean boo=true;
			for(String s: answer2){
				if(!answer.contains(s)){
					boo=false;
				}
			}
			if(boo){
				right+=1;
				if("0".equalsIgnoreCase(findOne2.getTopicType())){
					totalScore+=findOne.getSingleScore();
				}else{
					totalScore+=findOne.getCheckBoxScore();
				}
			}
		}
		tblScore2.setScore(totalScore);
		tblScoreRepository.save(tblScore2);
		map.put("totalScore", totalScore);
		map.put("rightNum", right);
		map.put("wrongNum", findOne.getTotal()-right);
		return map;
	}

	private void setNum(TblExam tblExam){
		Set<TblExamTopicnum> set = new HashSet<TblExamTopicnum>();
		Set<TblExamTopicnum> tblExamTopicnum = tblExam.getTblExamTopicnum();
		for(TblExamTopicnum t:tblExamTopicnum){
			int size = t.getTblTopic().size();
			t.setTopicNum((long)size);
			set.add(t);
		}
		tblExam.setTblExamTopicnum(set);
	}

	private Long setNum_01(TblExam tblExam){
		long top=0;
		Set<TblExamTopicnum> set = new HashSet<TblExamTopicnum>();
		Set<TblExamTopicnum> tblExamTopicnum = tblExam.getTblExamTopicnum();
		for(TblExamTopicnum t:tblExamTopicnum){
			Long num = null;
			if(tblExam.getLevelFirst().equals(t.getLevelSecond())){
				num=topicServer.getNum(t.getLevelSecond(),t.getTopicType());
			}else{
				num=topicServer.getNum(tblExam.getLevelFirst()+","+t.getLevelSecond(),t.getTopicType());
			}
			if(t.getTopicNum()>num){
				t.setTopicNum(num);
			}
			set.add(t);
			top+=t.getTopicNum();
		}
		tblExam.setTblExamTopicnum(set);
		return top;
	}
}
