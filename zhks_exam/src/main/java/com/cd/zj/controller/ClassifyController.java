package com.cd.zj.controller;

import java.util.List;
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
import com.cd.zj.bean.query.ClassIfyQuery;
import com.cd.zj.bean.result.ClassifyResult;
import com.cd.zj.entity.TblClassify;
import com.cd.zj.server.ClassifyServer;
import com.cd.zj.util.CodeMsg;
import com.cd.zj.util.CommonUtil;

@RestController
@RequestMapping("/classify")
public class ClassifyController {

	@Autowired
	private ClassifyServer classifyServer;

	@PostMapping("/addClassify")
	public Response addClassify(@RequestBody @Validated TblClassify tblClassify,BindingResult bindResult) throws Exception{
		Response response = new Response();
		String id = classifyServer.addClassify(tblClassify,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@PutMapping("/editClassify/{id}")
	public Response editClassify(@RequestBody @Validated TblClassify tblClassify,BindingResult bindResult) throws Exception{
		Response response = new Response();
		String id = classifyServer.editClassify(tblClassify,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@DeleteMapping("/deleteClassify/{id}")
	public Response deleteClassify(@PathVariable(name="id") String id)throws Exception{
		Response response = new Response();
		classifyServer.deleteClassify(id);
		CommonUtil.success(response);
		return response;
	}

	@DeleteMapping("/deleteClassifys/{ids}")
	public Response deleteClassifys(@PathVariable(name="ids") String ids)throws Exception{
		Response response = new Response();
		classifyServer.deleteClassifys(ids);
		CommonUtil.success(response);
		return response;
	}

	@GetMapping("/viewClassify/{id}")
	public Response viewClassify(@PathVariable(name="id") String id)throws Exception{
		Response response = new Response();
		TblClassify classify=classifyServer.viewClassify(id);
		CommonUtil.success(response);
		response.setData(classify);
		return response;
	}

	@GetMapping("/queryClassify")
		public Response queryClassify(ClassIfyQuery classIfyQuery)throws Exception{
			Response response = new Response();
			PageBean<ClassifyResult> classify=classifyServer.queryClassify(classIfyQuery);
			CommonUtil.success(response);
			response.setData(classify);
			return response;
		}

	@GetMapping("/relateClassify")
	public Response relateClassify()throws Exception{
		Response response = new Response();
		List<Map<String, Object>> classify=classifyServer.relateClassify();
		CommonUtil.success(response);
		response.setData(classify);
		return response;
	}

	@GetMapping("/firstClassify")
	public Response firstClassify()throws Exception{
		Response response = new Response();
		List<TblClassify> classify=classifyServer.firstClassify();
		CommonUtil.success(response);
		response.setData(classify);
		return response;
	}

	@GetMapping("/firstClassifys")
	public Response firstClassifys()throws Exception{
		Response response = new Response();
		List<TblClassify> classify=classifyServer.firstClassifys();
		CommonUtil.success(response);
		response.setData(classify);
		return response;
	}

	@GetMapping("/secondClassify/{id}")
	public Response secondClassify(@PathVariable("id")String id)throws Exception{
		Response response = new Response();
		List<TblClassify> classify=classifyServer.secondClassify(id);
		CommonUtil.success(response);
		response.setData(classify);
		return response;
	}

	@PutMapping("/startClassify/{id}")
	public Response startClassify(@PathVariable("id")String id,@RequestBody ClassIfyQuery classIfyQuery){
		Response response = new Response();
		try {
			classifyServer.startClassify(id,classIfyQuery.getType());
			CommonUtil.success(response);
			response.setData(null);
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
}
