package com.junior.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.junior.blog.common.MyPasswordEncoder;
import com.junior.blog.controller.UserController;
import com.junior.blog.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private MyPasswordEncoder myPasswordEncoder;

	@Autowired
	private UserController user;

	/**
	 * 定义加密方式
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(myPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/user/unloginRequestDeal")// 定义当需要用户登录时候，转到的登录页面。
				.successHandler(user)// 登录成功跳转接口
				.failureHandler(user)// 登录失败跳转接口
				.loginProcessingUrl("/login")// 登录校验url
				.and().authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
				.antMatchers("/user/unloginRequestDeal", "/login", "/login.html","/index.html", "/", "/js/**", "/css/**", "/view/**",
						"/layui/**", "/showImage/**")
				.permitAll().anyRequest() // 任何请求,登录后可以访问
				.authenticated().and().csrf().disable();
		http.logout().logoutSuccessHandler(user);// 注销
		http.headers().frameOptions().sameOrigin();// 允许同域名下的http页面嵌套再iframe中
	}
}
