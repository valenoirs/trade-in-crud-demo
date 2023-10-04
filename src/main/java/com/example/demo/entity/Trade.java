package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trades")
public class Trade {

    @Id
    private String id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @Email
    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "inspector_name")
    private String inspectorName;

    @Column(name = "inspector_phone")
    private String inspectorPhone;

    @Email
    @Column(name = "inspector_email")
    private String inspectorEmail;

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
