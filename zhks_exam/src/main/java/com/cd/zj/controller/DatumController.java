package com.cd.zj.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.cd.zj.bean.PageBean;
import com.cd.zj.bean.Response;
import com.cd.zj.bean.query.DatumQuery;
import com.cd.zj.bean.query.GetDatum;
import com.cd.zj.bean.result.DatumResult;
import com.cd.zj.entity.TblDatum;
import com.cd.zj.entity.TblTrain;
import com.cd.zj.server.DatumServer;
import com.cd.zj.util.CommonUtil;

@RestController
@RequestMapping("/datum")
public class DatumController {

	@Autowired
	private DatumServer datumServer;

	@PostMapping("/addDatum")
	public Response addDatum(@Validated GetDatum tblDatum,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Long id = datumServer.addDatum(tblDatum,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@PostMapping("/editDatum/{id}")
	public Response editDatum(@Validated GetDatum tblDatum,BindingResult bindResult) throws Exception{
		Response response = new Response();
		Long id = datumServer.editDatum(tblDatum,bindResult);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@GetMapping("/viewDatum/{id}")
	public Response viewDatum( @PathVariable Long id) throws Exception{
		Response response = new Response();
		TblDatum tblDatum = datumServer.viewDatum(id);
		CommonUtil.success(response);
		response.setData(tblDatum);
		return response;
	}

	@DeleteMapping("/deleteDatum/{id}")
	public Response deleteDatum( @PathVariable("id") Long id) throws Exception{
		Response response = new Response();
		datumServer.deleteDatum(id);
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@DeleteMapping("/deleteDatums/{ids}")
	public Response deleteDatums( @PathVariable("ids") String ids) throws Exception{
		Response response = new Response();
		datumServer.deleteDatums(ids);
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@GetMapping("/queryDatum")
	public Response queryDatum(DatumQuery datumQuery) throws Exception{
		Response response = new Response();
		PageBean<DatumResult> tblDatum = datumServer.queryDatum(datumQuery);
		CommonUtil.success(response);
		response.setData(tblDatum);
		return response;
	}

	@PutMapping("/startDatum/{id}")
	public Response startClassify(@PathVariable("id")Long id,@RequestBody DatumQuery datumQuery)throws Exception{
		Response response = new Response();
		datumServer.startClassify(id,datumQuery.getType());
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@PutMapping("/addDatumTopic")
	public Response addDatumTopic(@RequestBody TblDatum tblDatum)throws Exception{
		Response response = new Response();
		datumServer.addDatumTopic(tblDatum);
		CommonUtil.success(response);
		response.setData(null);
		return response;
	}

	@GetMapping("/viewDatumTopic/{id}")
	public Response viewDatumTopic( @PathVariable("id") Long id) throws Exception{
		Response response = new Response();
		TblDatum tblDatum = datumServer.viewDatumTopic(id);
		CommonUtil.success(response);
		response.setData(tblDatum);
		return response;
	}

	@GetMapping("/queryDatums")
	public Response queryDatums(DatumQuery datumQuery) throws Exception{
		Response response = new Response();
		datumQuery.setIsHidden("0");
		PageBean<DatumResult> tblDatum = datumServer.queryDatum(datumQuery);
		CommonUtil.success(response);
		response.setData(tblDatum);
		return response;
	}

	@GetMapping("/doDatum/{id}")
	public Response doDatum(@PathVariable("id") Long id,Long userId) throws Exception{
		Response response = new Response();
		Map<String, Object> tblDatum = datumServer.doDatum(id,userId);
		CommonUtil.success(response);
		response.setData(tblDatum);
		return response;
	}

	@PostMapping("/addTrain")
	public Response addTrain(@RequestBody TblTrain tblTrain) throws Exception{
		Response response = new Response();
		Long id = datumServer.addTrain(tblTrain);
		CommonUtil.success(response);
		response.setData(id);
		return response;
	}

	@GetMapping("/downDatum/{id}")
	public Response downDatum(@PathVariable("id") Long id,HttpServletResponse response) throws Exception{
		Response respons = new Response();
		datumServer.downDatum(id,response);
		CommonUtil.success(respons);
		respons.setData(null);
		return respons;
	}
}
