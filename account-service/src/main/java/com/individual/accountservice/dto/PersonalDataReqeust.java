package com.individual.accountservice.dto;

import com.individual.accountservice.Entity.ActivityLevel;
import com.individual.accountservice.Entity.PersonalData;
import com.individual.accountservice.Entity.UserWeightHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalDataReqeust {
    private String userUID;
    private PersonalData personalData;
    private UserWeightHeight userWeightHeight;
    private ActivityLevel activityLevel;

}
