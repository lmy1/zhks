package com.cd.uap.service;

import java.util.*;

import com.cd.uap.bean.ReviewStatusCode;
import com.cd.uap.bean.dto.RegistUserDto;
import com.cd.uap.dao.OrgDao;
import com.cd.uap.dao.RoleDao;
import com.cd.uap.dao.UserRoleDao;
import com.cd.uap.entity.*;
import com.cd.uap.util.IdCardValidateUtil;
import com.cd.uap.util.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cd.uap.dao.UserDao;

import javax.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private OrgDao orgDao;

	@Autowired
//	private RoleDao roleDao;
	private UserRoleDao userRoleDao;

	/**
	 * 将结果返回
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public String getUserByUsername(String username) throws UsernameNotFoundException {

		Map<String, Object> map = new HashMap<>();

		User user = userDao.findByIdCard(username);

		if (null == user) {
			throw new UsernameNotFoundException("该用户不存在");
		}


		//校验审核状态
		if (!ReviewStatusCode.PASS.getCode().equals(user.getReviewStatus())) {
			if (ReviewStatusCode.UNREVIEW.getCode().equals(user.getReviewStatus())) {
				throw new UsernameNotFoundException("用户正在审核中");
			} else if (ReviewStatusCode.UNPASS.getCode().equals(user.getReviewStatus())) {
				throw new UsernameNotFoundException("用户审核未通过");
			} else {
				throw new UsernameNotFoundException("审核状态错误");
			}
		}

		Set<Role> roles = user.getRoles();
		if (roles == null || roles.size() == 0) {
			throw new UsernameNotFoundException("该用户没有绑定人员类型");
		}

		map.put("id", user.getId().toString());
		map.put("name", user.getName().toString());
//		map.put("telephone", user.getTelephone());
//		map.put("sex", user.getSex());
//		map.put("idCard", user.getIdCard());
//		map.put("createdDate", user.getCreatedDate());
//		map.put("reviewStatus", user.getReviewStatus());
//		map.put("createdUserId", user.getCreatedUserId());
//		map.put("unreviewReason", user.getUnreviewReason());

		List<Long> roleList = new ArrayList<>();
		HashSet<String> authorityList = new HashSet<>();

		for (Role role : roles) {
			roleList.add(role.getId());
			Set<Authority> authoritys = role.getAuthoritys();
			if (null != authoritys && authoritys.size() > 0) {
				for (Authority authority : authoritys) {
					authorityList.add(authority.getAuthorityIdentification());
				}
			}
		}
		map.put("roleIds", roleList);
		map.put("authoritys", authorityList);

		Org org = user.getOrg();
		if (null == org) {
			throw new UsernameNotFoundException("该用户没有绑定单位");
		}
		map.put("orgCode", user.getOrg().getOrgCode().toString());

		String userJson = JSON.toJSONString(map);
		return userJson;
	}

	/**
	 * 注册
	 *
	 * @param registUserDto
	 * @throws Exception
	 */
	@Transactional
	public void registUser(RegistUserDto registUserDto) throws Exception {

		//校验数据格式
		if (!IdCardValidateUtil.isIdCard01(registUserDto.getIdCard())) {
			throw new Exception("身份证号码格式错误");
		}
		//校验是否已经存在
		User isExist = userDao.findByIdCard(registUserDto.getIdCard());
		if(null != isExist) {
			throw new Exception("该身份证号已经被注册");
		}
		//工作单位转换
		Org oldOrg = orgDao.findByOrgCode(registUserDto.getOrgCode());
		if (oldOrg == null){
			throw new Exception("该单位不存在");
		}
		//保存并设置默认值
		User user = new User();
		BeanUtils.copyProperties(registUserDto,user);
		user.setCreatedDate(new Date());
		user.setOrg(oldOrg);
		user.setReviewStatus(0L);
		user.setPassword(MD5Util.md5Digest("88888888"));		//设置默认密码
		userDao.save(user);

		//TODO: 分配注册人员默认角色
		UserRole userRole = new UserRole();
		userRole.setUserId(user.getId());
		userRole.setRoleId(2L);
		userRoleDao.save(userRole);

	}

}
