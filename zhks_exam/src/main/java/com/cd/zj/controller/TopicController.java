package com.cd.zj.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.Response;
import com.cd.zj.bean.query.TopicQuery;
import com.cd.zj.bean.result.TopicResult;
import com.cd.zj.entity.TblTopic;
import com.cd.zj.server.TopicServer;
import com.cd.zj.util.CodeMsg;
import com.cd.zj.util.CommonUtil;

@RestController
@RequestMapping("/topic")
public class TopicController {

	@Autowired
	private TopicServer topicServer;

	@PostMapping("/addTopic")
	public Response addTopic(@RequestBody @Validated TblTopic tblTopic,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Long id = topicServer.addTopic(tblTopic,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@PutMapping("/editTopic/{id}")
	public Response aditTopic(@RequestBody @Validated TblTopic tblTopic,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Long id = topicServer.aditTopic(tblTopic,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@GetMapping("/viewTopic/{id}")
	public Response viewTopic(@PathVariable("id") Long id) throws Exception{
		Response response = new Response();
		TblTopic tblTopic = topicServer.viewTopic(id);
		CommonUtil.success(response);
		response.setData(tblTopic);
		return response;
	}

	@GetMapping("/queryTopic")
	public Response queryTopic(TopicQuery topicQuery) throws Exception{
		Response response = new Response();
		PageBean<TopicResult> tblTopic = topicServer.queryTopic(topicQuery);
		CommonUtil.success(response);
		response.setData(tblTopic);
		return response;
	}

	@DeleteMapping("/deleteTopic/{id}")
	public Response deleteTopic(@PathVariable("id") Long id) throws Exception{
		Response response = new Response();
		topicServer.deleteTopic(id);
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@DeleteMapping("/deleteTopics/{ids}")
	public Response deleteTopics(@PathVariable("ids") String ids) throws Exception{
		Response response = new Response();
		topicServer.deleteTopics(ids);
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@PostMapping("/importTopic")
	public Response importTopic(@RequestParam(value = "file") MultipartFile file, @RequestParam(value="name") String name,HttpServletResponse response){
		Response ponse = new Response();
		try {
			topicServer.importTopic(file,name);
			CommonUtil.success(ponse);
			ponse.setData(null);
		} catch (Exception e) {
			ponse.setStatus(0);
			if(e.getMessage().matches("\\d+")) {//若返回的是数字这配置文件取值
				ponse.setData(CodeMsg.getMsg(e.getMessage()));
			}else if(e.getMessage().contains("Exception")){//系统抛出异常 显示操作失败
				ponse.setData(CodeMsg.getMsg("1001"));
			}else {
				ponse.setData(e.getMessage());//主要是spring mvc验证框架里抛出的异常
			}
		}
		return ponse;
	}

	@GetMapping("/downTopic")
	public Response downTopic(HttpServletResponse respons) throws Exception{
		Response response = new Response();
		topicServer.downTopic(respons);
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}
}
