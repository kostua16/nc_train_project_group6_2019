package com.edunetcracker.billingservice.ProxyProxy.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbit.rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbit.rabbitmq.queue1}")
    private String queue1;

    @Value("${rabbit.rabbitmq.queue2}")
    private String queue2;

    @Bean
    Queue queue1() {
        return new Queue(queue1, false);
    }

    @Bean
    Queue queue2() {
        return new Queue(queue2, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding binding1(Queue queue1, DirectExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(String.valueOf(queue1));
    }
    @Bean
    Binding binding2(Queue queue2, DirectExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with(String.valueOf(queue2));
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
