package com.individual.accountservice.mapping;

import com.individual.accountservice.Entity.ActivityLevel;
import com.individual.accountservice.Entity.DietGoal;

import java.util.Map;

public class CalorieCalculator {

    public static double calculateDailyCalories(Map<String, Object> personalData, Map<String, Object> userWeightHeight,
                                                ActivityLevel activityLevel, DietGoal dietGoal) {
        long ageLong = (long) personalData.get("age");
        int age = (int) ageLong;
        String gender = (String) personalData.get("gender");
        double height = (double) userWeightHeight.get("height");
        double weight = (double) userWeightHeight.get("weight");

        // Calculate BMR based on gender
        double bmr;
        if (gender.equalsIgnoreCase("male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        // Adjust BMR based on activity level
        double activityFactor;
        switch (activityLevel) {
            case SEDENTARY:
                activityFactor = 1.2;
                break;
            case LIGHTLY_ACTIVE:
                activityFactor = 1.375;
                break;
            case MODERATELY_ACTIVE:
                activityFactor = 1.55;
                break;
            case VERY_ACTIVE:
                activityFactor = 1.725;
                break;
            case EXTRA_ACTIVE:
                activityFactor = 1.9;
                break;
            default:
                activityFactor = 1.2; // Default to sedentary if activity level is unknown
        }

        double dailyCalories = bmr * activityFactor;

        // Adjust calorie intake based on diet goal
        switch (dietGoal) {
            case LOSE_WEIGHT:
                dailyCalories -= 500; // Adjust as needed for desired weight loss rate
                break;
            case GAIN_WEIGHT:
                dailyCalories += 250; // Adjust as needed for desired weight gain rate
                break;
            case MAINTAIN_WEIGHT:
            default:
                // No adjustment needed for maintaining weight
                break;
        }

        return dailyCalories;
    }
}