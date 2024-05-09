package com.airbnb.property.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties
public class ApplicationConfig {
    @Value("${aes.key}")
    private String aesKey;

    @Value("${aes.iv}")
    private String iv;
}
