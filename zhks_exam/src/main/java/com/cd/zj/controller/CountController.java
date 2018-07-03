package com.cd.zj.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.Response;
import com.cd.zj.bean.query.TrainQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.CountTrain;
import com.cd.zj.bean.result.ScoreResult;
import com.cd.zj.bean.result.TrainResult;
import com.cd.zj.server.CountServer;
import com.cd.zj.util.CommonUtil;

@RestController
@RequestMapping("/count")
public class CountController {

	@Autowired
	private CountServer countServer;

	@GetMapping("/company")
	public Response companySorce(Long id) throws Exception{
		Response response = new Response();
		List<CountResult> list = countServer.companyCount(id);
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}

	@GetMapping("/exceptCompany")
	public Response exceptCompany(Long id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Response respon = new Response();
		String fileName="company";
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.addHeader("Content-Disposition","attachment; filename="+fileName+".xls");
		countServer.exceptCompany(id,response);
		CommonUtil.success(respon);
		respon.setData(null);
		return respon;
	}

	@GetMapping("/person")
	public Response personSorce(Long id,String name) throws Exception{
		Response response = new Response();
		List<CountResult> list = countServer.personSorce(id,name);
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}

	@GetMapping("/exceptPerson")
	public Response exceptPerson(Long id,String name,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Response respon = new Response();
		String fileName="person";
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.addHeader("Content-Disposition","attachment; filename="+fileName+".xls");
		countServer.exceptPerson(id,name,response);
		CommonUtil.success(respon);
		respon.setData(null);
		return respon;
	}

	@GetMapping("/countTrain")
	public Response countTrain(TrainQuery trainQuery) throws Exception{
		Response response = new Response();
		List<CountTrain> list = countServer.countTrain(trainQuery.getStartTime(),trainQuery.getEndTime());
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}

	@GetMapping("/exportTrain")
	public Response exportTrain(TrainQuery trainQuery,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Response respon = new Response();
		String fileName="person";
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.addHeader("Content-Disposition","attachment; filename="+fileName+".xls");
		countServer.exportTrain(trainQuery.getStartTime(),trainQuery.getEndTime(),response);
		CommonUtil.success(respon);
		respon.setData(null);
		return respon;
	}

	@GetMapping("/queryTrain")
	public Response queryTrain(TrainQuery trainQuery) throws Exception{
		Response response = new Response();
		PageBean<TrainResult> queryTrain = countServer.queryTrain(trainQuery);
		CommonUtil.success(response);
		response.setData(queryTrain);
		return response;
	}

	@GetMapping("/queryScore")
	public Response queryScore(TrainQuery trainQuery) throws Exception{
		Response response = new Response();
		PageBean<ScoreResult> queryTrain = countServer.queryScore(trainQuery);
		CommonUtil.success(response);
		response.setData(queryTrain);
		return response;
	}

	@GetMapping("/countNum/{id}")
	public boolean countNum(@PathVariable(name="id") Long id) throws Exception{
		Long num = countServer.countNum(id);
		if(num>0){
			return false;
		}
		return true;
	}
}
