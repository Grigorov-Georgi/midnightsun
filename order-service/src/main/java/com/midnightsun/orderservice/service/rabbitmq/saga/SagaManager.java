package com.midnightsun.orderservice.service.rabbitmq.saga;

import com.midnightsun.orderservice.mapper.OrderMapper;
import com.midnightsun.orderservice.model.enums.OrderStatus;
import com.midnightsun.orderservice.repository.OrderRepository;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import com.midnightsun.orderservice.service.event.OrderStatusUpdateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class SagaManager {

    @Value("${rabbitmq.exchanges.saga-exchange}")
    private String sagaExchange;

    @Value("${rabbitmq.routings.ps_saga_key}")
    private String sagaRoutingKey;

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public SagaManager(RabbitTemplate rabbitTemplate, ApplicationEventPublisher eventPublisher, OrderMapper orderMapper, OrderRepository orderRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventPublisher = eventPublisher;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    public void startSagaForProductsValidation(OrderDTO order) {
        log.debug("Initiated products validation for order with ID: {}", order.getId());

        Map<UUID, Long> productIdQuantityMap = new HashMap<>();
        order.getOrderItems().forEach(o -> productIdQuantityMap.putIfAbsent(o.getProductId(), o.getQuantity()));

        SagaMessage sagaMessage = new SagaMessage(Boolean.TRUE, order.getId(), productIdQuantityMap);

        log.debug("Sending message {} for saga to Product Service", sagaMessage);
        rabbitTemplate.convertAndSend(sagaExchange, sagaRoutingKey, sagaMessage);
    }

    @RabbitListener(queues = {"${rabbitmq.queues.os_saga_queue}"})
    private void finaliseSagaForProductsValidation(SagaMessage sagaMessage) {
        log.debug("Received sagaMessage {} for saga from Product Service", sagaMessage);
        final var orderId = sagaMessage.getOrderId();
        final var order = orderRepository.findById(orderId).get();

        if (sagaMessage.getIsTransactionSuccessful()) {
            log.debug("Changing status to APPROVED for order with ID {}", orderId);
            order.setStatus(OrderStatus.APPROVED);
        } else {
            log.debug("Changing status to CANCELED for order with ID {}", orderId);
            order.setStatus(OrderStatus.CANCELED);
        }

        final var savedOrder = orderRepository.save(order);
        OrderStatusUpdateEvent event = new OrderStatusUpdateEvent(this, orderMapper.toDTO(savedOrder));
        eventPublisher.publishEvent(event);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SagaMessage {
        private Boolean isTransactionSuccessful;
        private UUID orderId;
        private Map<UUID, Long> productIdQuantityMap;
    }
}