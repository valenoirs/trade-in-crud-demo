package com.example.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTradeResponse {

    private String id;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

    private String inspectorName;

    private String inspectorPhone;

    private String inspectorEmail;

    private String brand;

    private String model;

    private String type;

    private String year;

    private Integer mileage;

    private String fuel;

    private String color;

    private String inspectionLocation;
}
