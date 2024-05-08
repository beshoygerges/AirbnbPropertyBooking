package com.airbnb.property.service;

import com.airbnb.property.client.PaymentClient;
import com.airbnb.property.dto.BookingDTO;
import com.airbnb.property.dto.PaymentDTO;
import com.airbnb.property.model.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MockPaymentService implements PaymentService {

    private final PaymentClient paymentClient;

    @Override
    public PaymentDTO processPayment(BookingDTO bookingDTO, PaymentMethod paymentMethod) {
        ResponseEntity<PaymentDTO> response = paymentClient.processPayment(PaymentDTO.builder()
                .amount(bookingDTO.getAmount())
                .createdAt(LocalDateTime.now())
                .creditCardNumber(paymentMethod.getCardNumber())
                .cvc(paymentMethod.getCvc())
                .expireDate(paymentMethod.getExpiryDate())
                .build());

        if (response == null || response.getBody() == null ||
                (response.getStatusCode() != HttpStatus.CREATED && response.getStatusCode() != HttpStatus.OK)) {
            throw new IllegalArgumentException("Payment Failed");
        }

        return response.getBody();
    }
}
