package com.cd.zj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.Response;
import com.cd.zj.bean.query.GetReport;
import com.cd.zj.bean.query.ReportQuery;
import com.cd.zj.bean.result.ReportResult;
import com.cd.zj.server.ReportServer;
import com.cd.zj.util.CommonUtil;

@RestController

@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportServer reportServer;

	@PostMapping("/addReport")
	public Response addReport(@RequestBody @Validated GetReport tblReport,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Long id = reportServer.addReport(tblReport,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@GetMapping("/viewReport/{id}")
	public Response viewReport(@PathVariable("id") Long id,String type) throws Exception{
		Response response = new Response();
		Object tblReport = reportServer.viewReport(id,type);
		CommonUtil.success(response);
		response.setData(tblReport);
		return response;
	}

	@DeleteMapping("/deleteReport/{id}")
	public Response deleteReport(@PathVariable("id") Long id,@RequestBody ReportQuery type) throws Exception{
		Response response = new Response();
		reportServer.deleteReport(id,type.getType());
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@DeleteMapping("/deleteReports/{ids}")
	public Response deleteReports(@PathVariable("ids") String id,@RequestBody ReportQuery type) throws Exception{
		Response response = new Response();
		reportServer.deleteReports(id,type.getType());
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@GetMapping("/queryReport")
	public Response queryReport(ReportQuery reportQuery) throws Exception{
		Response response = new Response();
		PageBean<ReportResult> list=reportServer.queryReport(reportQuery);
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}
}
