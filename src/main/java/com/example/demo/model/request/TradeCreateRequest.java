package com.example.demo.model.request;

import com.example.demo.entity.Trade;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeCreateRequest {

    @NotBlank
    private String customerName;

    @NotBlank
    private String customerPhone;

    private String customerEmail;

    private String brand;

    private String model;

    private String type;

    private String year;

    private Integer mileage;

    private String fuel;

    private String color;

    @Column(name = "inspection_location")
    private String inspectionLocation;
}
