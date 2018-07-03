package com.cd.uap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.cd.uap.service.CustomUserService;

@SpringBootApplication
@EnableAuthorizationServer	//发送token
public class AuthorizationApplication {
	
	/**
	 * 项目启动入口
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
	}

	@Configuration
	public class LoginConfig extends WebSecurityConfigurerAdapter {


		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.authorizeRequests()
					.antMatchers("/user/regist/**").permitAll()
					.anyRequest().authenticated()
					.and()            //其他所有资源都需要认证，登陆后访问
					.csrf().disable();
		}
	}


	/**
	 * 配置自定义的ClientId、ClientSecret
	 */
	@Configuration
	public static class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {
		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private CustomUserService customUserService;
		
		@Autowired
		private MyExceptionTranslator myExceptionTranslator;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager)
				.userDetailsService(customUserService)
				.exceptionTranslator(myExceptionTranslator);
		}

		/**
		 * 配置ClientId、ClientSecret
		 */
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
					.withClient("HrYFvBvUowDnSSN3")
					.secret("NAYP0igLljhmMbarnmz4yzyydorxy48X")
					.authorizedGrantTypes("client_credentials", "password", "refresh_token")
					.scopes("all")
					.accessTokenValiditySeconds(60*60*24*7)
					.refreshTokenValiditySeconds(60*60*24*30);
		}
		
		/**
		 * 令牌校验check_token
		 */
		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.checkTokenAccess("permitAll()");
		}
	}


	@Configuration
	public static class MyExceptionTranslator implements WebResponseExceptionTranslator{

		@Override
		public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

			ResponseEntity<OAuth2Exception> esponseEntity = new ResponseEntity<OAuth2Exception>(new OAuth2Exception(e.getMessage()), HttpStatus.OK);//(new OAuth2Exception(e.getMessage()), HttpStatus.OK);
			return esponseEntity;
		}
	}
}



















