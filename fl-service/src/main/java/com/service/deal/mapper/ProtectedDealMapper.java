//package com.service.deal.mapper;
//
//import com.core.api_core.deal.model.ProtectedDeal;
//import com.service.deal.dto.CreateProtectedDealDto;
//import com.service.deal.dto.ProtectedDealResponse;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface ProtectedDealMapper {
//
//    ProtectedDealMapper INSTANCE = Mappers.getMapper(ProtectedDealMapper.class);
//
//
//    @Mapping(target = "charge", expression = "java(protectedDeal.calculateCharge())")
//    @Mapping(target = "totalPrice", expression = "java(protectedDeal.calculateFinalPayPrice())")
//    ProtectedDealResponse toDto(ProtectedDeal protectedDeal);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "dealState", expression = "java(com.core.api_core.deal.model.DealState.DURING)")
//    ProtectedDeal toEntity(CreateProtectedDealDto dto);
//
//}
