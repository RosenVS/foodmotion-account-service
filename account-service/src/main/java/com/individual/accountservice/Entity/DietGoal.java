package com.individual.accountservice.Entity;

public enum DietGoal {
    MAINTAIN_WEIGHT("MAINTAIN_WEIGHT"),
    LOSE_WEIGHT("LOSE_WEIGHT"),
    GAIN_WEIGHT("GAIN_WEIGHT");

    private final String value;

    DietGoal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
