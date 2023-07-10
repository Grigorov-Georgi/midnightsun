package com.midnightsun.orderservice.config.rabbitmq.producer;

import com.midnightsun.orderservice.model.Order;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailForCreatedOrder(OrderDTO order) {
        log.debug("Sending ORDER {} to Notification Service", order.getId());
        rabbitTemplate.convertAndSend(exchange, routingKey, order);
    }
}
