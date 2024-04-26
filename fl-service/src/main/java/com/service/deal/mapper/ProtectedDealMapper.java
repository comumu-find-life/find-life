package com.service.deal.mapper;

import com.core.deal.model.ProtectedDeal;
import com.service.deal.dto.CreateProtectedDealDto;
import com.service.deal.dto.ProtectedDealResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import com.core.deal.model.DealState; // 추가된 import 문

@Mapper(componentModel = "spring")
public interface ProtectedDealMapper {

    ProtectedDealMapper INSTANCE = Mappers.getMapper(ProtectedDealMapper.class);


    @Mapping(target = "charge", expression = "java(protectedDeal.calculateCharge())")
    @Mapping(target = "totalPrice", expression = "java(protectedDeal.calculateFinalPayPrice())")
    ProtectedDealResponse toDto(ProtectedDeal protectedDeal);

    //todo DealState symbol 에러 해결해야댐
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dealState", expression = "java(com.core.deal.model.DealState.DURING)")
    ProtectedDeal toEntity(CreateProtectedDealDto dto);

}
