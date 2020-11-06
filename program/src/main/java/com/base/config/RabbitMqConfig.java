package com.base.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqConfig.class);

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setEncoding("UTF-8");
		// 使用jackson 消息转换器
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		// 消息确认，yml需要配置 publisher-confirms: true
		rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
			/**
			 * @param correlationData 唯一标识，有了这个唯一标识，我们就知道可以确认（失败）哪一条消息了
			 * @param ack
			 * @param cause
			 */
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				if (ack) {
					LOGGER.info("消息发送到exchange成功" + correlationData.getId());
				} else {
					LOGGER.info("消息发送到exchange失败");
				}
			}
		});
		// 消息发送失败返回到队列中，yml需要配置 publisher-returns: true
		// setMandatory=true,表示如果交换机根据路由找不到队列，则将数据返回。false则直接丢弃。
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
			String correlationId = message.getMessageProperties().getCorrelationIdString();
			LOGGER.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange,
					routingKey);
		});
		return rabbitTemplate;
	}

	@Bean
	public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return factory;
	}
}
