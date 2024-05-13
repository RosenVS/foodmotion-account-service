package com.individual.accountservice.Entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserWeightHeight implements Serializable {
    private double weight;
    private double height;
    private HeightMeasurement heightMeasurement;
    private WeightMeasurement weightMeasurement;
}
