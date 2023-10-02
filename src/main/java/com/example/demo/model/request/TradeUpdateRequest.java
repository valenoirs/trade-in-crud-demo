package com.example.demo.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeUpdateRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String inspectorName;

    @NotBlank
    private String inspectorPhone;

    @NotBlank
    private String inspectorEmail;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String type;

    @NotBlank
    private String year;

    @NotNull
    private Integer mileage;

    @NotBlank
    private String fuel;

    @NotBlank
    private String color;

    @NotBlank
    @Column(name = "inspection_location")
    private String inspectionLocation;
}
