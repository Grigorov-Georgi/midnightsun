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

    //Notification service messaging variables
    @Value("${rabbitmq.queues.ns_queue}")
    private String nsQueue;

    @Value("${rabbitmq.exchanges.ns_exchange}")
    private String nsExchange;

    @Value("${rabbitmq.routings.ns_key}")
    private String nsRoutingKey;

    //Product service messaging variables
    @Value("${rabbitmq.queues.ps_queue}")
    private String psQueue;

    @Value("${rabbitmq.exchanges.ps_exchange}")
    private String psExchange;

    @Value("${rabbitmq.routings.ps_key}")
    private String psRoutingKey;

    @Bean
    public Queue nsQueue() { return new Queue(nsQueue); }

    @Bean
    public TopicExchange nsExchange() { return new TopicExchange(nsExchange); }

    @Bean
    public Binding nsBinding() {
        return BindingBuilder.bind(nsQueue())
                .to(nsExchange())
                .with(nsRoutingKey);
    }

    @Bean
    public Queue psQueue() { return new Queue(psQueue); }

    @Bean
    public TopicExchange psExchange() { return new TopicExchange(psExchange); }

    @Bean
    public Binding psBinding() {
        return BindingBuilder.bind(psQueue())
                .to(psExchange())
                .with(psRoutingKey);
    }

    //Global configs
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setUseTemporaryReplyQueues(true);
        template.setUseChannelForCorrelation(true);
        template.setReplyTimeout(5000);
        return template;
    }
}
