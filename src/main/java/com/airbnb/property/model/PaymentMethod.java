package com.airbnb.property.model;

import com.airbnb.property.config.AttributeEncryptor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PaymentMethod extends BaseEntity {
    @Convert(converter = AttributeEncryptor.class)
    @Column(nullable = false)
    private String cardNumber;

    @Convert(converter = AttributeEncryptor.class)
    @Column(nullable = false)
    private String cvc;

    @Convert(converter = AttributeEncryptor.class)
    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    private String last4;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
