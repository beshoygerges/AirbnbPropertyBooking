package com.airbnb.property.service;

import com.airbnb.property.dto.BookingDTO;
import com.airbnb.property.dto.PaymentDTO;
import com.airbnb.property.model.PaymentMethod;

public interface PaymentService {
    PaymentDTO processPayment(BookingDTO bookingDTO, PaymentMethod paymentMethod);
}
