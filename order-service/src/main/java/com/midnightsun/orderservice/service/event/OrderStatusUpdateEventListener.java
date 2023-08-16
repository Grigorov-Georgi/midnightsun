package com.midnightsun.orderservice.service.event;

import com.midnightsun.orderservice.service.rabbitmq.producer.NotificationProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class OrderStatusUpdateEventListener implements ApplicationListener<OrderStatusUpdateEvent> {

    private final NotificationProducer notificationProducer;

    public OrderStatusUpdateEventListener(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    @Override
    public void onApplicationEvent(OrderStatusUpdateEvent event) {
        log.debug("Triggered order status update event from {}, on {} with order {}",
                event.getSource(),
                new Date(event.getTimestamp()).getTime(),
                event.getOrder().toString());

        //send second email that the order is APPROVED/CANCELED
        notificationProducer.sendEmailForOrderCreation(event.getOrder());
    }
}
