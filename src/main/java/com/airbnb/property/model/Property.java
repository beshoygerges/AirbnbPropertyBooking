package com.airbnb.property.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Property extends BaseEntity {
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer rooms;

    @Column(nullable = false)
    private BigDecimal pricePerNight;
}
