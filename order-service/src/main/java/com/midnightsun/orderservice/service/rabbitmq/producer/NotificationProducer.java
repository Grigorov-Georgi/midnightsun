package com.midnightsun.orderservice.service.rabbitmq.producer;

import com.midnightsun.orderservice.service.dto.OrderDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;

@Slf4j
@Service
public class NotificationProducer {

    @Value("${rabbitmq.exchanges.ns_exchange}")
    private String nsExchange;

    @Value("${rabbitmq.routings.ns_key}")
    private String nsRoutingKey;

    private final TemplateEngine templateEngine;

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(TemplateEngine templateEngine, RabbitTemplate rabbitTemplate) {
        this.templateEngine = templateEngine;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmailForOrderCreation(OrderDTO order) {
        MailMessage mailMessage = new MailMessage(order, generateHtml(order));
        rabbitTemplate.convertAndSend(nsExchange, nsRoutingKey, mailMessage);
    }

    private String generateHtml(OrderDTO orderDTO) {
        Context context = new Context();
        context.setVariable("order", orderDTO);
        return templateEngine.process("created-order.html", context);
    }

    @Getter
    @Setter
    private static class MailMessage {
        private UUID orderId;
        private String receiver;
        private String status;
        private String htmlContent;

        public MailMessage(OrderDTO order, String htmlContent) {
            this.orderId = validateOrderId(order);
            this.receiver = validateReceiver(order);
            this.status = validateStatus(order);
            this.htmlContent = htmlContent;
        }

        private UUID validateOrderId(OrderDTO orderDTO) {
            if (orderDTO.getId() != null) {
                return orderDTO.getId();
            } else {
                throw new IllegalStateException("Order ID is missing");
            }
        }

        private String validateReceiver(OrderDTO orderDTO) {
            if (orderDTO.getCreatedBy() != null) {
                return orderDTO.getCreatedBy();
            } else {
                throw new IllegalStateException("Customer is missing");
            }
        }

        private String validateStatus(OrderDTO orderDTO) {
            if (orderDTO.getStatus() != null) {
                return orderDTO.getStatus().toString();
            } else {
                throw new IllegalStateException("Status is missing");
            }
        }
    }
}
