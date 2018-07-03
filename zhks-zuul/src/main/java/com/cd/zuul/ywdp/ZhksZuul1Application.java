package com.cd.zuul.ywdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.cd.zuul.ywdp.filter.UserAccessFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@SpringBootApplication
@EnableZuulProxy
@EnableResourceServer
@EnableWebSecurity
public class ZhksZuul1Application extends ResourceServerConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ZhksZuul1Application.class, args);
	}

	@Bean
	public UserAccessFilter accessFilter() {
		return new UserAccessFilter();
	}


	@Override
	public void configure(HttpSecurity http) throws Exception {

		 http
			.authorizeRequests()
			//获取当前用户信息
			  .antMatchers("/api/zhksmanagement/user/login").permitAll()
			//获取医科分类下拉列表
			  .antMatchers("/api/zhksweb/classify/relateClassify/**", "/api/zhksweb/classify/firstClassify/**").permitAll()
			//首页都可以访问
			  .antMatchers("/api/zhksweb/home/**", "/api/zhksweb/report/queryReport/**", "/api/zhksweb/exam/queryExam/**").permitAll()
			//修改密码都可以访问
			  .antMatchers("/api/zhksmanagement/user/password").permitAll()

			//系统管理
				 	//用户管理
			  .antMatchers("/api/zhksmanagement/user/**").access("hasAnyAuthority('userManage')")
				 	//角色管理
			  .antMatchers("/api/zhksmanagement/role/**").access("hasAnyAuthority('roleManage')")

			//网站管理
				 	//医科分类
	  		  .antMatchers("/api/zhksweb/classify/**").access("hasAnyAuthority('classifyManager')")
				 	//资料管理
	  		  .antMatchers("/api/zhksweb/datum/addDatum/**","/api/zhksweb/datum/queryDatum/**","/api/zhksweb/datum/editDatum/**","/api/zhksweb/datum/viewDatum/**","/api/zhksweb/datum/deleteDatum/**","/api/zhksweb/datum/deleteDatums/**","/api/zhksweb/datum/startDatum/**").access("hasAnyAuthority('datumManager')")
				 	//试卷管理
	  		  .antMatchers("/api/zhksweb/exam/addExam/**","/api/exam/editExam/test/**","/api/zhksweb/exam/viewExam/**","/api/zhksweb/exam/deleteExam/**","/api/zhksweb/exam/deleteExams/**","/api/zhksweb/exam/startExam/**","/api/zhksweb/exam/addExamUser/**").access("hasAnyAuthority('examManager')")
				 	//题库管理
	  		  .antMatchers("/api/zhksweb/topic/**").access("hasAnyAuthority('topicManager')")

			//查询统计统计
				 	//在线考试统计
		      .antMatchers("/api/zhksweb/count/company/**","/api/zhksweb/count/exceptCompany/**","/api/zhksweb/count/person/**","/api/zhksweb/count/exceptPerson/**").access("hasAnyAuthority('countManager')")
				 	//在线培训统计
		      .antMatchers("/api/zhksweb/count/countTrain/**","/api/zhksweb/count/exportTrain/**").access("hasAnyAuthority('trainManager')")
				 	//培训成绩查询
		      .antMatchers("/api/zhksweb/count/queryTrain/**").access("hasAnyAuthority('trainScoreManager')")
				 	//考试成绩查询
			  .antMatchers("/api/zhksweb/count/queryScore/**").access("hasAnyAuthority('scoreManager')")

			//通知公告
				 	//共用通知公告
		      .antMatchers("/api/zhksweb/report/viewReport/**","/api/zhksweb/report/deleteReport/**","/api/zhksweb/report/deleteReports/**").access("hasAnyAuthority('systemManager','normalManager')")
				 	//管理员特有通知公告
		      .antMatchers("/api/zhksweb/report/addReport/**").access("hasAnyAuthority('systemManager')")

			//在线考试
				 	//模拟正式公用
		      .antMatchers("/api/zhksweb/exam/queryExams/**","/api/zhksweb/exam/doExam/**","/api/zhksweb/exam/putExam/**").access("hasAnyAuthority('formalManager','mockManager')")
				 	//正式特有
		      .antMatchers("/api/zhksweb/exam/doRealExam/**","/api/zhksweb/exam/editRealScore/**").access("hasAnyAuthority('formalManager')")

			//培训中心
				 	//资料学习培训
				 .antMatchers("/api/zhksweb/datum/queryDatums/**","/api/zhksweb/datum/doDatum/**","/api/zhksweb/datum/addTrain/**","/api/zhksweb/datum/downDatum/**","/api/zhksweb/datum/viewDatumTopic/**").access("hasAnyAuthority('datumTrain')");
			  //.antMatchers("/api/zhksweb/datum/queryDatums/**","/api/zhksweb/datum/doDatum/**","/api/zhksweb/datum/addTrain/**").access("hasAnyAuthority('datumTrain')");
					//题库学习培训 --> 同在线考试考试


	}
}
