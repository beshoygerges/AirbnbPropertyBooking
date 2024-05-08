package com.airbnb.property.service;

import com.airbnb.property.dto.BookingDTO;
import com.airbnb.property.dto.PaymentDTO;
import com.airbnb.property.mapper.BookingMapper;
import com.airbnb.property.model.*;
import com.airbnb.property.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final PaymentService paymentService;
    private final PaymentMethodService paymentMethodService;
    private final UserService userService;
    private final PropertyService propertyService;

    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        User user = userService.getUser(bookingDTO.getUserId());

        Property property = propertyService.getProperty(bookingDTO.getPropertyId());

        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethod(bookingDTO.getPaymentMethodId(), user.getId());

        validateCheckInOutDates(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());

        validateNoActiveBookings(property, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());

        BigDecimal totalAmount = calculateBookingAmount(bookingDTO, property.getPricePerNight());

        validateAmountMatch(bookingDTO.getAmount(), totalAmount);

        PaymentDTO paymentDTO = paymentService.processPayment(bookingDTO, paymentMethod);

        Booking booking = createBookingEntity(user, property, paymentMethod, bookingDTO, paymentDTO);

        return bookingMapper.toDto(booking);
    }

    private BigDecimal calculateBookingAmount(BookingDTO bookingDTO, BigDecimal pricePerNight) {
        long numberOfNights = ChronoUnit.DAYS.between(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());

        return pricePerNight.multiply(BigDecimal.valueOf(numberOfNights));
    }

    private void validateCheckInOutDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate.isEqual(checkOutDate) || checkOutDate.isBefore(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
    }

    private void validateNoActiveBookings(Property property, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Booking> activeBookings = bookingRepository.findActiveBookingsForProperty(property.getId(), checkInDate, checkOutDate);
        if (!activeBookings.isEmpty()) {
            throw new IllegalArgumentException("There are active bookings for the property during this period");
        }
    }

    private void validateAmountMatch(BigDecimal requestedAmount, BigDecimal calculatedAmount) {
        if (!requestedAmount.equals(calculatedAmount)) {
            throw new IllegalArgumentException("Mismatched Amounts");
        }
    }

    private Booking createBookingEntity(User user, Property property, PaymentMethod paymentMethod,
                                        BookingDTO bookingDTO, PaymentDTO paymentDTO) {
        Booking booking = new Booking();
        booking.setProperty(property);
        booking.setUser(user);
        booking.setPaymentMethod(paymentMethod);
        booking.setAmount(bookingDTO.getAmount());
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setStatus(BookingStatus.COMPLETED);
        booking.setTxId(paymentDTO.getId());

        return bookingRepository.save(booking);
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(bookingMapper::toDto).collect(toList());
    }
}
