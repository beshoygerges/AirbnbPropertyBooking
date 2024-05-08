package com.airbnb.property.controller;

import com.airbnb.property.dto.BookingDTO;
import com.airbnb.property.dto.ResponseDTO;
import com.airbnb.property.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final MessageSource messageSource;


    @PostMapping
    public ResponseDTO<BookingDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        final String SUCCESS_MSG = messageSource.getMessage("success.message", null, LocaleContextHolder.getLocale());
        return new ResponseDTO<>(HttpStatus.CREATED.value(), SUCCESS_MSG, SUCCESS_MSG, bookingService.createBooking(bookingDTO));
    }

    @GetMapping
    public ResponseDTO<List<BookingDTO>> getAllBookings() {
        final String SUCCESS_MSG = messageSource.getMessage("success.message", null, LocaleContextHolder.getLocale());
        return new ResponseDTO<>(HttpStatus.OK.value(), SUCCESS_MSG, SUCCESS_MSG, bookingService.getAllBookings());
    }
}
