package com.airbnb.property.service;


import com.airbnb.property.dto.BookingDTO;
import com.airbnb.property.dto.PaymentDTO;
import com.airbnb.property.mapper.BookingMapper;
import com.airbnb.property.model.Booking;
import com.airbnb.property.model.PaymentMethod;
import com.airbnb.property.model.Property;
import com.airbnb.property.model.User;
import com.airbnb.property.repository.BookingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private PaymentMethodService paymentMethodService;

    @Mock
    private UserService userService;

    @Mock
    private PropertyService propertyService;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingService bookingService;


    @DisplayName("JUnit test for get all bookings method")
    @Test
    void testGetAllBookings() {
        // Arrange
        List<Booking> expectedBookings = Arrays.asList(new Booking(), new Booking());
        when(bookingRepository.findAll()).thenReturn(expectedBookings);

        // Act
        List<BookingDTO> actualBookings = bookingService.getAllBookings();

        // Assert
        assertEquals(expectedBookings.size(), actualBookings.size());
    }

    @DisplayName("JUnit test for create booking and user not found")
    @Test
    void testCreateBookingShouldFailUserNotFound() {

        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUserId(UUID.randomUUID());

        when(userService.getUser(bookingDTO.getUserId())).thenThrow(new RuntimeException());

        // Assert
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @DisplayName("JUnit test for create booking and property not found")
    @Test
    void testCreateBookingShouldFailPropertyNotFound() {

        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setPropertyId(UUID.randomUUID());
        bookingDTO.setUserId(UUID.randomUUID());

        when(userService.getUser(bookingDTO.getUserId())).thenReturn(new User());
        when(propertyService.getProperty(bookingDTO.getPropertyId())).thenThrow(new RuntimeException());

        // Assert
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @DisplayName("JUnit test for create booking and payment method not found")
    @Test
    void testCreateBookingShouldFailPaymentMethodNotFound() {

        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setPropertyId(UUID.randomUUID());
        bookingDTO.setUserId(UUID.randomUUID());
        bookingDTO.setPaymentMethodId(UUID.randomUUID());

        User user = new User();
        user.setId(1l);

        when(userService.getUser(bookingDTO.getUserId())).thenReturn(user);
        when(propertyService.getProperty(bookingDTO.getPropertyId())).thenReturn(new Property());
        when(paymentMethodService.getPaymentMethod(bookingDTO.getPaymentMethodId(), user.getId())).thenThrow(new RuntimeException());

        // ACT
        // Assert
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @DisplayName("JUnit test for create booking and payment method not found")
    @Test
    void testCreateBookingShouldFailCheckInDateAfterCheckoutDate() {

        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setPropertyId(UUID.randomUUID());
        bookingDTO.setUserId(UUID.randomUUID());
        bookingDTO.setPaymentMethodId(UUID.randomUUID());
        bookingDTO.setCheckInDate(LocalDate.now().plusDays(5));
        bookingDTO.setCheckOutDate(LocalDate.now());

        User user = new User();
        user.setId(1l);

        when(userService.getUser(bookingDTO.getUserId())).thenReturn(user);
        when(propertyService.getProperty(bookingDTO.getPropertyId())).thenReturn(new Property());
        when(paymentMethodService.getPaymentMethod(bookingDTO.getPaymentMethodId(), user.getId())).thenReturn(new PaymentMethod());

        // ACT
        // Assert
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @DisplayName("JUnit test for create booking and payment method not found")
    @Test
    void testCreateBookingShouldFailCheckInDateEqualCheckoutDate() {

        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setPropertyId(UUID.randomUUID());
        bookingDTO.setUserId(UUID.randomUUID());
        bookingDTO.setPaymentMethodId(UUID.randomUUID());
        bookingDTO.setCheckInDate(LocalDate.now());
        bookingDTO.setCheckOutDate(LocalDate.now());

        User user = new User();
        user.setId(1l);

        when(userService.getUser(bookingDTO.getUserId())).thenReturn(user);
        when(propertyService.getProperty(bookingDTO.getPropertyId())).thenReturn(new Property());
        when(paymentMethodService.getPaymentMethod(bookingDTO.getPaymentMethodId(), user.getId())).thenReturn(new PaymentMethod());

        // ACT
        // Assert
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @DisplayName("JUnit test for create booking and payment method not found")
    @Test
    void testCreateBookingShouldFailThereAreActiveBookingsDuringThisPeriod() {

        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setPropertyId(UUID.randomUUID());
        bookingDTO.setUserId(UUID.randomUUID());
        bookingDTO.setPaymentMethodId(UUID.randomUUID());
        bookingDTO.setCheckInDate(LocalDate.now());
        bookingDTO.setCheckOutDate(LocalDate.now().plusDays(1));

        User user = new User();
        user.setId(1l);

        Property property = new Property();
        property.setId(1l);

        when(userService.getUser(bookingDTO.getUserId())).thenReturn(user);
        when(propertyService.getProperty(bookingDTO.getPropertyId())).thenReturn(property);
        when(paymentMethodService.getPaymentMethod(bookingDTO.getPaymentMethodId(), user.getId())).thenReturn(new PaymentMethod());
        when(bookingRepository.findActiveBookingsForProperty(property.getId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())).thenReturn(List.of(new Booking()));


        // ACT
        // Assert
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @DisplayName("JUnit test for create booking and payment method not found")
    @Test
    void testCreateBookingShouldFailMisMatchedTotalAmounts() {

        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setPropertyId(UUID.randomUUID());
        bookingDTO.setUserId(UUID.randomUUID());
        bookingDTO.setPaymentMethodId(UUID.randomUUID());
        bookingDTO.setCheckInDate(LocalDate.now());
        bookingDTO.setCheckOutDate(LocalDate.now().plusDays(2));
        bookingDTO.setAmount(BigDecimal.valueOf(150));

        User user = new User();
        user.setId(1L);

        Property property = new Property();
        property.setId(1L);
        property.setPricePerNight(BigDecimal.valueOf(100));

        when(userService.getUser(bookingDTO.getUserId())).thenReturn(user);
        when(propertyService.getProperty(bookingDTO.getPropertyId())).thenReturn(property);
        when(paymentMethodService.getPaymentMethod(bookingDTO.getPaymentMethodId(), user.getId())).thenReturn(new PaymentMethod());
        when(bookingRepository.findActiveBookingsForProperty(property.getId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())).thenReturn(Collections.emptyList());


        // ACT
        // Assert
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @DisplayName("JUnit test for create booking and payment method not found")
    @Test
    void testCreateBookingShouldFailPaymentFailed() {

        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setPropertyId(UUID.randomUUID());
        bookingDTO.setUserId(UUID.randomUUID());
        bookingDTO.setPaymentMethodId(UUID.randomUUID());
        bookingDTO.setCheckInDate(LocalDate.now());
        bookingDTO.setCheckOutDate(LocalDate.now().plusDays(2));
        bookingDTO.setAmount(BigDecimal.valueOf(200));

        User user = new User();
        user.setId(1L);

        Property property = new Property();
        property.setId(1L);
        property.setPricePerNight(BigDecimal.valueOf(100));

        PaymentMethod paymentMethod = new PaymentMethod();

        when(userService.getUser(bookingDTO.getUserId())).thenReturn(user);
        when(propertyService.getProperty(bookingDTO.getPropertyId())).thenReturn(property);
        when(paymentMethodService.getPaymentMethod(bookingDTO.getPaymentMethodId(), user.getId())).thenReturn(paymentMethod);
        when(bookingRepository.findActiveBookingsForProperty(property.getId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())).thenReturn(Collections.emptyList());
        when(paymentService.processPayment(bookingDTO, paymentMethod)).thenThrow(new RuntimeException());


        // ACT
        // Assert
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(bookingDTO));
        verify(bookingRepository, times(0)).save(any());
    }

    @DisplayName("JUnit test for create booking and payment method not found")
    @Test
    void testCreateBookingShouldPass() {

        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setPropertyId(UUID.randomUUID());
        bookingDTO.setUserId(UUID.randomUUID());
        bookingDTO.setPaymentMethodId(UUID.randomUUID());
        bookingDTO.setCheckInDate(LocalDate.now());
        bookingDTO.setCheckOutDate(LocalDate.now().plusDays(2));
        bookingDTO.setAmount(BigDecimal.valueOf(200));

        User user = new User();
        user.setId(1L);

        Property property = new Property();
        property.setId(1L);
        property.setPricePerNight(BigDecimal.valueOf(100));

        PaymentMethod paymentMethod = new PaymentMethod();

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(UUID.randomUUID().toString());


        when(userService.getUser(bookingDTO.getUserId())).thenReturn(user);
        when(propertyService.getProperty(bookingDTO.getPropertyId())).thenReturn(property);
        when(paymentMethodService.getPaymentMethod(bookingDTO.getPaymentMethodId(), user.getId())).thenReturn(paymentMethod);
        when(bookingRepository.findActiveBookingsForProperty(property.getId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())).thenReturn(Collections.emptyList());
        when(paymentService.processPayment(bookingDTO, paymentMethod)).thenReturn(paymentDTO);
        when(bookingRepository.save(any())).then(returnsFirstArg());
        when(bookingMapper.toDto(any())).thenReturn(bookingDTO);


        // ACT
        BookingDTO booking = bookingService.createBooking(bookingDTO);

        // Assert
        assertNotNull(booking);
        assertEquals(bookingDTO.getCheckInDate(), booking.getCheckInDate());
        assertEquals(bookingDTO.getCheckOutDate(), booking.getCheckOutDate());
        assertEquals(bookingDTO.getAmount(), booking.getAmount());
        verify(bookingRepository, times(1)).save(any());
    }
}


