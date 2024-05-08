package com.airbnb.property.repository;

import com.airbnb.property.model.Property;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Property> findByUuid(UUID uuid);
}