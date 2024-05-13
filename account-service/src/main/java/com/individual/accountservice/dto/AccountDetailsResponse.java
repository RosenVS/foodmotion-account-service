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
public class AccountDetailsResponse {
    private UserPersonalData personalData;
    private UserSpecifications dietRestrictions;
    private DietGoal dietGoal;
}
