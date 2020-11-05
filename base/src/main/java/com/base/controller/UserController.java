package com.base.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.domain.User;
import com.base.service.RabbitMqSender;
import com.base.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;
	@Autowired
	private RabbitMqSender rabbitMqSender;

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/test")
	public void test(HttpServletRequest request) throws Throwable {
		service.test();
	}
	
	/**
	 * rabbitmq发送消息
	 * @param request
	 */
	@GetMapping("/rabbitmqSender")
	public void rabbitmqSender(HttpServletRequest request){
		User user=new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("name");
		user.setPassword("password");
		user.setUsername("username");
		rabbitMqSender.sendMessage(user);
	}
}
