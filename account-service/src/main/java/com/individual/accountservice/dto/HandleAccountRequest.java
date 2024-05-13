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
public class HandleAccountRequest {
    private String userUID;
    private PersonalData personalData;
    private UserWeightHeight userWeightHeight;
    private DietGoal dietGoal;
    private UserSpecifications userSpecifications;
    private ActivityLevel activityLevel;
}
