package com.airbnb.property.service;

import com.airbnb.property.model.Property;
import com.airbnb.property.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public Property getProperty(UUID propertyId) {
        return propertyRepository.findByUuid(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found with ID: " + propertyId));
    }
}
