package com.midnightsun.orderservice.service.event;

import com.midnightsun.orderservice.service.dto.OrderDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderCreatedEvent extends ApplicationEvent {
    private final OrderDTO order;

    public OrderCreatedEvent(Object source, OrderDTO order){
        super(source);
        this.order = order;
    }
}