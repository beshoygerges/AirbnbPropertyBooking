package com.airbnb.property.service;

import com.airbnb.property.model.Property;
import com.airbnb.property.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;


    @Test
    void getProperty_ValidPropertyId_ReturnsProperty() {
        // Arrange
        UUID propertyId = UUID.randomUUID();
        Property expectedProperty = createProperty(propertyId);
        when(propertyRepository.findByUuid(propertyId)).thenReturn(Optional.of(expectedProperty));

        // Act
        Property result = propertyService.getProperty(propertyId);

        // Assert
        assertEquals(expectedProperty, result);
        verify(propertyRepository).findByUuid(propertyId);
    }

    @Test
    void getProperty_InvalidPropertyId_ThrowsException() {
        // Arrange
        UUID propertyId = UUID.randomUUID();
        when(propertyRepository.findByUuid(propertyId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> propertyService.getProperty(propertyId));
        verify(propertyRepository).findByUuid(propertyId);
    }

    // Helper method to create a Property object for testing
    private Property createProperty(UUID propertyId) {
        Property property = new Property();
        property.setUuid(propertyId);
        // Set other properties as needed
        return property;
    }
}
