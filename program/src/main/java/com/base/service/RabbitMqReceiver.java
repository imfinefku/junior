package com.base.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.stereotype.Component;

import com.base.domain.User;
import com.rabbitmq.client.Channel;

@Component
public class RabbitMqReceiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqReceiver.class);
	
	/**
	 * 没有queue就创建，没有exchange就创建，没有用该key绑定就绑定
	 * @param message
	 * @param channel
	 */
	@RabbitListener(bindings=@QueueBinding(
			value=@Queue("${rabbitmq.queue}"),
			key = "${rabbitmq.route}",
			exchange=@Exchange("${rabbitmq.exchange}")
			))
	public void receiveMessage(Message message,Channel channel) {
		
		Jackson2JsonMessageConverter jackson2JsonMessageConverter =new Jackson2JsonMessageConverter();
        User user = (User)jackson2JsonMessageConverter.fromMessage(message);
        LOGGER.info("收到消息:"+user.toString());
		try {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			LOGGER.error("手动确认消息失败");
		}
	}
	
	public static void main(String args[]) {
		
	}
}
