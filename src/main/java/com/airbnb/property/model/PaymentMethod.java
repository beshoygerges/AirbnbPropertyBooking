package com.airbnb.property.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PaymentMethod extends BaseEntity {
    @Column(nullable = false)
    private String cardNumber;
    @Column(nullable = false)
    private String cvc;
    @Column(nullable = false)
    private String expiryDate;
    @Column(nullable = false)
    private String last4;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
