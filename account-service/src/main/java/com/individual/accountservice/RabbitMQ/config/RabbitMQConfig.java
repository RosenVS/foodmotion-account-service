package com.individual.accountservice.RabbitMQ.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// Start
// docker pull rabbitmq:3.13-management
// docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.exchange.string.name}")
    private String exchangeString;

    @Value("${rabbitmq.queue.string.name}")
    private String queueNameString;

    @Value("${rabbitmq.routing.string.key}")
    private String routingKeyString;

    @Value("${rabbitmq.exchange.json.name}")
    private String exchangeJson;

    @Value("${rabbitmq.queue.json.name}")
    private String queueNameJson;

    @Value("${rabbitmq.routing.json.key}")
    private String routingKeyJson;

    @Value("${rabbitmq.personaldata.exchange.json.name}")
    private String personalDataExchangeJson;

    @Value("${rabbitmq.personaldata.queue.json.name}")
    private String personalDataQueueJson;

    @Value("${rabbitmq.personaldata.routing.json.key}")
    private String personalDataRoutingKeyJson;

    @Value("${rabbitmq.dietrestrictions.exchange.json.name}")
    private String dietRestrictionsExchangeJson;

    @Value("${rabbitmq.dietrestrictions.queue.json.name}")
    private String dietRestrictionsQueueJson;

    @Value("${rabbitmq.dietrestrictions.routing.json.key}")
    private String dietRestrictionsRoutingKeyJson;

    @Value("${rabbitmq.dietgoal.exchange.json.name}")
    private String dietgoalExchangeJson;

    @Value("${rabbitmq.dietgoal.queue.json.name}")
    private String dietgoalQueueJson;

    @Value("${rabbitmq.dietgoal.routing.json.key}")
    private String dietgoalRoutingKeyJson;



    @Value("${rabbitmq.accountdeletion.exchange.json.name}")
    private String accountDetailsDeletionExchangeJson;

    @Value("${rabbitmq.accountdeletion.queue.json.name}")
    private String accountDetailsDeletionQueueJson;

    @Value("${rabbitmq.accountdeletion.routing.json.key}")
    private String accountDetailsDeletionRoutingKeyJson;


    @Value("${rabbitmq.accountauthdeletion.exchange.json.name}")
    private String accountAuthDeletionExchangeJson;

    @Value("${rabbitmq.accountauthdeletion.queue.json.name}")
    private String accountAuthDeletionQueueJson;

    @Value("${rabbitmq.accountauthdeletion.routing.json.key}")
    private String accountAuthDeletionRoutingKeyJson;




    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue queueString() {
        return new Queue(queueNameString, true);
    }

    @Bean
    public Queue queueJson() {
        return new Queue(queueNameJson, true);
    }

    @Bean
    public TopicExchange exchangeString() {
        return new TopicExchange(exchangeString);
    }

    @Bean
    public TopicExchange exchangeJson() {
        return new TopicExchange(exchangeJson);
    }

    @Bean
    public Queue queuePersonalDataJson() {
        return new Queue(personalDataQueueJson, true);
    }

    @Bean
    public TopicExchange exchangePersonalDataString() {
        return new TopicExchange(personalDataExchangeJson);
    }

    @Bean
    public Queue queueDietRestrictionsJson() {
        return new Queue(dietRestrictionsQueueJson, true);
    }

    @Bean
    public TopicExchange exchangeDietRestrictionsString() {
        return new TopicExchange(dietRestrictionsExchangeJson);
    }

    @Bean
    public Queue queueDietGoalJson() {
        return new Queue(dietgoalQueueJson, true);
    }

    @Bean
    public TopicExchange exchangeDietGoalString() {
        return new TopicExchange(dietgoalExchangeJson);
    }

    @Bean
    public Queue queueAccountDeletionJson() {
        return new Queue(accountDetailsDeletionQueueJson, true);
    }

    @Bean
    public TopicExchange exchangeAccountDeletionJson() {
        return new TopicExchange(accountDetailsDeletionExchangeJson);
    }

    @Bean
    public Queue queueAccountAuthDeletionJson() {
        return new Queue(accountAuthDeletionQueueJson, true);
    }

    @Bean
    public TopicExchange exchangeAccountAuthDeletionJson() {
        return new TopicExchange(accountAuthDeletionExchangeJson);
    }




    @Bean
    public Binding bindingString(Queue queueString, TopicExchange exchangeString) {
        return BindingBuilder.bind(queueString).to(exchangeString).with(routingKeyString);
    }

    @Bean
    public Binding bindingJson(Queue queueJson, TopicExchange exchangeJson) {
        return BindingBuilder.bind(queueJson).to(exchangeJson).with(routingKeyJson);
    }

    @Bean
    public Binding bindingPersonalDataJson(Queue queuePersonalDataJson, TopicExchange exchangePersonalDataString) {
        return BindingBuilder.bind(queuePersonalDataJson).to(exchangePersonalDataString).with(personalDataRoutingKeyJson);
    }

    @Bean
    public Binding bindingDietRestrictionsJson(Queue queueDietRestrictionsJson, TopicExchange exchangeDietRestrictionsString) {
        return BindingBuilder.bind(queueDietRestrictionsJson).to(exchangeDietRestrictionsString).with(dietRestrictionsRoutingKeyJson);
    }

    @Bean
    public Binding bindingDietGoalJson(Queue queueDietGoalJson, TopicExchange exchangeDietGoalString) {
        return BindingBuilder.bind(queueDietGoalJson).to(exchangeDietGoalString).with(dietgoalRoutingKeyJson);
    }
    @Bean
    public Binding bindingAccountDeletionJson(Queue queueAccountDeletionJson, TopicExchange exchangeAccountDeletionJson) {
        return BindingBuilder.bind(queueAccountDeletionJson).to(exchangeAccountDeletionJson).with(accountDetailsDeletionRoutingKeyJson);
    }

    @Bean
    public Binding bindingAccountAuthDeletionJson(Queue queueAccountAuthDeletionJson, TopicExchange exchangeAccountAuthDeletionJson) {
        return BindingBuilder.bind(queueAccountAuthDeletionJson).to(exchangeAccountAuthDeletionJson).with(accountAuthDeletionRoutingKeyJson);
    }


}