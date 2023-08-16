package com.midnightsun.orderservice.service.event;

import com.midnightsun.orderservice.service.rabbitmq.producer.NotificationProducer;
import com.midnightsun.orderservice.service.rabbitmq.saga.SagaManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class OrderCreatedEventListener implements ApplicationListener<OrderCreatedEvent> {

    private final NotificationProducer notificationProducer;
    private final SagaManager sagaManager;

    public OrderCreatedEventListener(NotificationProducer notificationProducer, SagaManager sagaManager) {
        this.notificationProducer = notificationProducer;
        this.sagaManager = sagaManager;
    }

    @Override
    public void onApplicationEvent(OrderCreatedEvent event) {
        log.debug("Triggered order created event from {}, on {} with order {}",
                event.getSource(),
                new Date(event.getTimestamp()).getTime(),
                event.getOrder().toString());

        //send first confirmation email that the order is created with status PENDING
        notificationProducer.sendEmailForOrderCreation(event.getOrder());

        //Saga pattern with choreography
        //This distributed transaction consists of 2 steps
        //Product service validate products ids and quantities
        //If validation succeed -> order status is changed to APPROVED
        //If validation fails -> order status is changed to CANCELED
        sagaManager.startSagaForProductsValidation(event.getOrder());
    }
}
