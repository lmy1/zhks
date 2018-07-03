package com.cd.uap.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import com.cd.uap.dao.RoleDao;
import com.cd.uap.entity.Authority;
import com.cd.uap.entity.Role;
import com.cd.uap.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.cd.uap.dao.UserDao;

@Service
public class
CustomUserService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserService userService;


	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (null == username) {
			throw new UsernameNotFoundException("用户名不能为空");
		}
		

		// 如果是用户名密码方式登录
		User user = userDao.findByIdCard(username);
		if (null == user) {
			throw new UsernameNotFoundException("用户名不存在");
		}

		String userJson = userService.getUserByUsername(username);
		// 此处将权限信息添加到 authorities 对象中
		List<GrantedAuthority> authorityList = getAuthorities(user);
		return new org.springframework.security.core.userdetails.User(userJson, user.getPassword(), authorityList);


	}

	/**
	 * 通过user对象得到Authority
	 * @param user
	 * @return
	 */
	private List<GrantedAuthority> getAuthorities(User user) throws UsernameNotFoundException{

		Set<Role> roles = user.getRoles();
		//如果人员类型为空，报错
		if (null == roles && roles.size() == 0) {
			throw new UsernameNotFoundException("用户角色不能为空");
		}

		Set<String> authoritysSet = new HashSet<>();

		for (Role role : roles) {
			Set<Authority> authoritys = role.getAuthoritys();
			for (Authority authority : authoritys) {
				authoritysSet.add(authority.getAuthorityIdentification());
			}

		}
		String authoritysString= authoritysSet.toString();
		authoritysString = authoritysString.substring(1,authoritysString.length()-1);

		List<GrantedAuthority> allAuthorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritysString);
		return allAuthorityList;
		
	}

}
