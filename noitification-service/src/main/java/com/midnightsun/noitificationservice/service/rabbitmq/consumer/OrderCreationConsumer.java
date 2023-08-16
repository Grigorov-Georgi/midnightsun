package com.midnightsun.noitificationservice.service.rabbitmq.consumer;

import com.midnightsun.noitificationservice.service.NotificationService;
import com.midnightsun.noitificationservice.service.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Slf4j
@Component
public class OrderCreationConsumer {

    private final NotificationService notificationService;

    public OrderCreationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = {"${rabbitmq.queues.ns_queue}"})
    public void sendEmailForOrderCreation(OrderDTO order) throws MessagingException {
        log.debug("Received ORDER {} from Order Service", order.getId());
        notificationService.sendEmail(order);
    }
}
