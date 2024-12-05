package com.common.reservation.mapper;

import com.common.deal.mapper.ProtectedDealMapper;
import com.common.reservation.request.ReservationGeneratorRequest;
import com.core.api_core.reservation.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservationState", expression = "java(com.core.api_core.reservation.model.ReservationState.PENDING)")
    Reservation toEntity(ReservationGeneratorRequest request);
}
