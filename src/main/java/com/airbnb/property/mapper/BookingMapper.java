package com.airbnb.property.mapper;

import com.airbnb.property.dto.BookingDTO;
import com.airbnb.property.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "property.uuid", target = "propertyId")
    @Mapping(source = "user.uuid", target = "userId")
    @Mapping(source = "paymentMethod.uuid", target = "paymentMethodId")
    BookingDTO toDto(Booking booking);

    Booking toEntity(BookingDTO dto);
}
