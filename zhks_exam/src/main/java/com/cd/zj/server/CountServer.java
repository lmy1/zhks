package com.cd.zj.server;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.query.TrainQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.CountTrain;
import com.cd.zj.bean.result.ScoreResult;
import com.cd.zj.bean.result.TrainResult;
import com.cd.zj.dao.CountDao;
import com.cd.zj.util.ExcelUtils;
import com.cd.zj.util.MapUtils;
import com.github.pagehelper.PageHelper;

@Service
public class CountServer {

	@Autowired
	private CountDao countDao;

	/**
	 * 查询单位平均成绩排名
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public List<CountResult> companyCount(Long id) throws Exception {
		List<CountResult> list = countDao.findCompanyPx(id);
		return list;
	}

	/**
	 * 个人成绩排名
	 * @param id
	 * @param name
	 * @param order 
	 * @return
	 * @throws Exception 
	 */
	public List<CountResult> personSorce(Long id, String name) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", name);
		List<CountResult> list = countDao.personSorce(map);
		return list;
	}

	/**
	 * 导出单位成绩
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	public void exceptCompany(Long id, HttpServletResponse response) throws Exception {
		String data="company";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		ExcelUtils.writeFileOfMap(getHeads(data), getProperty(data), getDate(data,map),response.getOutputStream());
	}

	/**
	 * 导出个人成绩
	 * @param id
	 * @param name
	 * @param response
	 * @throws Exception 
	 * @throws IOException 
	 */
	public void exceptPerson(Long id, String name, HttpServletResponse response) throws Exception {
		String data="person";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", name);
		ExcelUtils.writeFileOfMap(getHeads(data), getProperty(data), getDate(data,map),response.getOutputStream());
	}

	/**
	 * 在线培训统计
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception 
	 */
	public List<CountTrain> countTrain(Date startTime, Date endTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<CountTrain> list = countDao.countTrain(map);
		return list;
	}

	/**
	 * 导出培训记录
	 * @param startTime
	 * @param endTime
	 * @param response
	 * @throws Exception
	 */
	public void exportTrain(Date startTime, Date endTime, HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		String data = "train";
		ExcelUtils.writeFileOfMap(getHeads(data), getProperty(data), getDate(data,map),response.getOutputStream());
	}

	/**
	 * 显示培训信息
	 * @param trainQuery
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBean<TrainResult> queryTrain(TrainQuery trainQuery) {
		com.github.pagehelper.Page<TrainResult> p = PageHelper.startPage(trainQuery.getPage() == null ? 0 : trainQuery.getPage(),trainQuery.getSize() == null ? 0 : trainQuery.getSize());
		List<TrainResult> list = countDao.queryTrain(trainQuery);
		PageBean<TrainResult> pageBean = new PageBean<TrainResult>(p.getTotal(), list);
		return pageBean;
	}

	/**
	 * 显示考试成绩
	 * @param trainQuery
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBean<ScoreResult> queryScore(TrainQuery trainQuery) {
		com.github.pagehelper.Page<ScoreResult> p = PageHelper.startPage(trainQuery.getPage() == null ? 0 : trainQuery.getPage(),trainQuery.getSize() == null ? 0 : trainQuery.getSize());
		List<ScoreResult> list = countDao.queryScore(trainQuery);
		PageBean<ScoreResult> pageBean = new PageBean<ScoreResult>(p.getTotal(), list);
		return pageBean;
	}

	/**
  	 * 获取信息导出数据
  	 * @param isoutError
  	 * @param queryOutModel
  	 * @return
  	 */
	private List<Map<String, Object>>  getDate(String data,Map<String, Object> map) throws Exception{
		if("company".equals(data)){
			List<CountResult> list=countDao.findCompanyPx((long)map.get("id"));
			return MapUtils.objectToMap(list);
		}else if("person".equals(data)){
			List<CountResult> list = countDao.personSorce(map);
			return MapUtils.objectToMap(list);
		}else if("train".equals(data)){
			List<CountTrain> list =countDao.countTrain(map);
			return MapUtils.objectToMap(list);
		}
		return null;
	}

	/**
  	 * 获取信息表表头
  	 * @param isoutError
  	 * @return
  	 */
	private String[]  getHeads(String data) {
		if("company".equals(data)){
			String heads[]= {"排名","单位名称","平均成绩（分）"};
			return heads;
		}else if("person".equals(data)){
			String heads[]= {"排名","参考人员姓名","平均成绩（分）"};
			return heads;
		}else if("train".equals(data)){
			String heads[]= {"序号","单位名称","参加培训率"};
			return heads;
		}
		return null;
	}

	/**
	* 获取信息导出格式
	* @param class1
	* @return
	*/
	private  String[] getProperty(String data) {
		if("train".equals(data)){
			String property[]= {"id","name","percent"};
			return property;
		}else{
			String property[]= {"id","name","score"};
			return property;
		}
	}

	/**
	 * 查询当前用户是否考试或培训
	 * @param id
	 * @return
	 */
	public Long countNum(Long id) throws Exception{
		return countDao.countNum(id);
	}
}
