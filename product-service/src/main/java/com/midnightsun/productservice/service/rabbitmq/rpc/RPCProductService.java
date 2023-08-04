package com.midnightsun.productservice.service.rabbitmq.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnightsun.productservice.service.ProductService;
import com.midnightsun.productservice.service.dto.ProductDTO;
import com.midnightsun.productservice.service.dto.external.OrderDTO;
import com.midnightsun.productservice.service.dto.external.OrderItemDTO;
import com.midnightsun.productservice.service.dto.external.OrderItemExtendedInfoDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

@Component
public class RPCProductService {

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
        //DANGER: add error handling
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

            BigDecimal orderItemQuantity = BigDecimal.valueOf(orderItem.getQuantity());
            BigDecimal productPrice = product.getPrice();
            BigDecimal totalPriceOfCurrentProduct = orderItemQuantity.multiply(productPrice);
            totalPrice = totalPrice.add(totalPriceOfCurrentProduct);
        }

        order.setTotalPrice(totalPrice);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message build = MessageBuilder.withBody(objectMapper.writeValueAsBytes(order)).andProperties(messageProperties).build();

        String replyTo = message.getMessageProperties().getReplyTo();

        rabbitTemplate.sendAndReceive("", replyTo, build);
    }
}
