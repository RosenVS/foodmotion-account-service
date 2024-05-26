package com.individual.accountservice.mapping;

import com.individual.accountservice.Entity.ActivityLevel;

public class ActivityLevelConverter {
    public static ActivityLevel convertToActivityLevel(String activityLevelStr) {
        switch (activityLevelStr) {
            case "SEDENTARY":
                return ActivityLevel.SEDENTARY;
            case "LIGHTLY_ACTIVE":
                return ActivityLevel.LIGHTLY_ACTIVE;
            case "MODERATELY_ACTIVE":
                return ActivityLevel.MODERATELY_ACTIVE;
            case "VERY_ACTIVE":
                return ActivityLevel.VERY_ACTIVE;
            case "EXTRA_ACTIVE":
                return ActivityLevel.EXTRA_ACTIVE;
            default:
                throw new IllegalArgumentException("Invalid activity level: " + activityLevelStr);
        }
    }
}
