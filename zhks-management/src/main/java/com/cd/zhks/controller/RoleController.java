package com.cd.zhks.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cd.zhks.bean.Response;
import com.cd.zhks.bean.ResultCode;
import com.cd.zhks.bean.dto.RoleDto;
import com.cd.zhks.bean.query.RoleQuery;
import com.cd.zhks.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/role")
@Api(description= "角色相关接口")
public class RoleController {

	@Autowired
	private RoleService roleService;

	private Logger log = LoggerFactory.getLogger(RoleController.class);


	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "新增角色")
	public Response addRole( @RequestBody @Validated RoleDto roleDto)
            throws Exception {

		return new Response(0, ResultCode.SUCCESS,   roleService.addRole(roleDto));
	}


	@ApiOperation(value = "修改角色")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Response updateRole(@PathVariable @ApiParam(value = "角色id") Long id, @RequestBody @Validated RoleDto roleDto)
            throws Exception {
        roleService.updateRole(id, roleDto);
		return new Response(0, ResultCode.SUCCESS, null);
	}


	@ApiOperation(value = "删除角色")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteRoles(@PathVariable("id") @ApiParam(value = "角色id")Long id)
            throws Exception {
	    roleService.deleteRole(id);
		return new Response(0, ResultCode.SUCCESS, null);
	}


	@ApiOperation(value = "根据ID查询角色")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response getRole(@PathVariable("id") @ApiParam(value = "角色id") Long id)
			throws Exception {

		return new Response(0, ResultCode.SUCCESS, roleService.getRole(id));
	}


	@ApiOperation(value = "角色分页条件查询")
	@RequestMapping(value = "/conditions", method = RequestMethod.GET)
	public Response findRoleByConditions(RoleQuery roleQuery)
			throws Exception {

		return new Response(0, ResultCode.SUCCESS, roleService.getRoleList(roleQuery));
	}


	@ApiOperation(value = "修改角色对应权限")
	@RequestMapping(value = "/{id}/authoritys", method = RequestMethod.PUT)
	public Response updateRoleAuthoritys(@PathVariable("id") @ApiParam(value = "角色id") Long id, @RequestBody @ApiParam(value = "角色对应权限id") Set<Long> authorityIds)
            throws Exception {
        roleService.updateRoleAuthoritys(id, authorityIds);
		return new Response(0, ResultCode.SUCCESS, null);
	}



}