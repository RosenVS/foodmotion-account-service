package com.individual.accountservice.RabbitMQ.consumer;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.individual.accountservice.Entity.*;
import com.individual.accountservice.dto.DietGoalRequest;
import com.individual.accountservice.dto.DietRestrictionsRequest;
import com.individual.accountservice.dto.PersonalDataReqeust;
import com.individual.accountservice.mapping.ActivityLevelConverter;
import com.individual.accountservice.mapping.CalorieCalculator;
import org.slf4j.Logger;
import com.google.firestore.v1.Value;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);
    private static final String PERSONAL_DATA_COLLECTION_NAME = "personal_data";
    private static final String DIET_RESTRICTIONS_COLLECTION_NAME = "diet_restrictions";
    private static final String DIET_GOAL_COLLECTION_NAME = "diet_goal";


    @RabbitListener(queues = "${rabbitmq.queue.string.name}")
    public void receiveMessage(String userUID) throws ExecutionException, InterruptedException {
        LOGGER.info(String.format("Received message -> %s", userUID));
        Firestore dbFirestore = FirestoreClient.getFirestore();

        UserPersonalData personalData = UserPersonalData.builder()
                .personalData(PersonalData.builder()
                        .age(21) // Set the user's age
                        .gender("female") // Set the user's gender
                        .build())
                .userWeightHeight(UserWeightHeight.builder()
                        .weight(150.0) // Change weight to 71.25 kg
                        .height(50.0)
                        .heightMeasurement(HeightMeasurement.CM)
                        .weightMeasurement(WeightMeasurement.KG)
                        .build())
                .activityLevel(ActivityLevel.MODERATELY_ACTIVE) // Set the activity level
                .build();
        dbFirestore.collection(PERSONAL_DATA_COLLECTION_NAME).document(userUID).set(personalData);


        UserSpecifications userSpecifications = UserSpecifications.builder()
                        .glutenFree(false)
                        .lactoseFree(false)
                        .vegan(false)
                        .vegetarian(false)
                        .build();
        dbFirestore.collection(DIET_RESTRICTIONS_COLLECTION_NAME).document(userUID).set(userSpecifications);

        UserDietGoal userDietGoal = UserDietGoal.builder()
                .dietGoal(DietGoal.MAINTAIN_WEIGHT)
                .dailyCalories(2500)
                .build();
        dbFirestore.collection(DIET_GOAL_COLLECTION_NAME).document(userUID).set(userDietGoal);


    }


    @RabbitListener(queues = "${rabbitmq.personaldata.queue.json.name}")
    public void receiveJsonMessage(PersonalDataReqeust personalDataReqeust) throws ExecutionException, InterruptedException {
        LOGGER.info(String.format("Received personaldata message -> %s", personalDataReqeust.toString()));

        Firestore dbFirestore = FirestoreClient.getFirestore();
        UserPersonalData userPersonalData = UserPersonalData.builder().personalData(personalDataReqeust.getPersonalData()).activityLevel(personalDataReqeust.getActivityLevel()).userWeightHeight(personalDataReqeust.getUserWeightHeight()).build();
        dbFirestore.collection(PERSONAL_DATA_COLLECTION_NAME).document(personalDataReqeust.getUserUID()).set(userPersonalData);

    }

    @RabbitListener(queues = "${rabbitmq.dietrestrictions.queue.json.name}")
    public void receiveDietRestrictionsJsonMessage(DietRestrictionsRequest dietRestrictionsRequest) throws ExecutionException, InterruptedException {
        LOGGER.info(String.format("Received dietRestrictionsRequest message -> %s", dietRestrictionsRequest.toString()));

        Firestore dbFirestore = FirestoreClient.getFirestore();
        UserDietRestrictions userDietRestrictions = UserDietRestrictions.builder().userSpecifications(dietRestrictionsRequest.getUserSpecifications()).build();
        dbFirestore.collection(DIET_RESTRICTIONS_COLLECTION_NAME).document(dietRestrictionsRequest.getUserUID()).set(userDietRestrictions.getUserSpecifications());

    }

//    @RabbitListener(queues = "${rabbitmq.dietgoal.queue.json.name}")
//    public void receiveDietGoalJsonMessage(DietGoalRequest dietGoalRequest) throws ExecutionException, InterruptedException {
//        LOGGER.info(String.format("Received Diet goal message -> %s", dietGoalRequest.toString()));
//
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<DocumentSnapshot> calculatingNutrition =dbFirestore.collection(PERSONAL_DATA_COLLECTION_NAME).document(dietGoalRequest.getUserUID()).get();
//        DocumentSnapshot document = calculatingNutrition.get();
//        LOGGER.info(document.toString());
//
//        UserDietGoal userDietGoal = UserDietGoal.builder().dietGoal(dietGoalRequest.getDietGoal()).build();
//
//        dbFirestore.collection(DIET_GOAL_COLLECTION_NAME).document(dietGoalRequest.getUserUID()).set(userDietGoal);
//
//    }


    @RabbitListener(queues = "${rabbitmq.dietgoal.queue.json.name}")
    public void receiveDietGoalJsonMessage(DietGoalRequest dietGoalRequest) throws ExecutionException, InterruptedException {
        LOGGER.info(String.format("Received Diet goal message -> %s", dietGoalRequest.toString()));

        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentSnapshot> calculatingNutrition = dbFirestore.collection(PERSONAL_DATA_COLLECTION_NAME).document(dietGoalRequest.getUserUID()).get();
        DocumentSnapshot document = calculatingNutrition.get();
        LOGGER.info(document.toString());


        Map<String, Object> userData = document.getData();
        Map<String, Object> personalData = (Map<String, Object>) userData.get("personalData");
        Map<String, Object> userWeightHeight = (Map<String, Object>) userData.get("userWeightHeight");
        String activityLevelStr = (String) userData.get("activityLevel");
        ActivityLevel activityLevel = ActivityLevelConverter.convertToActivityLevel(activityLevelStr);

        double dailyCalories = CalorieCalculator.calculateDailyCalories(personalData, userWeightHeight, activityLevel, dietGoalRequest.getDietGoal());
        int dailyCaloriesInt = (int) Math.round(dailyCalories); // Round and convert to int

        LOGGER.info(String.valueOf(dailyCalories));
        UserDietGoal userDietGoal = UserDietGoal.builder()
                .dietGoal(dietGoalRequest.getDietGoal())
                .dailyCalories(dailyCaloriesInt)
                .build();

        dbFirestore.collection(DIET_GOAL_COLLECTION_NAME).document(dietGoalRequest.getUserUID()).set(userDietGoal);
    }

    // Method to calculate suggested calorie intake
    private int calculateCalorieIntake(double height, double weight, ActivityLevel activityLevel, int age, String gender, DietGoal dietGoal) {
        // Perform calorie calculation based on user data and diet goal
        // You can use various formulas or libraries to perform this calculation
        // Here's a simple example using some arbitrary calculations
        int baseCalories = (int) (10 * weight + 6.25 * height - 5 * age + (gender.equals("male") ? 5 : -161));
        int activityMultiplier = activityLevel.getLevel(); // You can define appropriate multipliers for activity levels
        int calorieIntake;

        switch (dietGoal) {
            case MAINTAIN_WEIGHT:
                calorieIntake = (int) (baseCalories * activityMultiplier);
                break;
            case LOSE_WEIGHT:
                calorieIntake = (int) (baseCalories * activityMultiplier * 0.8); // Example: Reduce 20% for weight loss
                break;
            case GAIN_WEIGHT:
                calorieIntake = (int) (baseCalories * activityMultiplier * 1.2); // Example: Increase 20% for weight gain
                break;
            default:
                calorieIntake = baseCalories;
                break;
        }

        return calorieIntake;
    }





    @RabbitListener(queues = "${rabbitmq.accountdeletion.queue.json.name}")
    public void recieveAccountDeletionMessage(String userUID) throws ExecutionException, InterruptedException {
        LOGGER.info(String.format("Deleting user account message -> %s", userUID));

        Firestore dbFirestore = FirestoreClient.getFirestore();

        dbFirestore.collection(DIET_GOAL_COLLECTION_NAME).document(userUID).delete();
        dbFirestore.collection(DIET_RESTRICTIONS_COLLECTION_NAME).document(userUID).delete();
        dbFirestore.collection(PERSONAL_DATA_COLLECTION_NAME).document(userUID).delete();
        dbFirestore.collection(PERSONAL_DATA_COLLECTION_NAME).document(userUID).delete();

    }


}