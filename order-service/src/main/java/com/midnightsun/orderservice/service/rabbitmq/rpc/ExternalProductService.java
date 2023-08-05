package com.midnightsun.orderservice.service.rabbitmq.rpc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ExternalProductService {

    @Value("${rabbitmq.routings.ps_key}")
    private String psRoutingKey;

    @Value("${rabbitmq.exchanges.ps_exchange}")
    private String psExchange;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public ExternalProductService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public List<OrderDTO> getFullOrderInformation(@NonNull List<OrderDTO> orderDTOList) {
        try {
            var body = objectMapper.writeValueAsBytes(orderDTOList);

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType("application/json");
            Message newMessage = MessageBuilder.withBody(body).andProperties(messageProperties).build();

            log.debug("Sending request to ProductService");
            Message result = rabbitTemplate.sendAndReceive(psExchange, psRoutingKey, newMessage);

            if (result != null) {
                log.debug("Received response from ProductService");
                return objectMapper.readValue(result.getBody(), new TypeReference<List<OrderDTO>>() {});
            }
            return null;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public OrderDTO getFullOrderInformation(@NonNull OrderDTO orderDTO) {
        final var result = this.getFullOrderInformation(List.of(orderDTO));
        return result != null ? result.get(0) : null;
    }
}
