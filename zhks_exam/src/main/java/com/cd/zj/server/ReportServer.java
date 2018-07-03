package com.cd.zj.server;


import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.query.GetReport;
import com.cd.zj.bean.query.ReportQuery;
import com.cd.zj.bean.result.ReportResult;
import com.cd.zj.dao.CountDao;
import com.cd.zj.entity.TblReport;
import com.cd.zj.entity.TblReportUser;
import com.cd.zj.repository.TblReportRepository;
import com.cd.zj.repository.TblReportUserRepository;
import com.cd.zj.util.CommonUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ReportServer {

	@Autowired
	private TblReportRepository tblReportRepository;

	@Autowired
	private CountDao countDao;

	@Autowired
	private TblReportUserRepository tblReportUserRepository;

	/**
	 * 添加通知
	 * @param tblReport
	 * @param userId 
	 * @param bindResult
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackOn=Exception.class)
	public Long addReport(GetReport getReport, BindingResult bindResult) throws Exception{
		String msg =CommonUtil.getError(bindResult);
		if(StringUtils.isNotBlank(msg)){
			throw new Exception(msg);
		}
		String[] split = getReport.getUserIds().split(",");
		for(String s:split){
			getReport.setUserid(Long.parseLong(s));
			getReport.setCreatetime(new Date());
			TblReport tbl = new TblReport();
			BeanUtils.copyProperties(tbl, getReport);
			tblReportRepository.save(tbl);
			TblReportUser us = new TblReportUser();
			BeanUtils.copyProperties(us, getReport);
			tblReportUserRepository.save(us);
		}
		return null;
	}

	/**
	 * 查看短信
	 * @param id
	 * @param type 
	 * @return
	 */
	public Object viewReport(Long id, String type) {
		//管理员
				if("0".equals(type)){
					return tblReportRepository.findOne(id);
					//用户
				}else if ("1".equals(type)) {
					return tblReportUserRepository.findOne(id);
				}
				return null;
	}

	/**
	 * 删除通知
	 * @param id
	 * @param type 
	 */
	@Transactional(rollbackOn=Exception.class)
	public void deleteReport(Long id, String type) throws Exception{
		//管理员
		if("0".equals(type)){
			tblReportRepository.delete(id);
			//用户
		}else if ("1".equals(type)) {
			tblReportUserRepository.delete(id);
		}
	}

	/**
	 * 批量删除通知
	 * @param id
	 * @param type 
	 */
	@Transactional(rollbackOn=Exception.class)
	public void deleteReports(String id, String type) throws Exception{
		String[] split = id.split(",");
		for(String i:split){
			//管理员
			if("0".equals(type)){
				tblReportRepository.delete(Long.parseLong(i));
				//用户
			}else if ("1".equals(type)) {
				tblReportUserRepository.delete(Long.parseLong(i));
			}
		}
	}

	/**
	 * 查询通知列表
	 * @param reportQuery
	 * @return
	 */
	public PageBean<ReportResult> queryReport(ReportQuery reportQuery) {
		PageBean<ReportResult> pageBean = null;
		@SuppressWarnings("rawtypes")
		Page help = PageHelper.startPage(reportQuery.getPage() == null ? 0 : reportQuery.getPage(),reportQuery.getSize() == null ? 0 : reportQuery.getSize());
		List<ReportResult> list = countDao.queryReport(reportQuery);
		pageBean = new PageBean<ReportResult>(help.getTotal(), list);
		return pageBean;
	}
}
