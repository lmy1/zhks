package com.cd.zj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cd.zj.bean.Response;
import com.cd.zj.bean.query.ScheduleQuery;
import com.cd.zj.bean.result.CountResult;
import com.cd.zj.bean.result.HomeResult;
import com.cd.zj.bean.result.Schedule;
import com.cd.zj.server.HomeServer;
import com.cd.zj.util.CommonUtil;

@RestController
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private HomeServer homeServer;

	@GetMapping("/viewSchedule")
	public Response viewSchedule(ScheduleQuery type) throws Exception{
		Response response = new Response();
		List<Schedule>list = homeServer.viewSchedule(type);
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}

	@GetMapping("/unitExam")
	public Response unitExam() throws Exception{
		Response response = new Response();
		List<CountResult>list = homeServer.unitExam();
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}

	@GetMapping("/passRate")
	public Response passRate() throws Exception{
		Response response = new Response();
		List<HomeResult>list = homeServer.passRate();
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}

	@GetMapping("/historyScore")
	public Response historyScore(Long userId) throws Exception{
		Response response = new Response();
		List<CountResult>list = homeServer.historyScore(userId);
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}

	@GetMapping("/personPass")
	public Response personPass(Long userId) throws Exception{
		Response response = new Response();
		List<HomeResult>list = homeServer.personPass(userId);
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}
}
