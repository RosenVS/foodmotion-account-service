package com.individual.accountservice.mapping;

import com.google.cloud.firestore.DocumentSnapshot;
import com.individual.accountservice.Entity.*;

import java.util.Map;


public class AccountDetailsMapping {
    public PersonalData extractPersonalData(DocumentSnapshot document) {
        Map<String, Object> personalDataMap = (Map<String, Object>) document.get("personalData");
        int age = ((Long) personalDataMap.get("age")).intValue();
        String gender = (String) personalDataMap.get("gender");
        return PersonalData.builder()
                .age(age)
                .gender(gender)
                .build();
    }
    public UserSpecifications extractUserSpecifications(DocumentSnapshot document) {
        boolean glutenFree = document.getBoolean("glutenFree");
        boolean lactoseFree = document.getBoolean("lactoseFree");
        boolean vegan = document.getBoolean("vegan");
        boolean vegetarian = document.getBoolean("vegetarian");

        return UserSpecifications.builder()
                .glutenFree(glutenFree)
                .lactoseFree(lactoseFree)
                .vegan(vegan)
                .vegetarian(vegetarian)
                .build();
    }
    public DietGoal extractDietGoal(DocumentSnapshot document) {
        String dietGoal = (String) document.getString("dietGoal");
        return DietGoal.valueOf(dietGoal);
    }



    public UserWeightHeight extractUserWeightHeight(DocumentSnapshot document) {
        Map<String, Object> userWeightHeightMap = (Map<String, Object>) document.get("userWeightHeight");
        double weight = (double) userWeightHeightMap.get("weight");
        double height = (double) userWeightHeightMap.get("height");
        String heightMeasurement = (String) userWeightHeightMap.get("heightMeasurement");
        String weightMeasurement = (String) userWeightHeightMap.get("weightMeasurement");
        return UserWeightHeight.builder()
                .weight(weight)
                .height(height)
                .heightMeasurement(HeightMeasurement.valueOf(heightMeasurement))
                .weightMeasurement(WeightMeasurement.valueOf(weightMeasurement))
                .build();
    }

    public ActivityLevel extractActivityLevel(DocumentSnapshot document) {
        String activityLevelStr = (String) document.get("activityLevel");
        return ActivityLevel.valueOf(activityLevelStr);
    }


}
