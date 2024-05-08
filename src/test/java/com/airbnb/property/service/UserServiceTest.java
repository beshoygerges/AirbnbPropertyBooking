package com.airbnb.property.service;

import com.airbnb.property.model.User;
import com.airbnb.property.repository.UserRepository;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void getUser_ValidUserId_Returns() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User expectedUser = createUser(userId);
        when(userRepository.findByUuid(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = userService.getUser(userId);

        // Assert
        assertEquals(expectedUser, result);
        verify(userRepository).findByUuid(userId);
    }

    @Test
    void getUser_InvalidUserId_ThrowsException() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userRepository.findByUuid(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(userId));
        verify(userRepository).findByUuid(userId);
    }

    // Helper method to create a Property object for testing
    private User createUser(UUID userId) {
        User user = new User();
        user.setUuid(userId);
        return user;
    }
}
