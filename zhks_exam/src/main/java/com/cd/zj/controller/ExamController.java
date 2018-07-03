package com.cd.zj.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;
import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.Response;
import com.cd.zj.bean.query.ExamQuery;
import com.cd.zj.bean.query.ExamQuerys;
import com.cd.zj.bean.query.ScoreQuery;
import com.cd.zj.bean.result.ExamResult;
import com.cd.zj.bean.result.TblExamResult;
import com.cd.zj.entity.TblExam;
import com.cd.zj.entity.TblScore;
import com.cd.zj.server.ExamServer;
import com.cd.zj.util.CodeMsg;
import com.cd.zj.util.CommonUtil;
import com.cd.zj.util.group.Validate1;

@RestController
@RequestMapping("/exam")
public class ExamController {

	@Autowired
	private ExamServer examServer;

	@PostMapping("/addExam")
	public Response addExam(@RequestBody @Validated TblExam tblExam,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Long id = examServer.addExam(tblExam,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@PutMapping("/editExam/{id}")
	public Response aditExam(@RequestBody @Validated TblExam tblExam,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Long id = examServer.aditExam(tblExam,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@GetMapping("/viewExam/{id}")
	public Response viewExam(@PathVariable("id") Long id) throws Exception{
		Response response = new Response();
		TblExamResult tblExam = examServer.viewExam(id);
		CommonUtil.success(response);
		response.setData(tblExam);
		return response;
	}

	@PutMapping("/startExam/{id}")
	public Response startExam(@PathVariable("id") Long id,@RequestBody ExamQuery examQuery){
		Response response = new Response();
		Long examId;
		try {
			examId = examServer.startExam(id,examQuery);
			CommonUtil.success(response);
			response.setData(examId);
		} catch (Exception e) {
			response.setStatus(0);
			if(e.getMessage().matches("\\d+")) {//若返回的是数字这配置文件取值
				response.setResult(e.getMessage(),CodeMsg.getMsg(e.getMessage()));
			}else if(e.getMessage().contains("Exception")){//系统抛出异常 显示操作失败
				response.setResult("1001",CodeMsg.getMsg("1001"));
			}else {
				response.setResult("1003",e.getMessage());//主要是spring mvc验证框架里抛出的异常
			}
			response.setData("1");
		}
		return response;
	}

	@GetMapping("/queryExam")
	public Response queryExam( ExamQuery examQuery) throws Exception{
		Response response = new Response();
		PageBean<ExamResult> page = examServer.queryExam(examQuery);
		CommonUtil.success(response);
		response.setData(page);
		return response;
	}

	@GetMapping("/queryExamXL")
	public Response queryExamXL(ExamQuery examQuery) throws Exception{
		Response response = new Response();
		PageBean<ExamResult> page = examServer.queryExamXL(examQuery);
		CommonUtil.success(response);
		response.setData(page);
		return response;
	}

	@DeleteMapping("/deleteExam/{id}")
	public Response deleteExam( @PathVariable("id") Long id) throws Exception{
		Response response = new Response();
		examServer.deleteExam(id);
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@DeleteMapping("/deleteExams/{ids}")
	public Response deleteExams( @PathVariable("ids") String ids) throws Exception{
		Response response = new Response();
		examServer.deleteExams(ids);
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@PostMapping("/addExamUser")
	public Response addExamUser(@RequestBody @Validated(value=Validate1.class) TblExam tblExam,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Long id = examServer.addExamUser(tblExam,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@GetMapping("/queryExams")
	public Response queryExams( ExamQuerys examQuerys) throws Exception{
		Response response = new Response();
		PageBean<TblExam> page = examServer.queryExams(examQuerys);
		CommonUtil.success(response);
		response.setData(page);
		return response;
	}

	@GetMapping("/doExam/{id}")
	public Response doExam(@PathVariable("id") Long id) throws Exception{
		Response response = new Response();
		TblExam tblExam = examServer.doExam(id);
		CommonUtil.success(response);
		response.setData(tblExam);
		return response;
	}

	@GetMapping("/doRealExam/{id}")
	public Response doRealExam(@PathVariable("id") Long id) throws Exception{
		Response response = new Response();
		TblExam tblExam = examServer.doRealExam(id);
		CommonUtil.success(response);
		response.setData(tblExam);
		return response;
	}

	@PostMapping("/editScore")
	public Response putExam(@RequestBody @Validated TblScore tblScore,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Long examId = examServer.editScore(tblScore,bindResult);
		CommonUtil.success(response);
		response.setData(examId);
		return response;
	}

	@PostMapping("/editRealScore")
	public Response editRealScore(@RequestBody @Validated ScoreQuery tblScore,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Map<String, Object> editRealScore = examServer.editRealScore(tblScore,bindResult);
		CommonUtil.success(response);
		response.setData(editRealScore);
		return response;
	}
}
