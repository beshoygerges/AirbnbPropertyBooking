package com.airbnb.property.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class BookingDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid;

    @NotNull
    private UUID propertyId;

    @NotNull
    private UUID userId;

    @NotNull
    private UUID paymentMethodId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String txId;

    @Positive
    private BigDecimal amount;

    @NotNull
    @FutureOrPresent
    private LocalDate checkInDate;

    @NotNull
    @Future
    private LocalDate checkOutDate;
}
