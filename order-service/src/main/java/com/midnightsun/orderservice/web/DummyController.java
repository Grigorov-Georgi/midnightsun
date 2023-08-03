package com.midnightsun.orderservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnightsun.orderservice.service.OrderService;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DummyController {

    @Value("${rabbitmq.routings.ps_key}")
    private String psRoutingKey;

    @Value("${rabbitmq.exchanges.ps_exchange}")
    private String psExchange;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    private final OrderService orderService;

    public DummyController(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, OrderService orderService) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @GetMapping("/dummy/{id}")
    public ResponseEntity<OrderDTO> getProductName(@PathVariable UUID id) throws IOException {
        var body = objectMapper.writeValueAsBytes(orderService.getOne(id));

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message newMessage = MessageBuilder.withBody(body).andProperties(messageProperties).build();

        Message result = rabbitTemplate.sendAndReceive(psExchange, psRoutingKey, newMessage);

        if (result != null) {
            String correlationId = newMessage.getMessageProperties().getCorrelationId();

            HashMap<String, Object> headers = (HashMap<String, Object>) result.getMessageProperties().getHeaders();
            String msgId = (String) headers.get("spring_returned_message_correlation");

            if (msgId.equals(correlationId)){
                return ResponseEntity.status(HttpStatus.OK).body(objectMapper.readValue(result.getBody(), OrderDTO.class));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
