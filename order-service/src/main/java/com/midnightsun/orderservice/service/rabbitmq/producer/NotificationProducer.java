package com.midnightsun.orderservice.service.rabbitmq.producer;

import com.midnightsun.orderservice.service.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationProducer {

    @Value("${rabbitmq.exchanges.ns_exchange}")
    private String nsExchange;

    @Value("${rabbitmq.routings.ns_key}")
    private String nsRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailForOrderCreation(OrderDTO order) {
        rabbitTemplate.convertAndSend(nsExchange, nsRoutingKey, order);
    }
}
