package com.individual.accountservice.dto;

import com.individual.accountservice.Entity.UserSpecifications;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DietRestrictionsRequest {
    private String userUID;
    private UserSpecifications userSpecifications;
}
