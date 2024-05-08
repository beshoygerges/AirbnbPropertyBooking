package com.airbnb.property.client;

import com.airbnb.property.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${payment.service.url}")
public interface PaymentClient {

    @PostMapping
    ResponseEntity<PaymentDTO> processPayment(@RequestBody PaymentDTO paymentDTO);
}
