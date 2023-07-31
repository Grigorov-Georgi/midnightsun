package com.midnightsun.orderservice.service;

import com.midnightsun.orderservice.config.rabbitmq.producer.RabbitMQProducer;
import com.midnightsun.orderservice.mapper.OrderMapper;
import com.midnightsun.orderservice.model.Order;
import com.midnightsun.orderservice.model.OrderItem;
import com.midnightsun.orderservice.repository.OrderItemRepository;
import com.midnightsun.orderservice.repository.OrderRepository;
import com.midnightsun.orderservice.service.dto.OrderDTO;
import com.midnightsun.orderservice.service.dto.OrderItemDTO;
import com.midnightsun.orderservice.web.exception.HttpBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final RabbitMQProducer rabbitMQProducer;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        OrderMapper orderMapper,
                        RabbitMQProducer rabbitMQProducer,
                        RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
        this.rabbitMQProducer = rabbitMQProducer;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Page<OrderDTO> getAll(Pageable pageable) {
        log.debug("Request to get all ORDERS");
        return orderRepository.findAll(pageable).map(orderMapper::toDTO);
    }

    @Transactional
    public OrderDTO getOne(UUID uuid, boolean withProducts) {
        log.debug("Request to get ORDER by ID: {}", uuid);

        //TODO: Implement internal call to product service to fetch data
        if (withProducts) {

        }

        return orderMapper.toDTO(orderRepository.findById(uuid).orElse(null));
    }

    public OrderDTO getOne(UUID uuid) {
        return this.getOne(uuid, false);
    }

    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save ORDER: {}", orderDTO);
        if (orderDTO.getId() != null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        }
        final var order = orderMapper.toEntity(orderDTO);
        return save(order);
    }

    public OrderDTO update(OrderDTO orderDTO) {
        log.debug("Request to update ORDER: {}", orderDTO);
        if (orderDTO.getId() == null) {
            throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        }
        final var order = orderMapper.toEntity(orderDTO);
        return save(order);
    }

    private OrderDTO save(Order order) {
        //TODO
//        if (!areProductAvailable()) {
        if (true) {
            throw new HttpBadRequestException("Products doesn't exists or insufficient quantity");
        }

        final Set<OrderItem> orderItemSet = order.getOrderItems();
        order.resetOrderItems();

        final var savedOrder = orderRepository.save(order);

        if (orderItemSet != null && !orderItemSet.isEmpty()) {
            orderItemSet.forEach(orderItem -> orderItem.setOrder(savedOrder));
            final var savedOrderItems = orderItemRepository.saveAll(orderItemSet);
            savedOrder.setOrderItems(Set.copyOf(savedOrderItems));
        }

        final var savedOrderDTO = orderMapper.toDTO(savedOrder);

        rabbitMQProducer.sendEmailForCreatedOrder(savedOrderDTO);

        return savedOrderDTO;
    }

    public void delete(UUID uuid) {
        log.debug("Request to delete ORDER with ID: {}", uuid);
        orderRepository.deleteById(uuid);
    }

    private void fetchProducts(OrderDTO orderDTO) {
        List<Long> productIds = orderDTO.getOrderItems().stream().map(OrderItemDTO::getProductId).collect(Collectors.toList());

        String correlationId = UUID.randomUUID().toString();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(correlationId);

        Message requestMessage = rabbitTemplate.getMessageConverter().toMessage(productIds, messageProperties);

    }
}
