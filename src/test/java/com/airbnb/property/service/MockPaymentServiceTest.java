package com.airbnb.property.service;

import com.airbnb.property.client.PaymentClient;
import com.airbnb.property.dto.BookingDTO;
import com.airbnb.property.dto.PaymentDTO;
import com.airbnb.property.model.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockPaymentServiceTest {

    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private MockPaymentService paymentService;


    @Test
    void processPayment_ValidInput_ReturnsPaymentDTO() {
        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        PaymentMethod paymentMethod = new PaymentMethod();
        PaymentDTO expectedPaymentDTO = new PaymentDTO();
        ResponseEntity<PaymentDTO> responseEntity = ResponseEntity.ok(expectedPaymentDTO);

        when(paymentClient.processPayment(any(PaymentDTO.class))).thenReturn(responseEntity);

        // Act
        PaymentDTO result = paymentService.processPayment(bookingDTO, paymentMethod);

        // Assert
        assertEquals(expectedPaymentDTO, result);
        verify(paymentClient).processPayment(any(PaymentDTO.class));
    }

    @Test
    void processPayment_NullResponse_ThrowsException() {
        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        PaymentMethod paymentMethod = new PaymentMethod();

        when(paymentClient.processPayment(any(PaymentDTO.class))).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> paymentService.processPayment(bookingDTO, paymentMethod));
        verify(paymentClient).processPayment(any(PaymentDTO.class));
    }

    @Test
    void processPayment_FailedResponse_ThrowsException() {
        // Arrange
        BookingDTO bookingDTO = new BookingDTO();
        PaymentMethod paymentMethod = new PaymentMethod();
        ResponseEntity<PaymentDTO> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        when(paymentClient.processPayment(any(PaymentDTO.class))).thenReturn(responseEntity);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> paymentService.processPayment(bookingDTO, paymentMethod));
        verify(paymentClient).processPayment(any(PaymentDTO.class));
    }

}

