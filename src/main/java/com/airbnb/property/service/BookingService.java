package com.airbnb.property.service;

import com.airbnb.property.dto.BookingDto;
import com.airbnb.property.model.*;
import com.airbnb.property.repository.BookingRepository;
import com.airbnb.property.repository.PaymentMethodRepository;
import com.airbnb.property.repository.PropertyRepository;
import com.airbnb.property.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final PropertyRepository propertyRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    @Transactional
    public Booking createBooking(BookingDto bookingDTO) {
        // Check if the user exists
        User user = userRepository.findByUuid(bookingDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + bookingDTO.getUserId()));

        // Check if the property exists
        Property property = propertyRepository.findByUuid(bookingDTO.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Property not found with ID: " + bookingDTO.getPropertyId()));

        // Check if the payment method exists
        PaymentMethod paymentMethod = paymentMethodRepository.findByUuidAndUserId(bookingDTO.getPaymentMethodId(), user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found for user with ID: " + bookingDTO.getUserId()));

        // Here you can implement the logic to create the booking
        // Example: Saving booking details to the database
        Booking booking = new Booking();
        booking.setProperty(property);
        booking.setUser(user);
        booking.setPaymentMethod(paymentMethod);
        booking.setAmount(bookingDTO.getAmount());
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setStatus(BookingStatus.COMPLETED);

        return bookingRepository.save(booking);
    }
}
