package com.cd.uap.config;

import java.util.Collection;

import com.cd.uap.util.MyPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cd.uap.service.CustomUserService;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserService userService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 自定义验证方式
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		UserDetails user = userService.loadUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名错误");
		}

		try {
			password = MyPasswordEncoder.encode(password);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new UsernameNotFoundException("密码错误");
		}
		// 加密过程在这里体现

		if (!password.equals(user.getPassword())) {
			throw new UsernameNotFoundException("密码错误");
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}