package com.cd.zj.server;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.query.TopicQuery;
import com.cd.zj.bean.result.TopicResult;
import com.cd.zj.dao.CountDao;
import com.cd.zj.entity.TblClassify;
import com.cd.zj.entity.TblOptions;
import com.cd.zj.entity.TblTopic;
import com.cd.zj.repository.TblClassifyRepository;
import com.cd.zj.repository.TblOptionsRepository;
import com.cd.zj.repository.TblTopicRepository;
import com.cd.zj.util.CodeChange;
import com.cd.zj.util.CodeMsg;
import com.cd.zj.util.CommonUtil;

@Service
public class TopicServer {

	@Autowired
	private TblTopicRepository tblTopicRepository;

	@Autowired
	private TblOptionsRepository tblOptionsRepository;

	@Autowired
	private CodeChange codeChange;

	@Autowired
	private TblClassifyRepository tblClassifyRepository;

	@Autowired
	private CountDao countDao;

	/**
	 * 题目新增
	 * @param tblTopic
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long addTopic(TblTopic tblTopic, BindingResult bindResult) throws Exception {
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		/*if(tblTopic.getClassifyKind().indexOf(",")==-1){
			throw new Exception("请填写二级医科分类");
		}*/
		Set<TblOptions> tblOptions = tblTopic.getTblOptions();
		tblTopic.setTblOptions(null);
		tblTopic.setCreateTime(new Date());
		tblTopic.setIsDelete(codeChange.getIsDelete().get("notDelete"));
		tblTopicRepository.save(tblTopic);
		for(TblOptions i:tblOptions){
			i.setTblTopic(tblTopic);
			tblOptionsRepository.save(i);
		}
		return tblTopic.getId();
	}

	/**
	 * 修改题目
	 * @param tblTopic
	 * @param bindResult
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long aditTopic(TblTopic tblTopic, BindingResult bindResult) throws Exception {
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		/*if(tblTopic.getClassifyKind().indexOf(",")==-1){
			throw new Exception("请填写二级医科分类");
		}*/
		Long num = countDao.findNum(tblTopic.getId());
		if(num>0){
			throw new Exception("1161");
		}
		Set<TblOptions> tblOptions = tblTopic.getTblOptions();
		TblTopic findOne = tblTopicRepository.findOne(tblTopic.getId());
		tblOptionsRepository.delete(findOne.getTblOptions());
		tblTopic.setTblOptions(null);
		tblTopic.setCreateTime(new Date());
		tblTopicRepository.save(tblTopic);
		for(TblOptions t:tblOptions){
			t.setTblTopic(tblTopic);
			tblOptionsRepository.save(t);
		}
		return tblTopic.getId();
	}

	/**
	 * 查看题目
	 * @param id
	 * @return
	 */
	public TblTopic viewTopic(Long id) throws Exception{
		return tblTopicRepository.findOne(id);
	}

	/**
	 * 查询试题列表
	 * @param topicQuery
	 * @return
	 */
	public PageBean<TopicResult> queryTopic(TopicQuery topicQuery) throws Exception{
		PageBean<TopicResult> pageBean =null;
		Specification<TblTopic> spe = new Specification<TblTopic>() {
			
			@Override
			public Predicate toPredicate(Root<TblTopic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(topicQuery.getName())){
					Predicate p =cb.like(root.get("topics"), "%"+topicQuery.getName()+"%");
					list.add(p);
				}
				if(StringUtils.isNotBlank(topicQuery.getKind())){
					Predicate p =cb.like(root.get("classifyKind"), "%"+topicQuery.getKind()+"%");
					list.add(p);
				}
				if(StringUtils.isNotBlank(topicQuery.getPerson())){
					Predicate p =cb.like(root.get("createPerson"), "%"+topicQuery.getPerson()+"%");
					list.add(p);
				}
				if(topicQuery.getStartTime()!=null&&topicQuery.getEndTime()!=null){
					Predicate p =cb.between(root.get("createTime"), topicQuery.getStartTime(), topicQuery.getEndTime());
					list.add(p);
				}
				if(StringUtils.isNotBlank(topicQuery.getTopicType())){
					Predicate pred = cb.equal(root.get("topicType"), topicQuery.getTopicType());
					list.add(pred);
				}
				Predicate pre = cb.equal(root.get("isDelete"), codeChange.getIsDelete().get("notDelete"));
				list.add(pre);
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable able = new PageRequest(topicQuery.getPage()-1, topicQuery.getSize(), sort);
		Page<TblTopic> findAll = tblTopicRepository.findAll(spe, able);
		List<TblTopic> content = findAll.getContent();
		List<TopicResult> top = new ArrayList<TopicResult>();
		if(content.size()>0&&content!=null){
			for(int x=0;x<content.size();x++){
				TopicResult result = new TopicResult();
				BeanUtils.copyProperties(result, content.get(x));
				String[] split = content.get(x).getClassifyKind().split(",");
				result.setSecondName(tblClassifyRepository.findOne(split[split.length-1]).getClassifyName());
				top.add(result);
			}
		}
		pageBean = new PageBean<TopicResult>(findAll.getTotalElements(), top);
		return pageBean;
	}

	/**
	 * 试题删除
	 * @param id
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteTopic(Long id) throws Exception {
		TblTopic findOne = tblTopicRepository.findOne(id);
		findOne.setIsDelete(codeChange.getIsDelete().get("delete"));
		Long num = countDao.findNum(id);
		if(num>0){
			throw new Exception("1159");
		}
		tblTopicRepository.save(findOne);
	}

	/**
	 * 批量删除题目
	 * @param ids
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteTopics(String ids) throws Exception {
		String id[]=ids.split(",");
		for(String i:id){
			TblTopic findOne = tblTopicRepository.findOne(Long.parseLong(i));
			Long num = countDao.findNum(Long.parseLong(i));
			if(num>0){
				throw new Exception("1159");
			}
			findOne.setIsDelete(codeChange.getIsDelete().get("delete"));
			tblTopicRepository.save(findOne);
		}
	}

	/**
	 * 导入题目
	 * @param file
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void importTopic(MultipartFile file,String name) throws Exception {
		Workbook wb = null;
		//判断文件类型
		if (isExcel2007(file.getOriginalFilename())) {
			wb = new XSSFWorkbook(file.getInputStream());
		} else if(isExcel2003(file.getOriginalFilename())) {
			wb = new HSSFWorkbook(file.getInputStream());
		}
		Sheet sheetAt = wb.getSheetAt(0);
		System.out.println(sheetAt.getLastRowNum());
		Set<TblOptions> tblOptions = new HashSet<TblOptions>();
		//循环取值
		for (int i = 2; i <= sheetAt.getLastRowNum(); i++){
			if(sheetAt.getRow(i)==null){
				throw new Exception("第"+(i+1)+"行，不允许为空");
			}
			Row row = sheetAt.getRow(i);
			if(row.getCell(0)==null&&row.getCell(1)==null&&row.getCell(2)==null&&row.getCell(3)==null&&row.getCell(4)==null&&
					row.getCell(5)==null&&row.getCell(6)==null&&row.getCell(7)==null&&row.getCell(8)==null&&row.getCell(9)==null&&row.getCell(10)==null
					&&row.getCell(11)==null){
				continue;
			}
			TblTopic tblTopic =new TblTopic();
			tblTopic.setTblOptions(null);
			tblOptions.clear();
			//校验一级分类是否存在
			String cell1=row.getCell(0)==null?"":row.getCell(0).toString();
			if(cell1.endsWith(".0")){
				cell1=cell1.split("\\.")[0];
			}
			//校验一级分类是否存在
			List<TblClassify> findByClass = tblClassifyRepository.findByClassifyNameAndIsDeleteAndLevelsAndIsHidden(cell1, codeChange.getIsDelete().get("notDelete"), "1",codeChange.getClassifyType().get("show"));
			if(findByClass==null||findByClass.size()==0){
				throw new Exception("第"+(i+1)+"行，一级医科分类不存在");
			}
			tblTopic.setClassifyKind(findByClass.get(0).getId());
			//校验二级分类是否存在
			String cell2=row.getCell(1)==null?"":row.getCell(1).toString();
			List<TblClassify> findByParentId=null;
			if(StringUtils.isNotBlank(cell2)){
				if(cell2.endsWith(".0")){
					cell2=cell2.split("\\.")[0];
				}
				findByParentId = tblClassifyRepository.findByParentKeyAndIsDeleteAndIsHiddenAndClassifyName(findByClass.get(0).getId(), codeChange.getIsDelete().get("notDelete"), codeChange.getClassifyType().get("show"),cell2);
				if(findByParentId==null||findByParentId.size()==0){
					throw new Exception("第"+(i+1)+"行，二级医科分类不存在或与一级医科分类不关联");
				}
				tblTopic.setClassifyKind(findByClass.get(0).getId()+","+findByParentId.get(0).getId());
			}
			if("多选".equals(row.getCell(2)==null?"":row.getCell(2).toString())){
				tblTopic.setTopicType("1");
			}else if("单选".equals(row.getCell(2)==null?"":row.getCell(2).toString())){
				tblTopic.setTopicType("0");
			}else{
				throw new Exception("第"+(i+1)+"行，试题类型不正确");
			}
			
			//赋值
			tblTopic.setTopics(row.getCell(3)==null?"":row.getCell(3).toString());
			tblTopic.setResolve(row.getCell(5)==null?"":row.getCell(5).toString());
			
			//布尔类型 用于判断 选项中是否包含正确答案
			boolean yes = true;
			//选项循环
			int c = 64;
			String top="";
			for(int cell=6;cell<12;cell++){
				char a = (char) (cell+59);
				//选项值为空下一条
				if(StringUtils.isBlank(row.getCell(cell)==null?"":row.getCell(cell).toString())){
					continue;
				}
				c=c+1;
				TblOptions tblOption = new TblOptions();
				if(a!=(char)c){
					throw new Exception("第"+(i+1)+"行，选项之间不允许有空白选项");
				}
				if(row.getCell(4)==null){
					throw new Exception("第"+(i+1)+"行，正确答案不能为空");
				}
				//正确答案与选项相同
				/*if((row.getCell(4)==null?"":row.getCell(4).toString()).equals(String.valueOf((char)c))){
					yes=true;
				}*/
				top+=String.valueOf((char)c);
				tblOption.setContent(row.getCell(cell)==null?"":row.getCell(cell).toString());
				tblOption.setOptions(String.valueOf((char)c));
				tblOptions.add(tblOption);
			}
			if(row.getCell(4)!=null){
				String[] split = row.getCell(4).toString().split("");
				for(String s:split){
					if(!top.contains(s.trim())){
						yes=false;
					}
				}
			}
			//有正确答案
			if(yes){
				tblTopic.setAnswer(row.getCell(4)==null?"":row.getCell(4).toString());
			}else{
				throw new Exception("第"+(i+1)+"行，选项中没有该答案");
			}
			tblTopic.setTblOptions(tblOptions);
			tblTopic.setCreateTime(new Date());
			tblTopic.setIsDelete(codeChange.getIsDelete().get("notDelete"));
			tblTopic.setCreatePerson(name);
			//springMVC注解校验
			StringBuilder errorMsg = new StringBuilder();
			Set<ConstraintViolation<TblTopic>> validateSet = null;
			validateSet = Validation.buildDefaultValidatorFactory().getValidator().validate(tblTopic);
			for (ConstraintViolation<TblTopic> constraintViolation : validateSet) {
				errorMsg.append(CodeMsg.getMsg(constraintViolation.getMessage()));
				errorMsg.append(" ");
			}
			if(StringUtils.isNotBlank(errorMsg.toString())){
				throw new Exception("第"+(i+1)+"行，"+errorMsg.toString());
			}
			tblTopicRepository.save(tblTopic);
			for(TblOptions tbl:tblOptions){
				tbl.setTblTopic(tblTopic);
				tblOptionsRepository.save(tbl);
			}
		}
	}

	private boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	private boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	/**
	 * 下载模版
	 * @param respons 
	 * @throws Exception 
	 */
	public void downTopic(HttpServletResponse respons) throws Exception {
		//respons.setContentType("multipart/form-data");
		//respons.addHeader("Content-Type", "application/vnd.ms-excel");
		respons.addHeader("Content-Disposition",
				"attachment; filename="+new String("试题导入模版".getBytes("UTF-8"),"ISO8859-1")+".xlsx");
			InputStream input = null;
			input = this.getClass().getResourceAsStream("/templates/importTopic.xlsx");
			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				respons.getOutputStream().write(buffer, 0, n);
			}
	}

	public Long getNum(String classify, String string){
		List<TblTopic> findByClassifyKindAndIsDelete = tblTopicRepository.findByClassifyKindAndIsDeleteAndTopicType(classify, codeChange.getIsDelete().get("notDelete"),string);
		return (long) findByClassifyKindAndIsDelete.size();
	}
}
