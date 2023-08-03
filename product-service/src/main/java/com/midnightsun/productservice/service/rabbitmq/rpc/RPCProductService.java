package com.midnightsun.productservice.service.rabbitmq.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnightsun.productservice.service.ProductService;
import com.midnightsun.productservice.service.dto.ProductDTO;
import com.midnightsun.productservice.service.dto.external.OrderDTO;
import com.midnightsun.productservice.service.dto.external.OrderItemDTO;
import com.midnightsun.productservice.service.dto.external.OrderItemExtendedInfoDTO;
import liquibase.pro.packaged.O;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

@Component
public class RPCProductService {

    @Value("${rabbitmq.exchanges.ps_exchange}")
    private String psExchange;

    @Value("${rabbitmq.routings.ps_reply_key}")
    private String psReplyRoutingKey;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final ProductService productService;

    public RPCProductService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, ProductService productService) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.productService = productService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.ps_queue}")
    public void process(Message message) throws IOException {
        byte[] body = message.getBody();
        OrderDTO order = objectMapper.readValue(body, OrderDTO.class);

        BigDecimal totalPrice = BigDecimal.ZERO;

        Set<OrderItemDTO> orderItems = order.getOrderItems();
        for (OrderItemDTO orderItem : orderItems) {
            ProductDTO product = productService.getOne(orderItem.getProductId());

            OrderItemExtendedInfoDTO orderItemExtendedInfo = new OrderItemExtendedInfoDTO();
            orderItemExtendedInfo.setName(product.getName());
            orderItemExtendedInfo.setDescription(product.getDescription());
            orderItemExtendedInfo.setPrice(product.getPrice());

            orderItem.setOrderItemExtendedInfoDTO(orderItemExtendedInfo);

            totalPrice.add(product.getPrice());
        }

        order.setTotalPrice(totalPrice);

        Message build = MessageBuilder.withBody(objectMapper.writeValueAsBytes(order)).build();

        CorrelationData correlationData = new CorrelationData(message.getMessageProperties().getCorrelationId());
        rabbitTemplate.sendAndReceive(psExchange, psReplyRoutingKey, build, correlationData);
    }
}
