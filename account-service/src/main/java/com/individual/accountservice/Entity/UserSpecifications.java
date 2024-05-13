package com.individual.accountservice.Entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserSpecifications implements Serializable {
    private boolean glutenFree;
    private boolean lactoseFree;
    private boolean vegan;
    private boolean vegetarian;
}

