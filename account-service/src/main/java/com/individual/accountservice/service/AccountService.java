package com.individual.accountservice.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.individual.accountservice.Entity.*;
import com.individual.accountservice.dto.UserPersonalDataResponse;
import com.individual.accountservice.mapping.AccountDetailsMapping;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class AccountService {
    private static final String PERSONAL_DATA_COLLECTION_NAME = "personal_data";
    private static final String DIET_RESTRICTIONS_COLLECTION_NAME = "diet_restrictions";
    private static final String DIET_GOAL_COLLECTION_NAME = "diet_goal";
    private final AccountDetailsMapping accountDetailsMapping = new AccountDetailsMapping();

    public UserPersonalDataResponse getUserAccount(String userUID) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        DocumentReference personalDataRef = dbFirestore.collection(PERSONAL_DATA_COLLECTION_NAME).document(userUID);
        ApiFuture<DocumentSnapshot> personalDataFuture = personalDataRef.get();
        DocumentSnapshot personalDataDocument = personalDataFuture.get();

        DocumentReference dietRestrictionsRef = dbFirestore.collection(DIET_RESTRICTIONS_COLLECTION_NAME).document(userUID);
        ApiFuture<DocumentSnapshot> dietRestrictionsFuture = dietRestrictionsRef.get();
        DocumentSnapshot dietRestrictionsDocument = dietRestrictionsFuture.get();

        DocumentReference dietGoalRef = dbFirestore.collection(DIET_GOAL_COLLECTION_NAME).document(userUID);
        ApiFuture<DocumentSnapshot> dietGoalFuture = dietGoalRef.get();
        DocumentSnapshot dietGoalDocument = dietGoalFuture.get();

        PersonalData personalData = accountDetailsMapping.extractPersonalData(personalDataDocument);
        UserWeightHeight userWeightHeight = accountDetailsMapping.extractUserWeightHeight(personalDataDocument);
        ActivityLevel activityLevel = accountDetailsMapping.extractActivityLevel(personalDataDocument);
        UserSpecifications userSpecifications = accountDetailsMapping.extractUserSpecifications(dietRestrictionsDocument);
        DietGoal dietGoal = accountDetailsMapping.extractDietGoal(dietGoalDocument);
        int dailyCalories = accountDetailsMapping.extractCalorieIntake(dietGoalDocument);

        return UserPersonalDataResponse.builder()
                .personalData(personalData)
                .userWeightHeight(userWeightHeight)
                .activityLevel(activityLevel)
                .userSpecifications(userSpecifications)
                .dietGoal(dietGoal)
                .calorieIntake(dailyCalories)
                .build();
    }


}
