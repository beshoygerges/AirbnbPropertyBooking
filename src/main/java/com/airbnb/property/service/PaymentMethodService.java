package com.airbnb.property.service;

import com.airbnb.property.model.PaymentMethod;
import com.airbnb.property.repository.PaymentMethodRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod getPaymentMethod(UUID paymentMethodId, Long userId) {
        return paymentMethodRepository.findByUuidAndUserId(paymentMethodId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Payment Method not found with ID: " + paymentMethodId));
    }
}
