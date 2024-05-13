package com.individual.accountservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPreferences implements Serializable {
    private PersonalData personalData;
    private UserWeightHeight userWeightHeight;
    private DietGoal dietGoal;
    private UserSpecifications userSpecifications;
    private ActivityLevel activityLevel;

}
