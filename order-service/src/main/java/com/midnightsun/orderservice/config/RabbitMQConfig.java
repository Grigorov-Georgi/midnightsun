package com.midnightsun.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queues.ns_queue}")
    private String nsQueue;

    @Value("${rabbitmq.exchanges.ns_exchange}")
    private String nsExchange;

    @Value("${rabbitmq.routings.ns_key}")
    private String nsRoutingKey;

    @Bean
    public Queue nsQueue() {
        return new Queue(nsQueue);
    }

    @Bean
    public TopicExchange nsExchange() {
        return new TopicExchange(nsExchange);
    }

    @Bean
    public Binding nsBinding() {
        return BindingBuilder.bind(nsQueue())
                .to(nsExchange())
                .with(nsRoutingKey);
    }

    //Global configs
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
