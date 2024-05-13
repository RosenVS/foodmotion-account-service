package com.individual.accountservice.RabbitMQ.publisher;

import com.individual.accountservice.dto.DietGoalRequest;
import com.individual.accountservice.dto.DietRestrictionsRequest;
import com.individual.accountservice.dto.PersonalDataReqeust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.string.name}")
    private String exchange_string;

    @Value("${rabbitmq.routing.string.key}")
    private String routingJsonKey_string;

    @Value("${rabbitmq.exchange.json.name}")
    private String exchange_json;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey_json;

    @Value("${rabbitmq.personaldata.exchange.json.name}")
    private String personalDataExchange_json;

    @Value("${rabbitmq.personaldata.routing.json.key}")
    private String personalDataJsonKey_json;

    @Value("${rabbitmq.dietrestrictions.exchange.json.name}")
    private String dietRestrictionsExchange_json;

    @Value("${rabbitmq.dietrestrictions.routing.json.key}")
    private String dietRestrictionsJsonKey_json;

    @Value("${rabbitmq.dietgoal.exchange.json.name}")
    private String dietGoalExchange_json;

    @Value("${rabbitmq.dietgoal.routing.json.key}")
    private String dietGoalJsonKey_json;

    @Value("${rabbitmq.accountdeletion.exchange.json.name}")
    private String accountDeletionExchange_json;

    @Value("${rabbitmq.accountdeletion.routing.json.key}")
    private String accountDeletionJsonKey_json;


    @Value("${rabbitmq.accountauthdeletion.exchange.json.name}")
    private String accountAuthDeletionExchange_json;

    @Value("${rabbitmq.accountauthdeletion.routing.json.key}")
    private String accountAuthDeletionJsonKey_json;



    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);
    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void registerAccountDetailsMessage (String userUID){
        LOGGER.info(String.format("Register default account Details Message send -> %s", userUID));
        rabbitTemplate.convertAndSend(exchange_string, routingJsonKey_string, userUID);
    }
    public void sendPersonalDataMessage (PersonalDataReqeust personalDataReqeust){
        LOGGER.info(String.format("String Message send -> %s", personalDataReqeust.toString()));
        rabbitTemplate.convertAndSend(personalDataExchange_json, personalDataJsonKey_json, personalDataReqeust);
    }
    public void sendDietRestrictionsMessage (DietRestrictionsRequest dietRestrictionsRequest){
        LOGGER.info(String.format("String Message send diet -> %s", dietRestrictionsRequest.toString()));
        rabbitTemplate.convertAndSend(dietRestrictionsExchange_json, dietRestrictionsJsonKey_json, dietRestrictionsRequest);
    }
    public void sendDietGoalMessage(DietGoalRequest dietGoalRequest){
        LOGGER.info(String.format("String Message send diet -> %s", dietGoalRequest.toString()));
        rabbitTemplate.convertAndSend(dietGoalExchange_json, dietGoalJsonKey_json, dietGoalRequest);
    }


    public void handleAccountDeletion (String userUID){
        LOGGER.info(String.format("Account deletion message send -> %s", userUID));
        rabbitTemplate.convertAndSend(accountDeletionExchange_json, accountDeletionJsonKey_json, userUID);
    }

    public void handleAccountAuthDeletion (String userUID){
        LOGGER.info(String.format("Account auth deletion message send -> %s", userUID));
        rabbitTemplate.convertAndSend(accountAuthDeletionExchange_json, accountAuthDeletionJsonKey_json, userUID);
    }


}

