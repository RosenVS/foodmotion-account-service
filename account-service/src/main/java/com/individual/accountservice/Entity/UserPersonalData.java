package com.individual.accountservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPersonalData {
    private PersonalData personalData;
    private UserWeightHeight userWeightHeight;
    private ActivityLevel activityLevel;
}
