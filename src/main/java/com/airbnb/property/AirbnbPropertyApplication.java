package com.airbnb.property;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AirbnbPropertyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirbnbPropertyApplication.class, args);
    }

}
