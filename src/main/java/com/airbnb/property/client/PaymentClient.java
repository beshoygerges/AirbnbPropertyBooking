package com.airbnb.property.client;

import com.airbnb.property.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:8081") // Replace with the actual URL of the mock payment service
public interface PaymentClient {

    @PostMapping("/processPayment")
    ResponseEntity<PaymentDTO> processPayment(@RequestBody PaymentDTO paymentDTO);
}
