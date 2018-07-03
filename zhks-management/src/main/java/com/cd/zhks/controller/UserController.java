package com.cd.zhks.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cd.zhks.bean.Id;
import com.cd.zhks.bean.Response;
import com.cd.zhks.bean.ResultCode;
import com.cd.zhks.bean.dto.PasswordDto;
import com.cd.zhks.bean.dto.ReviewStatusDto;
import com.cd.zhks.bean.query.UserQuery;
import com.cd.zhks.bean.vo.UserVo;
import com.cd.zhks.entity.User;
import com.cd.zhks.service.UserService;
import com.cd.zhks.service.exception.InspectionException;
import com.cd.zhks.service.exception.LogicException;
import com.cd.zhks.utils.LoginUserUtils;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private Logger log = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(method = RequestMethod.POST)
	public Response addUser(@RequestBody @Validated User user) throws Exception {
		Long result = userService.addUser(user);
		return new Response(0, ResultCode.SUCCESS, new Id(result));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Response updateUser(@PathVariable Long id, @RequestBody @Validated User user) throws Exception {
		userService.updateUser(id, user);
		return new Response(0, ResultCode.SUCCESS, "");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Response deleteUser(@PathVariable("id") Long id) throws InspectionException, LogicException {
		userService.deleteUser(id);
		return new Response(0, ResultCode.SUCCESS, "");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response getUser(@PathVariable("id") Long id) throws InspectionException {
		UserVo user = userService.getUser(id);
		return new Response(0, ResultCode.SUCCESS, user);
	}

	@RequestMapping(value = "/{id}/roles", method = RequestMethod.PUT)
	public Response updateUserRoles(@PathVariable("id") Long id, @RequestBody List<Long> roleIds)
			throws InspectionException {
		userService.updateUserRoles(id, roleIds);
		return new Response(0, ResultCode.SUCCESS, "");
	}

	/**
	 * 分页查询
	 * 
	 * @param userQuery
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/conditions", method = RequestMethod.GET)
	public Response findUserByConditions(UserQuery userQuery) throws Exception {
		return new Response(0, ResultCode.SUCCESS, userService.findUserByConditions(userQuery));
	}

	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public Response updatePasswordById(@RequestBody PasswordDto passwordDto)
			throws Exception {
		userService.updatePasswordById(passwordDto);
		return new Response(0, ResultCode.SUCCESS, null);
	}

	@RequestMapping(value = "/{id}/reset/password", method = RequestMethod.PUT)
	public Response resetPasswordById(@PathVariable("id") Long id) throws Exception {
		userService.resetPasswordById(id);
		return new Response(0, ResultCode.SUCCESS, null);
	}

	@RequestMapping(value = "/{id}/reviewStatus", method = RequestMethod.PUT)
	public Response updatereviewStatusById(@PathVariable("id") Long id, @RequestBody ReviewStatusDto reviewStatusDto)
			throws Exception {
		userService.updatereviewStatusById(id, reviewStatusDto);
		return new Response(0, ResultCode.SUCCESS, null);
	}
	
	/**
	 * 获取当前用户信息
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Response getUserInfo() throws Exception {
		Map<String, Object> appUserInfo = LoginUserUtils.appUserInfo();
		return new Response(0, ResultCode.SUCCESS, appUserInfo);
	}

}