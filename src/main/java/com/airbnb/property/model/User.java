package com.airbnb.property.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Customer extends BaseEntity {
    private String name;
    private String email;
    private String mobile;
}
