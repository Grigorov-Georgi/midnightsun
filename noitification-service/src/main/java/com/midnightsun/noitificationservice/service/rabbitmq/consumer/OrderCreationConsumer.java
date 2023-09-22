package com.midnightsun.noitificationservice.service.rabbitmq.consumer;

import com.midnightsun.noitificationservice.service.NotificationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class OrderCreationConsumer {

    private final NotificationService notificationService;

    public OrderCreationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = {"${rabbitmq.queues.ns_queue}"})
    public void sendEmailForOrderCreation(MailMessage message) {
        log.debug("Received ORDER {} from Order Service", message.getOrderId());
        notificationService.sendEmail(
                message.getOrderId(),
                message.getReceiver(),
                message.getStatus(),
                message.getHtmlContent()
        );
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class MailMessage {
        private UUID orderId;
        private String receiver;
        private String status;
        private String htmlContent;
    }
}
