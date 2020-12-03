package com.junior.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.domain.User;

@Component
public class RabbitMqSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqSender.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public boolean sendMessage(User user) {
		try {
			rabbitTemplate.convertAndSend("testExchange", "testKey", user,new CorrelationData(user.getId()));
		}catch(Exception ex) {
			LOGGER.error("发送队列消息失败",ex);
			return false;
		}
		LOGGER.info("发送队列消息成功"+user.getId());
		return true;
	}
}
