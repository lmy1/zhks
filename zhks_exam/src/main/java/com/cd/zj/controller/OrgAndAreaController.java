package com.cd.zj.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cd.zj.bean.Response;
import com.cd.zj.bean.result.TreeNode;
import com.cd.zj.server.OrgAndAreaServer;
import com.cd.zj.util.CommonUtil;

@RestController
@RequestMapping("/orgAndArea")
public class OrgAndAreaController {

	@Autowired
	private OrgAndAreaServer orgAndAreaServer;

	@RequestMapping(value="/relateOrg",method=RequestMethod.GET)
	public Response relateOrg()throws Exception{
		Response response = new Response();
		List<Map<String, Object>> classify=orgAndAreaServer.relateOrg();
		CommonUtil.success(response);
		response.setData(classify);
		return response;
	}

	@RequestMapping(value="/relateArea",method=RequestMethod.GET)
	public Response relateArea()throws Exception{
		Response response = new Response();
		List<TreeNode> list=orgAndAreaServer.relateArea();
		CommonUtil.success(response);
		response.setData(list);
		return response;
	}

	@RequestMapping(value="/relateExam",method=RequestMethod.GET)
	public Response relateExam()throws Exception{
		Response response = new Response();
		List<Map<String, Object>> classify=orgAndAreaServer.relateExam();
		CommonUtil.success(response);
		response.setData(classify);
		return response;
	}
}
