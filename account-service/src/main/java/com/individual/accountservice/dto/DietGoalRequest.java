package com.individual.accountservice.dto;

import com.individual.accountservice.Entity.DietGoal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DietGoalRequest {
    private String userUID;
    private DietGoal dietGoal;


}
