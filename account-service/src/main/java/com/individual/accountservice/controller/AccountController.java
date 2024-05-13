package com.individual.accountservice.controller;

import com.individual.accountservice.RabbitMQ.publisher.RabbitMQJsonProducer;
import com.individual.accountservice.dto.*;
import com.individual.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    private RabbitMQJsonProducer jsonProducer;

    public AccountController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/personal_data")
    public ResponseEntity<String> setPersonalData(@RequestBody PersonalDataReqeust personalDataReqeust){
        jsonProducer.sendPersonalDataMessage(personalDataReqeust);
        return ResponseEntity.ok("Personal Data sent to RabbitMQ ...");
    }
    @PostMapping("/diet_restrictions")
    public ResponseEntity<String> setDietRestrictions(@RequestBody DietRestrictionsRequest dietRestrictionsRequest){
        jsonProducer.sendDietRestrictionsMessage(dietRestrictionsRequest);
        return ResponseEntity.ok("Diet restrictions sent to RabbitMQ ...");
    }
    @PostMapping("/diet_goal")
    public ResponseEntity<String> setDietGoal(@RequestBody DietGoalRequest dietRestrictionsRequest){
        jsonProducer.sendDietGoalMessage(dietRestrictionsRequest);
        return ResponseEntity.ok("Diet Goal sent to RabbitMQ ...");
    }


    @GetMapping("/{userUID}")
    public ResponseEntity<UserPersonalDataResponse> getAccountInfo(@PathVariable String userUID) throws InterruptedException, ExecutionException {
        UserPersonalDataResponse test = accountService.getUserAccount(userUID);
        return ResponseEntity.ok(test);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAccount(@RequestBody AccountDeletionRequest user){
        jsonProducer.handleAccountDeletion(user.getUserUID());
        jsonProducer.handleAccountAuthDeletion(user.getUserUID());
        return ResponseEntity.ok("Json message sent to RabbitMQ ...");
    }


}
