package com.airbnb.property.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentDTO {
    private LocalDateTime createdAt;
    private String creditCardNumber;
    private String cvc;
    private String expireDate;
    private BigDecimal amount;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;
}
