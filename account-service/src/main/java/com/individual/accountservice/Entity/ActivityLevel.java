package com.individual.accountservice.Entity;

public enum ActivityLevel {
    SEDENTARY(1), // Little to no exercise
    LIGHTLY_ACTIVE(2), // Light exercise/sports 1-3 days a week
    MODERATELY_ACTIVE(3), // Moderate exercise/sports 3-5 days a week
    VERY_ACTIVE(4), // Hard exercise/sports 6-7 days a week
    EXTRA_ACTIVE(5); // Very hard exercise/sports & physical job or 2x training

    private final int level;

    ActivityLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
