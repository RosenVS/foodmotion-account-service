package com.individual.accountservice.dto;

import com.individual.accountservice.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPersonalDataResponse {
    private PersonalData personalData;
    private UserSpecifications userSpecifications;
    private UserWeightHeight userWeightHeight;
    private ActivityLevel activityLevel;
    private DietGoal dietGoal;
}
