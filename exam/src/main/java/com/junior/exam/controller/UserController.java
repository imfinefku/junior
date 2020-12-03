package com.junior.exam.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.exam.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDetailsServiceImpl service;

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/login")
	public void login(HttpServletRequest request) throws Throwable {
		
	}
}
